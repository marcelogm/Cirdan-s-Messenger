package controller;

// <editor-fold defaultstate="collapsed" desc="Importações">
import app.gui.component.CFriendCell;
import engine.Engine;
import protocol.CProtocol;
import protocol.EStatus;
import app.gui.component.CStatusListener;
import java.io.IOException;
import java.net.URL;
import util.ObservableQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import protocol.model.SMessage;
import protocol.model.SProfile;
import util.UStage;
import util.USound;
// </editor-fold>

/**
 * Classe controladora da tela principal do aplicativo
 * @author Marcelo Gomes Martins
 */
public class CMain extends AController {
    // Mapeamento de amigos e chaves primarias
    private ObservableMap<Long, SProfile> friends;
    // Mapeamento de chats e chaves primerias
    private HashMap<Long, CChat> chats;
    // Fila de mensagems cliente/servidor
    // Não há necessidade de utilizar fila, modificar futuramente
    private ObservableQueue<CProtocol> serverMessages;
    // Motor
    private Engine engine;
    // Nome do usuário
    @FXML private Label lblProfileName;
    // Descrição do usuário
    @FXML private Label lblProfileBio;
    // Nickname do usário
    @FXML private Label lblNickname;
    // Combobox com status do usuário
    @FXML private ComboBox cmbStatus;
    // Imagem de perfil
    @FXML private ImageView imgSelf;
    // Pane de visualização de status
    @FXML private Pane panProfileBorder;
    // ListView com perfils dos amigos na tela principal
    @FXML private ListView<SProfile> lvwFriends;
    // Abas de mensagens
    @FXML private TabPane tbpMain;
    // Stage de "adicionar amigos"
    private Stage addFriend;
    // Controller do Stage addFriend
    private CNewFriendship addController;
    
    /**
     * Inicilização do ambiente gráfico
     */
    public CMain() {}
    
    @FXML public void initialize() {
        // Inicia ListView de amigos
        this.listViewInitializer();
        // Inicia TabPane de chats
        this.tabPaneInitializer();
        // Espera mensagens do servidor
        this.serverListeningInitializer();
        // Monta dados do usuário na interface
        this.selfProfileBinding();
        // Envia requisição de lista de amigos
        this.engine.sendFriendListRequest();
        // Envia requisição de amizades pendentes
        this.engine.sendPendingFriendshipRequest();
    }
    
    /**
     * Realiza a construção do listview por FXML
     */
    private void listViewInitializer(){
        this.friends = FXCollections.observableHashMap();
        this.lvwFriends.getItems().addAll(friends.values());
        /**
         * Adiciona relação entre o HashMap e a ListView
         * A modificação na HashMap reflete em modificação na ListView
         */
        this.friends.addListener((MapChangeListener.Change<? extends Long, ? extends SProfile> change) -> {
            Platform.runLater(() -> {
                if(change.wasAdded()){
                    lvwFriends.getItems().add(change.getValueAdded());
                } else if (change.wasRemoved()){
                    lvwFriends.getItems().remove(change.getValueRemoved());
                }
            });
        });
        // Determinar o Componente como base de construção para cada celula da lista
        this.lvwFriends.setCellFactory((ListView<SProfile> param) -> new CFriendCell());
        /**
         * Adiciona evento de clique a cada celula da lista
         * Responsavel por gerar nova aba de conversa
         * Adiciona a lista de chats um novo item
         */
        this.lvwFriends.setOnMouseClicked((MouseEvent event) -> {
            SProfile friend = lvwFriends.getSelectionModel().getSelectedItem();
            ObservableList<Tab> tabs = tbpMain.getTabs();
            // Se não estiver offline
            if(friend.getStatus() != EStatus.OFFLINE.status){
                // Verifica cada aba
                for(Tab t : tabs){
                    // Se achar seleciona e quebra o fluxo de execução
                    if(t.getId() != null && t.getId().equals(friend.getId().toString())){
                        tbpMain.getSelectionModel().select(t);
                        return;
                    }
                }
                // Se não, cria nova aba
                newChatTab(friend);
            }
        });
    }
    
    /**
     * Inicia configurações do TabPane
     */
    private void tabPaneInitializer(){
        this.chats = new HashMap<>();
        /**
         * Remove da lista de chats ativos ao clicar para fechar Tab
         */
        this.tbpMain.getTabs().addListener((ListChangeListener.Change<? extends Tab> change) -> {
            while(change.next()){
                if(change.wasRemoved()){
                    for(Tab removed : change.getRemoved()){
                        chats.remove(Long.parseLong(removed.getId()));
                    }
                }
            }
        });
    }
    
    /**
     * Inicia escuta em combobox
     */
    private void setStatusListener(){
        cmbStatus.getSelectionModel().selectedIndexProperty().addListener(
            new CStatusListener(this.panProfileBorder)
        );
    }
    
