package app.gui.component;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import protocol.EStatus;
import protocol.model.SProfile;

/**
 * Classe Controlladora do ListCell
 * @author Marcelo Gomes Martins
 */
public class CFriendCell extends AProfileCell {
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
        if(friend == null){
            this.setText(null);
            this.setGraphic(null);
        } else {
            loader = new FXMLLoader(getClass().getResource("/app/gui/GFriendListCell.fxml"));
            loader.setController(this);
            try{
                loader.load();
            } catch(IOException e){
                System.out.println("updateItem@CFriendListCell");
            }
            this.lblName.setText(friend.getNick());
            this.lblSubItem.setText(friend.getSubnick());
            this.loadImage(friend.getImageUrl());
            this.profileStatusSet(EStatus.get(friend.getStatus()));
            this.setText(null);
            this.setGraphic(hbxGrid);
        }      
    }
    /**
     * Modifica Pane de status de acordo com informação do usuárop
     * @param friend 
     */
    private void profileStatusSet(EStatus status){
        this.profileStatusBorderReset();        
        switch(status){
            case ONLINE:
                panProfileBorder.getStyleClass().add("online");
                break;
            case BUSY:
                panProfileBorder.getStyleClass().add("busy");
                break;
            case AWAY:
                panProfileBorder.getStyleClass().add("away");
                break;
            default:
                panProfileBorder.getStyleClass().add("offline");
                break;
        }
    }
    
    /**
     * Limpa os estilos do Pane de status
     */
    private void profileStatusBorderReset(){
        panProfileBorder.getStyleClass().remove("away");
        panProfileBorder.getStyleClass().remove("online");
        panProfileBorder.getStyleClass().remove("busy");
        panProfileBorder.getStyleClass().remove("offline");
    }
    
    public void initialize() {}
}
