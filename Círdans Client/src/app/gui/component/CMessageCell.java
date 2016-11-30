/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.gui.component;

import engine.Engine;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import protocol.model.SMessage;

/**
 *
 * @author 00271922
 */
public class CMessageCell extends ListCell<SMessage>{
    private FXMLLoader loader;
    
    @FXML private AnchorPane anpMain;
    @FXML private Label lblName;
    @FXML private Label lblDate;
    @FXML private Label lblMessage;
    
    @Override
    protected void updateItem(SMessage message, boolean empty){
        super.updateItem(message, empty);
        if(message == null){
            this.setText(null);
            this.setGraphic(null);
        } else {
            Engine engine = Engine.getInstance();
            if(engine.getClient().id == message.getSenderId()){
                loader = new FXMLLoader(getClass().getResource("/app/gui/GSentMessage.fxml"));
            } else {
                loader = new FXMLLoader(getClass().getResource("/app/gui/GReceivedMessage.fxml"));
            }
            loader.setController(this);
            try{
                loader.load();
            } catch(IOException e){
                System.out.println("updateItem@CMessageCell");
            }
            this.selfBinding(message);
            this.setText(null);
            this.setGraphic(anpMain);
            
        }      
    }
    
    private void selfBinding(SMessage message){
        this.lblDate.setText(message.getSentAt().toString());
        this.lblMessage.setText(message.getMessage());
        this.lblName.setText(message.getSenderName());
    }
    
    public void initialize() {
    }
}