    /**
     * Inicia tratador de mensagens do servidor
     * Recebe mensagens do servidor e modifica o controlador Main
     * de acordo com cada mensagem
     */
    private void serverListeningInitializer(){
        this.engine = Engine.getInstance();
        this.serverMessages = new ObservableQueue<>();
        this.engine.startListening(serverMessages);
        /**
         * Escuta objeto obsrvavel procurando nova mensagem
         * e trata ela de acordo com o caso.
         * Aqui são disparadas as ações que modificam o controlador
         */
        this.serverMessages.addListener((Change<? extends CProtocol> change) -> {
            // <editor-fold defaultstate="collapsed" desc="Tratamento de mensagens do servidor">
            while (change.next()) {
                if (change.wasAdded()) {
                    CProtocol message = (CProtocol) change.getAddedSubList().get(0);
                    Platform.runLater(() -> {
                        switch(message.getType()){
                            // Recebe lista de amigos
                            case FRIENDLIST_RESPONSE:
                                this.friendListBinding(message);
                                break;
                            // Recebe mudança de status de algum amigo
                            case FRIEND_CHANGE_STATUS:
                                this.friendChangeStatusBinding(message);
                                break;
                            // Recebe lista de amigos para pesquisa de "adicioanr amigos"
                            case FIND_PROFILE_RESPONSE:
                                this.addController.profileListBinding(message);
                                break;
                            // Questiona usuário sobre amizade requisitada
                            case FRIENDSHIP_QUESTION:
                                this.friendshipQuestionDialog(message);
                                break;
                            // Recebe fila de requsições de amizades pendentes
                            case PENDING_FRIENDSHIP_RESPONSE:
                                this.friendshipQuestionQueue(message);
                                break;
                            case RECEIVE_MESSAGE:
                                this.recieveMessage(message, false);
                                break;
                            case RECEIVE_TAKE_ATTENTION:
                                this.recieveMessage(message, true);
                                break;
                            // Falha na comunicação
                            default:
                                System.out.println("Protocol ilegal.");
                                break;
                        }
                    });
                }
            }
            // </editor-fold>
        });
    }
    
    /**
     * Alimenta controlador com dados do perfil do usuário
     */
    private void selfProfileBinding(){
        this.lblProfileName.setText(this.engine.getClientInfo().name);
        this.lblProfileBio.setText(this.engine.getClientInfo().subnick);
        this.lblNickname.setText(this.engine.getClientInfo().nick);
        this.cmbStatus.getSelectionModel().select((int)this.engine.getClientInfo().status);
        this.loadImage();
        switch(EStatus.get(this.engine.getClientInfo().status)){
             case ONLINE:
                 this.panProfileBorder.getStyleClass().add("online");
                 break;
             case AWAY:
                 this.panProfileBorder.getStyleClass().add("away");
                 break;
             case BUSY:
                 this.panProfileBorder.getStyleClass().add("busy");
                 break;
        }
        this.setStatusListener();
    }
    
    /**
     * Prepara chatMessageBinding ou attentionBinding e grava mensagem de usuário
     * em arquivo de registro em binário
     * @param message 
     */
    private void recieveMessage(CProtocol serverMessage, boolean isAttention){
        UStage manager = UStage.getInstance();
        SMessage message = (SMessage)serverMessage.getPayload();
        manager.getStage().toFront();
        if(isAttention) {
            this.chatMessageBinding(message); 
        } else { 
            this.attentionBinding(message);
        }
    }
   
    /**
     * Executa "chamar atenção" e envia a mensagem para
     * o controlador do chat
     * @param message 
     */
    private void attentionBinding(SMessage message){
        UStage stage = UStage.getInstance();
        if(!chats.containsKey(message.getSenderId())){
            this.newChatTab(friends.get(message.getSenderId()));
        }
        CChat chat = chats.get(message.getSenderId());
        this.buzzSound(message.getSenderId());
        stage.shakeStage();
        chat.addMessage(message);
    }
    
    /**
     * Envia conteudo de mensagem para
     * o controlador do chat
     * @param message 
     */
    private void chatMessageBinding(SMessage message){
        if(!chats.containsKey(message.getSenderId())){
            this.newChatTab(friends.get(message.getSenderId()));
        }
        CChat chat = chats.get(message.getSenderId());
        this.alertSound(message.getSenderId());
        chat.addMessage(message);
    }
    
    /**
     * Alimenta HashMap com lista de amigo
     * @param message 
     */
    private void friendListBinding(CProtocol message){
        this.friends.clear();
        ArrayList<SProfile> list = (ArrayList<SProfile>) message.getPayload();
        for(SProfile friend: list){
            this.friends.put(friend.getId(), friend);
        }
    }
    
    /**
     * Realiza a modificação do status de um amigo
     * @param message 
     */
    private void friendChangeStatusBinding(CProtocol message){
        SProfile friend = this.friends.get(message.getSenderId());
        this.friends.remove(message.getSenderId());
        friend.setStatus((Integer)message.getPayload());
        this.friends.put(friend.getId(), friend);
        if(chats.containsKey(friend.getId())){
            this.chats.get(friend.getId()).updateStatusPane(EStatus.get((Integer)message.getPayload()));
        }
    }
        
