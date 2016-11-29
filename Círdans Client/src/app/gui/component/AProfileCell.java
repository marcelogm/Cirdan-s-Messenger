/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app.gui.component;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import protocol.model.SProfile;

/**
 * Classe Abstrata de Controladora de ListCell vinculadas a Perfils
 * @author Marcelo Gomes Martins
 */
public abstract class AProfileCell extends ListCell<SProfile>{
    // Grid principal
    @FXML protected HBox hbxGrid;
    // Campo de nome de usuário
    @FXML protected Label lblName;
    // Campo de texto extra exibido
    @FXML protected Label lblSubItem;
    // Pane de borda da imagem de perfil
    @FXML protected Pane panProfileBorder;
    // Loader utilizado para gerar o item na listview
    @FXML protected ImageView imgProfile;
    
    /**
     * Carrega imagem no Pane de imagem de perfil
     * @param url origem da foto
     */
    protected void loadImage(String url){
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
}
