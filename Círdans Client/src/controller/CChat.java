package controller;

import app.gui.component.CMessageCell;
import engine.Engine;
import java.util.Date;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import protocol.EStatus;
import protocol.model.SMessage;
import protocol.model.SProfile;
import util.UHistoric;
import util.USound;

/**
 * Controlador da janela de conversa individual
 * @author Marcelo Gomes Martins
 */
public class CChat extends AController {
    // Nome do usuário
    @FXML private Label lblName;
    // Subnick do usuário
    @FXML private Label lblNick;
    // Descrição escolhida pelo usuário
    @FXML private Label lblStatus;
    // Foto de perfil
    @FXML private ImageView imgProfile;
    // Status atual do usuário
    @FXML private Pane panStatus;
    // Mostra mensagens na tela
    @FXML private ListView<SMessage> lvwMessages;
    // Entrada de dados para mensagem
    @FXML private TextArea txaInput;
    // Referencia da VBox principal
    @FXML private VBox vbxMain;
    // Referencia interna do perfil do amigo
    private SProfile friend; 
    // Referencia interna para engine
    private Engine engine;
    // Lista de mensagens
    private ObservableList<SMessage> messages;

    public void initialize() {
        // Recebe instance do engine
        this.engine = Engine.getInstance();
        // Inicia ListView
        this.listViewInitializer();
        // Inicia TextArea
        this.textAreaInitializer();
    }
     
    /**
     * Inicia o processo de inicialização do ListView,
     * observando a lista de mensagens recebidas
     */
    private void listViewInitializer(){
        this.messages = FXCollections.observableArrayList();
        this.lvwMessages.getItems().addAll(messages);
        
        /**
         * Adiciona relação entre o ArrayList e a ListView
         */
        this.messages.addListener((ListChangeListener.Change<? extends SMessage> change) -> {
            Platform.runLater(() -> {
                this.lvwMessages.getItems().clear();
                this.lvwMessages.getItems().addAll(messages);
            });
        });
        // Determinar o Componente como base de construção para cada celula da lista
        this.lvwMessages.setCellFactory((ListView<SMessage> param) -> new CMessageCell());
    }
    
    /**
     * Ainda não implementado
     * @param status 
     */
    public void updateStatusPane(EStatus status){
        this.vbxMain.disableProperty().set(false);
        this.profileStatusBorderReset();
        switch(status){
            case ONLINE:
                panStatus.getStyleClass().add("online");
                break;  
            case BUSY:
                panStatus.getStyleClass().add("busy");
                break;
            case AWAY:
                panStatus.getStyleClass().add("away");
                break;
            default:
                panStatus.getStyleClass().add("offline");
                this.vbxMain.disableProperty().set(true);
                break;
        }
    }
    
    /**
     * Limpa os estilos do Pane de status
     */
    private void profileStatusBorderReset(){
        panStatus.getStyleClass().remove("away");
        panStatus.getStyleClass().remove("online");
        panStatus.getStyleClass().remove("busy");
        panStatus.getStyleClass().remove("offline");
    }
   
    /**
     * Mostra dados do usuário na tela
     */
    private void profileBinding(){
        this.lblName.setText(this.friend.getName());
        this.lblNick.setText(this.friend.getNick());
        this.lblStatus.setText(this.friend.getSubnick());
        this.updateStatusPane(EStatus.get(this.friend.getStatus()));
        this.loadImage(this.friend.getImageUrl());
    }
        
    /**
     * Carrega imagem no Pane de imagem de perfil
     * @param url origem da foto
     */
    private void loadImage(String url){
        Platform.runLater(() -> {
            Image img;
            try{
                img = new Image(url);
                if(img.isError()){
                    this.imgProfile.setImage(new Image("/images/default-profile.png"));
                } else {
                    this.imgProfile.setImage(img);
                }
            } catch(Exception e){
                this.imgProfile.setImage(new Image("/images/default-profile.png"));
            }
        });
    }
    
    
    /**
     * Inicia o processo de inicialização do TextArea,
     * ENTER envia mensagem
     */
    private void textAreaInitializer(){
        this.txaInput.setOnKeyReleased((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                String text = txaInput.getText().trim();
                if(!text.equals("")){
                    SMessage message = this.createMessage(text);
                    UHistoric historic = UHistoric.getInstance();
                    historic.record(message, true);
                    this.addMessage(message);
                    this.engine.sendMessage(this.friend.getId(), message, false);
                }
            
                txaInput.setText("");
            }
        });
    }
    
    /**
     * Cria objeto que representa uma mensagem
     * @param value texto da mensagem
     * @return mensagem
     */
    private SMessage createMessage(String value){
        return new SMessage(
                this.engine.getClient().id,
                this.engine.getClientInfo().name,
                this.friend.getId(),
                this.friend.getName(),
                value,
                new Date()
        );
    }
    
    public void addMessage(SMessage message){
        UHistoric historic = UHistoric.getInstance();
        historic.record(message, false);
        this.messages.add(message);
        this.lvwMessages.scrollTo(message);
    }
    
    /**
     * Trava o botão de requisição de atenção
     * @param ae 
     */
    public void attentionHandler(ActionEvent ae){
        Runnable runIt = () -> {
            try {
                Button self = (Button)ae.getSource();
                self.disableProperty().set(true);
                Platform.runLater(() -> {
                    this.takeAttention();
                });
                Thread.sleep(10000);
                self.disableProperty().set(false);
            } catch (InterruptedException ex) {
                System.out.println("takeAttention@CChat");
            }
        };
        new Thread(runIt).start();
    }
    
    public void takeAttention(){
        UHistoric historic = UHistoric.getInstance();
        SMessage toStore = this.createMessage("Você chamou a atenção.");
        historic.record(toStore, true);
        this.addMessage(toStore);
        SMessage toSent = this.createMessage(this.engine.getClientInfo().name + " chamou a atenção.");
        this.engine.sendMessage(this.friend.getId(), toSent, true);
    }
   
    public SProfile getFriend() { return friend; }
    public void setFriend(SProfile friend) { 
        this.friend = friend;
        this.profileBinding();
    }
}
