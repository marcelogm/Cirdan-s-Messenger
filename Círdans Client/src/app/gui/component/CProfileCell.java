package app.gui.component;

import engine.Engine;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import protocol.model.SProfile;

/**
 * Classe Controladora de ListCell do menu de "adicionar amigo"
 * @author Marcelo Gomes Martins
 */
public class CProfileCell extends AProfileCell {
    // Botão de adicionar amigo vinculado ao profile
    @FXML private Button btnAdd;
    // Referencia ao objeto profile vinculado
    private SProfile self;
    // Loader do fxml
    private FXMLLoader loader;

    /**
     * Função de criação de um item novo na ListView de amigos
     * mostrada no CMain. 
     * Disparado ao modificar objeto observavel
     * @param friend informações do usuário
     * @param empty se está vazio ou não
     */
    @Override
    protected void updateItem(SProfile friend, boolean empty){
        super.updateItem(friend, empty);
        if(friend == null || empty == true){
            this.setText(null);
            this.setGraphic(null);
        } else {
            this.self = friend;
            this.loader = new FXMLLoader(getClass().getResource("/app/gui/GProfileListCell.fxml"));
            this.loader.setController(this);
            try{
                loader.load();
            } catch(IOException e){
                System.out.println("updateItem@CProfileListCell");
            }
            this.lblName.setText(friend.getName());
            this.lblSubItem.setText(friend.getNick());
            this.loadImage(friend.getImageUrl());
            this.panProfileBorder.getStyleClass().add("offline");
            this.setText(null);
            this.setGraphic(hbxGrid);
        }
    }
    
    /**
     * Dispara ação da engine para envio de solicitação de amizade
     * @param ae evento de clique
     */
    @FXML private void friendRequestAction(ActionEvent ae){
        Engine engine = Engine.getInstance();
        engine.sendNewFriendRequest(self.getId());
        this.btnAdd.setDisable(true);
    }
    
    public void initialize() {}
}
