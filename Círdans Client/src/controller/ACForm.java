package controller;

import app.console.Main;
import engine.Engine;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

/**
 * Controlador abstrato para Formulários
 * @author Marcelo Gomes Martins
 */
public abstract class ACForm extends AController{
    // Exibe mensagens de alerta ao usuário
    @FXML protected Label lblFeedback;
    
    /**
     * Exibe mensagem de erro padrão
     */
    protected void setDefaultErrorMessage(){
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Erro");
        dialog.setHeaderText("Algo inesperado aconteceu.");
        dialog.setContentText("Falha ao realizar o login, por favor informar desenvolvedor.");
        dialog.showAndWait();
    }
    
    /**
     * Exibe mensagem de erro de conexão
     */
    protected void setConnErrorMessage(){
        Alert dialog = new Alert(Alert.AlertType.WARNING);
        dialog.setTitle("Falha na conexão");
        dialog.setHeaderText("Você está sem acesso ao servidor de mensagens.");
        dialog.setContentText("Verifique sua conexão e tente novamente mais tarde.");
        dialog.showAndWait();
    }
    
    /**
     * Exibe aviso simples em lblFeedback
     * @param target campo que receberá destaque
     * @param message mensagem para lblFeedback
     */
    protected void setInvalidMessage(TextField target, String message){
        target.getStyleClass().add("error");
        lblFeedback.setText(message);
    }
    
    /**
     * Opção de desenvolvimento que solicita o ip do servidor
     * de destino de mensagens
     */
    protected void serverInputDialog(){
        if(Main.DEBUG){
            TextInputDialog dialog = new TextInputDialog("localhost");
            dialog.setTitle("Modo de desenvolvimento");
            dialog.setHeaderText("Digite o endereço do servidor de mensagem:");
            dialog.setContentText("Servidor de mensagem:");

            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()){
                Engine engine = Engine.getInstance();
                engine.setServerIpv4(result.get());
            }
        }
    }
}
