/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import engine.Engine;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import util.UHistory;

/**
 *
 * @author 00271922
 */
public class CHistory extends AController {
    @FXML private ListView<String> lvwHistory;
    private ArrayList<String> list;
    private Long id;
    
    public void initialize() {
        this.listViewInitializer();
    }

    public CHistory() { }
    
    private void listViewInitializer(){ }
    
    public void setMessages(ArrayList<String> list){
        if(list != null){
            this.lvwHistory.getItems().addAll(list);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Não há históricos registrados.");
            alert.setHeaderText("Não temos registros para mostrar no histórico.");
            alert.showAndWait();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
