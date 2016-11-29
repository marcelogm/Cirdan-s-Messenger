package controller;

import app.gui.component.CProfileCell;
import engine.Engine;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import protocol.CProtocol;
import protocol.model.SProfile;

/**
 * Classe controladora da tela de adicionar amigos
 * @author Marcelo Gomes Martins
 */
public class CNewFriendship extends AController {
    // Lista de perfils 
    @FXML private ListView<SProfile> lvwProfiles;
    // Entrada de dados de pesquisa
    @FXML private TextField txfSearch;
    // Hashmap de ids e usuários
    private ObservableMap<Long, SProfile> profiles;    
    private Engine engine;
    
    /**
     * Script de inicialização
     */
    @FXML public void initialize() {
        engine = Engine.getInstance();
        this.listViewInitializer();
    }
    
    /**
     * Controi listview com base na modificação da hashmap
     */
    private void listViewInitializer(){
        profiles = FXCollections.observableHashMap();
        lvwProfiles.getItems().addAll(profiles.values());
        /**
         * Vincula modificação do objeto observavel aos itens da lista
         */
        profiles.addListener((MapChangeListener.Change<? extends Long, ? extends SProfile> change) -> {
            Platform.runLater(() -> {
                if(change.wasAdded()){
                    lvwProfiles.getItems().add(change.getValueAdded());
                } else if (change.wasRemoved()){
                    lvwProfiles.getItems().remove(change.getValueRemoved());
                }
            });
        });
        // Cria vinculo com FXML
        lvwProfiles.setCellFactory((ListView<SProfile> param) -> new CProfileCell());
    }
    
    /**
     * Inicia comunicação com o servidor de mensagens
     * em busca de lista de amigos
     * @param event 
     */
    @FXML public void searchAction(ActionEvent event){
        if(this.validateSearch()){
            String text = this.txfSearch.getText();
            this.engine.sendProfileListRequest(text);
        }
    }
    
    /**
     * Valida campo de pesquisa
     * @return resultado
     */
    public boolean validateSearch(){
        txfSearch.getStyleClass().remove("error");
        if(txfSearch.getText().length() < 2){
            txfSearch.getStyleClass().add("error");
            return false;
        }
        return true;
    }
    
    /**
     * Alimenta lista com itens provenientes de resposta do servidor
     * @param message 
     */
    public void profileListBinding(CProtocol message){
        this.profiles.clear();
        ArrayList<SProfile> list = (ArrayList<SProfile>) message.getPayload();
        for(SProfile profile: list){
            this.profiles.put(profile.getId(), profile);
        }
    }
}