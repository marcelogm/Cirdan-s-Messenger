package controller;

import app.console.Main;
import engine.Engine;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import protocol.EResponse;
import util.FacebookConnector;
import util.UPattern;

/**
 * Classe controladora de tela de registro
 * @author Marcelo Gomes Martins
 */
public class CRegister extends ACForm {
    // Nome
    @FXML private TextField txfName;
    // Nickname
    @FXML private TextField txfNick;
    // Email
    @FXML private TextField txfEmail;
    // Senha
    @FXML private PasswordField pdfPass;
    // Redigitação da senha
    @FXML private PasswordField pdfRepass;
    // Botão de ação de registro
    @FXML private Button btnRegister;
    // Botão de retorno ao menu principal
    @FXML private Button btnRegistered;
    // Botão de registro com Facebook
    @FXML private Button btnFacebook;
    // Root
    @FXML private VBox vbxMain;
    // Url da foto de perfil
    private String profileImage;

    /**
     * Construtor
     */
    public CRegister() {
        this.profileImage = "";
    }
    
    /**
     * Tratador de click em botão
     * @param ae 
     */
    @FXML private void buttonHandler(ActionEvent ae){
        Button btn = (Button)ae.getSource();
        switch (btn.getId()) {
            case "btnRegistered":
                this.loadLoginAction();
                break;
            case "btnFacebook":
                this.facebookRequestAction();
                break;
            case "btnRegister":
                this.registratioAction();
                break;
        }
    }
    
    /**
     * Carrega página de login,
     * retornando ao menu anterior
     */
    private void loadLoginAction(){
        try {
            sceneManager.loadScene("GLogin");
            sceneManager.showStage("Círdan's Messenger Login", false);
        } catch (IOException ex) {
            System.out.println("loadLoginAction@CRegister");
        }
    }
    
    /**
     * Inicia requisição de informações a API do facebook
     * mostra scene responsavel pela navegação web
     */
    private void facebookRequestAction(){
        FacebookConnector fb = FacebookConnector.getInstance();
        fb.showDialog();
    }
    
    /**
     * Alimenta campos com informações do facebook
     * @param name
     * @param email
     * @param imageUrl 
     */
    public void bindFacebookInfo(String name, String email, String imageUrl){
        this.setAndDisableField(this.txfName, name);
        this.setAndDisableField(this.txfEmail, email);
        this.btnFacebook.setText("Conectado.");
        this.btnFacebook.setDisable(true);
        this.profileImage = imageUrl;
    }
    
    /**
     * Desabilita campo que não pode ser editado
     * @param field TextField de campo
     * @param text 
     */
    private void setAndDisableField(TextField field, String text){
        field.setText(text);
        field.setEditable(false);
        field.setDisable(true);
    }
    
    /**
     * Valida campos e da inicio ao processo de registro
     */
    private void registratioAction(){
        String name = this.txfName.getText();
        String normalizedName = UPattern.removeMarks(name);
        String email = this.txfEmail.getText();
        String nick = this.txfNick.getText();
        String pass = this.pdfPass.getText();
        String repass = this.pdfRepass.getText();
        if(Main.DEBUG){
            this.serverInputDialog();
        }
        if(this.loginFieldsVerifications(normalizedName, email, nick, pass, repass)){
            Platform.runLater(() -> {
                this.mainDisable(true);
                this.setConnMessage();
                EResponse response = this.registrationRequest(name, email, nick, pass);
                this.loginHandler(response);
                this.resetConnMessage();
                mainDisable(false);
            });
        }
    }

    /**
     * Envia requisição de novo registro para o servidor
     * @param name
     * @param email
     * @param nick
     * @param pass
     * @return resposta do servidor
     */
    private EResponse registrationRequest(String name, String email, String nick, String pass){
        Engine engine = Engine.getInstance();
        EResponse response = engine.sendRegistrationRequest(name, email, nick, pass, this.profileImage);
        return response;
    }
    
    /**
     * Trata resposta a requisição de registro
     * @param response 
     */
    private void loginHandler(EResponse response){
        switch(response){
            case REGISTRATION_SUCESSFUL:
            {
                try {
                    sceneManager.loadScene("GMain");
                    sceneManager.showStage("Círdan's Messenger Login", true);
                } catch (IOException ex) {
                    Logger.getLogger(CLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case EMAIL_ALREADY_EXISTS:
                setEmailErrorMessage();
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
     * Mensagem de email errado
     */
    private void setEmailErrorMessage(){
        txfEmail.getStyleClass().add("error");
        txfEmail.setDisable(false);
        txfEmail.setEditable(true);
        lblFeedback.setText("O email digitado já está cadastrado.");
    }
    
    /**
     * Modifica stage, proibindo a modificação dos itens
     * @param bool 
     */
    private void mainDisable(boolean bool){
        vbxMain.setDisable(bool);
    }
     
    /**
     * Marca botão com mensagem de conexão
     */
    private void setConnMessage(){
        this.btnRegister.setText("Cadastrando...");
    }
    
    /**
     * Retorna botão ao estado original
     */
    private void resetConnMessage(){
        this.btnRegister.setText("Cadastrar");
    }
    
    /**
     * Realiza a validação dos campos de registro
     * @param name
     * @param email
     * @param nick
     * @param pass
     * @param repass
     * @return resposta
     */
    private boolean loginFieldsVerifications(String name, String email, String nick, String pass, String repass){
        this.resetMessageFields();
        
        boolean ptnEmail = UPattern.execute(email, UPattern.EMAIL_PATTERN);
        boolean ptnName = UPattern.execute(name, UPattern.NAME_PATTERN);
        boolean nickSize = (nick.length() > 2);
        boolean passSize = (pass.length() > 3);
        boolean samePass = (pass.equals(repass));
        
        if(ptnName && ptnEmail && nickSize && passSize && samePass){
            return true;
        } else if (!ptnName){
            this.setInvalidMessage(this.txfName, "Insira um nome válido!");
        } else if (!nickSize){
            this.setInvalidMessage(this.txfNick, "Insira um nick válido!");
        } else if (!ptnEmail){
            this.setInvalidMessage(this.txfEmail, "Insira um email válido!");
        } else if (!passSize){
            this.setInvalidMessage(this.pdfPass, "");
            this.setInvalidMessage(this.pdfRepass, "Insira uma senha válido!");
        } else {
            this.setInvalidMessage(this.pdfPass, "");
            this.setInvalidMessage(this.pdfRepass, "As senhas digitadas não conferem.");
        }
        return false;
    }
    
    /**
     * Retorna ao estado orignal mensagens de erro.
     */
    private void resetMessageFields(){
        this.txfEmail.getStyleClass().remove("error");
        this.txfName.getStyleClass().remove("error");
        this.txfNick.getStyleClass().remove("error");
        this.pdfPass.getStyleClass().remove("error");
        this.pdfRepass.getStyleClass().remove("error");
        lblFeedback.setText("");
    }
    
    public void initialize() { }    
}