    /**
     * Controe nova aba utilizando FXML
     * @param friend 
     */
    private void newChatTab(SProfile friend){
        try {
            Tab t = new Tab();
            t.setId(friend.getId().toString());
            t.setText(friend.getNick());
            
            URL location = getClass().getResource("/app/gui/GChat.fxml");
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(location);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = (Parent)loader.load(location.openStream());
            
            CChat chat = loader.getController();
            chat.setFriend(friend);
            this.chats.put(friend.getId(), chat);
            t.setContent(root);
            
            tbpMain.getTabs().add(t);
        } catch (IOException ex) {
            System.out.println("newChatTab@CMain");
        }
    }
  
    /**
     * Emite o som de alerta para novas mensagens
     * @param id 
     */
    private void alertSound(long id){
        Tab tab = this.tbpMain.getSelectionModel().getSelectedItem();
        if(tab.getId() == null || Long.parseLong(tab.getId()) != id){
            USound sound = USound.getInstance();
            sound.alert();
        }
    }
    
    /**
     * Emite o som de chamar atenção
     * @param id 
     */
    private void buzzSound(long id){
        Tab tab = this.tbpMain.getSelectionModel().getSelectedItem();
        if(tab.getId() == null || Long.parseLong(tab.getId()) != id){
            USound sound = USound.getInstance();
            sound.buzz();
        }
    }

    /**
     * Carrega imagem no perfil do usuário
     */
    private void loadImage(){
        Image img;
        try{
           img = new Image(this.engine.getClientInfo().imageUrl);
            if(img.isError()){
                this.imgSelf.setImage(new Image("/images/default-profile.png"));
            } else {
                this.imgSelf.setImage(img);
            }
        } catch(Exception e){
            this.imgSelf.setImage(new Image("/images/default-profile.png"));
        }
    }
    
    /**
     * Finaliza programa
     * @param event
     */
    public void closeAction(ActionEvent event){
        Stage self = UStage.getInstance().getStage();
        this.engine.destroy();
        self.close();
        Platform.exit();
    }
    
     /**
     * Mostra informações
     * @param event
     */
    public void informationAction(ActionEvent event){
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informações");
            alert.setHeaderText("Círdan's Messenger");
            alert.setContentText("Desenvolvido por Marcelo Gomes Martins.\n"
                    + "Trabalho de conclusão de semestre para Programação "
                    + "Orientada a Objetos II.\n"
                    + "Professora Diana Cabral Cavalcanti.");
            alert.showAndWait();
        });
    }
    
    
    /**
     * Realiza a construção da scene GNewFriendship
     */
    private void newAddFriendScene(){
        try {
            URL location = getClass().getResource("/app/gui/GNewFriendship.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(location);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = (Parent)loader.load(location.openStream());
            this.addController = loader.getController();
            Scene scene = new Scene(root);
            this.addFriend.setScene(scene);
            this.addFriend.show();
        } catch (IOException ex) {
            System.out.println("IOException@newAddFriendScene@CMain");
        } catch (IllegalArgumentException ex){
            System.out.println("IllegalArgumentException@newAddFriendScene@CMain");
        } 
    }
    
    /**
     * Inicia scene para adicionar amigos
     * @param event 
     */
    public void addFriendAction(ActionEvent event){
        if(this.addFriend == null){ this.addFriend = new Stage(); }
        if(!this.addFriend.isShowing()){
            this.addFriend = new Stage();
            this.addFriend.setTitle("Círdan's Messenger - Adicionar amigo");
            this.addFriend.setAlwaysOnTop(true);
            this.addFriend.setResizable(false);
            this.addFriend.setIconified(false);
            this.newAddFriendScene();
        }
    }
    
    /**
     * Questiona usuário em relação a amizades pendentes
     * ação gerada na inicialização da conexão
     * @param message 
     */
    private void friendshipQuestionQueue(CProtocol message){
        HashMap<Long, String> requests = (HashMap<Long, String>)message.getPayload();
        requests.forEach((id, name) -> this.showFriendshipQuestion(id, name));
    }
    
    /**
     * Prepara showFriendshipQuestion
     * @param message 
     */
    private void friendshipQuestionDialog(CProtocol message){
        String name = (String)message.getPayload();
        Long sender = message.getSenderId();

        this.showFriendshipQuestion(sender, name);
    }
    
    /**
     * Mostra dialogo de pedido de amizade
     * e, se for o caso, envia respota ao servidor
     * @param id
     * @param name 
     */
    private void showFriendshipQuestion( Long id, String name){
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Você tem solicitações de amizade!");
            alert.setHeaderText(name + " deseja ser seu amigo.");
            alert.setContentText("Você deseja adicionar " + name + "?");
            ButtonType btnYes = new ButtonType("Sim");
            ButtonType btnNo = new ButtonType("Não");
            ButtonType btnCancel = new ButtonType("Decidir mais tarde", ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(btnYes, btnNo, btnCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == btnYes){
                this.engine.sendFriendshipRespose(id, true);
            } else if (result.get() == btnNo) {
                this.engine.sendFriendshipRespose(id, false);
            }
        });
    }
}