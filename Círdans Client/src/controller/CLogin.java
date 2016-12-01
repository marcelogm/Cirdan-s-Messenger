package controller;

import engine.Engine;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import protocol.EResponse;
import util.UPattern;

/**
 * Controlador de Login
 * @author Marcelo Gomes Martins
 */
public class CLogin extends ACForm {
    // Email digitado pelo usuário
    @FXML private TextField txfEmail;
    // Senha digitada pelo usuário
    @FXML private PasswordField psfPassword;
    // Botão de registro
    @FXML private Button btnRegistration;
    // Botão de login
    @FXML private Button btnLogin;
    // Barra de progresso de tentativa de conexão
    @FXML private ProgressBar pgbConnect;
    
    public void initialize() {
        Engine engine = Engine.getInstance();
    }
    
    /**
     * Tratador de eventos de click em botões na Scene
     * @param ae botão clickado
     */
    @FXML private void buttonHandler(ActionEvent ae){
        Button btn = (Button)ae.getSource();
        switch (btn.getId()) {
            case "btnRegistration":
                this.registrationAction();
                break;
            case "btnLogin":
                this.loginAction();
                break;
        }
    }
    
    /**
     * Realização verificação dos campos e 
     * tenta realizar o login
     */
    private void loginAction(){
        String email = txfEmail.getText();
        String password = psfPassword.getText();
        if(loginFieldsVerifications(email, password)){
            Platform.runLater(() -> {
                // Feedback de inicio de processo
                this.buttonSetDisable(true);
                this.pgbConnect.setVisible(true);
                this.setConnMessage();
                // Envia requisição de login
                this.serverInputDialog();
                EResponse response = this.loginRequest(email,password);
                this.loginHandler(response);
                // Feedback de final de processo
                this.resetConnMessage();
                this.pgbConnect.setVisible(false);
                buttonSetDisable(false);
            });
        }
    }
    
    /**
     * Inicia Controller de Registro
     */
    private void registrationAction(){
        try {
            sceneManager.loadScene("GRegister");
            sceneManager.showStage("Círdan's Messenger Registro", false);
        } catch (IOException ex) {
            System.out.println("registrationAction@CLogin");
        }
    }
    
    /**
     * Dispara ação de conexão com o servidor para login
     * @param email
     * @param password
     * @return resposta do servidor
     */
    private EResponse loginRequest(String email, String password){
        Engine engine = Engine.getInstance();
        EResponse response = engine.sendLoginRequest(email,password);
        return response;
    }
    
    /**
     * Trata resposta do servidor realizando o login,
     * ou exibindo mensagem de erro
     * @param response resposta do servidor
     */
    private void loginHandler(EResponse response){
        switch(response){
            case AUTH_SUCCESSFUL:
            {
                try {
                    sceneManager.loadScene("GMain");
                    sceneManager.showStage("Círdan's Messenger", true);
                } catch (IOException ex) {
                    System.out.println("loginHandler@CLogin");
                }
                break;
            }
            case EMAIL_NOT_FOUND:
                setEmailErrorMessage();
                break;
            case PASSWORD_DOESNT_MATCH:
                setPasswordErrorMessage();
                break;
            case ERROR:
                setConnErrorMessage();
                break;
            default:
                setDefaultErrorMessage();
                break;
        }
    }
    
    /**
     * Modifica possibilidade click em botão
     * @param bool 
     */
    private void buttonSetDisable(boolean bool){
        this.btnLogin.setDisable(bool);
        this.btnRegistration.setDisable(bool);
    }
    
    /**
     * Modifica mensagem do botão de login
     */
    private void setConnMessage(){ this.btnLogin.setText("Conectando..."); }
    
    /**
     * Retorna mensagem original ao botão de login
     */
    private void resetConnMessage(){ this.btnLogin.setText("Entrar"); }
       
    /**
     * Realiza a verificação dos campos digitados
     * @param email
     * @param password
     * @return resultado
     */
    private boolean loginFieldsVerifications(String email, String password){
        this.resetMessageFields();
        
        boolean pattern = UPattern.execute(email, UPattern.EMAIL_PATTERN);
        boolean passwordSize = (password.length() > 3);
        
        if(pattern && passwordSize){
            return true;
        } else if (!pattern){
            this.setInvalidMessage(this.txfEmail, "Insira um email válido!");
        } else {
            this.setInvalidMessage(this.psfPassword, "Insira uma senha válida!");
        }
        return false;
    }
    
    /**
     * Exibe erro de email não cadastrado
     */
    private void setEmailErrorMessage(){
        txfEmail.getStyleClass().add("error");
        lblFeedback.setText("O email digitado não está cadastrado.");
    }
    
    /**
     * Exibe mensagem de password errado
     */
    private void setPasswordErrorMessage(){
        txfEmail.getStyleClass().add("error");
        lblFeedback.setText("A senha informada não confere.");
    }
    
    /**
     * Retorna feedback ao original
     */
    private void resetMessageFields(){
        txfEmail.getStyleClass().remove("error");
        psfPassword.getStyleClass().remove("error");
        lblFeedback.setText("");
    }
}