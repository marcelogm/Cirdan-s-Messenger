package util;

import controller.CRegister;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Reading;
import facebook4j.User;
import facebook4j.auth.AccessToken;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe que gerencia interações com a API do facebook
 * @author Marcelo Gomes Martins
 */
public class FacebookConnector {
    // Propria referencia
    private static FacebookConnector self;
    // Id do aplicativo
    private final String appId = "xxx";
    // Palava secreta do aplicativo
    private final String secret = "xxx";
    // Retorna para página de sucesso
    private final String redirectTo = "https://www.facebook.com/connect/login_success.html";
    // URL da requisição
    private final URL requestUri;
    // Token do cliente
    private String clientToken;
    // Intancia de framework de conexão
    private Facebook connection;
    // Stage do webview
    private Stage stage;
    
    /**
     * Recupera instance da classe
     * @return 
     */
    public static FacebookConnector getInstance(){
        if(self == null) {
            try {
                self = new FacebookConnector();
            } catch (IOException ex) {
                Logger.getLogger(FacebookConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return self;
    }
    
    /**
     * Inicia conexão e mostra stage
     * @throws MalformedURLException 
     */
    private FacebookConnector() throws MalformedURLException {
        requestUri = new URL(
                "https://www.facebook.com/v2.4/dialog/oauth?" +
                        "client_id=" + this.appId +
                        "&redirect_uri=" + this.redirectTo +
                        "&response_type=token" + 
                        "&display=page" + 
                        "&scope=email,public_profile,user_friends"
        );
        this.stage = new Stage();
        this.stage.setTitle("Conecte com o Facebook.");
        this.stage.setAlwaysOnTop(true);
        this.stage.setResizable(false);
    }
    
    /**
     * Fecha dialogo com webview
     */
    public void closeDialog(){
        this.stage.close();
        this.apiConnect();
    }
    
    /**
     * Mostra dialogo com webview
     */
    public void showDialog() {
        if(!this.stage.showingProperty().get()){
            this.stage.setAlwaysOnTop(true);
            this.newScene();
        }
    }
    
    /**
     * Abre uma nova cena com webview
     */
    public void newScene(){
        try {
            URL location = getClass().getResource("/app/gui/GFacebook.fxml");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(location);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = (Parent)loader.load(location.openStream());
            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.show();
        } catch (IOException ex) {
            System.out.println("IOException@newScene@FacebookConnector");
        } catch (IllegalArgumentException ex){
            System.out.println("IllegalArgumentException@newScene@FacebookConnector");
        } 
    }

    /**
     * Faz o request para a API por meio do framework
     */
    public void apiConnect(){
        if(this.clientToken != null){
            try {
                this.setConnectionConfiguration(new AccessToken(this.clientToken, null));
                User user = this.connection.getMe(new Reading().fields("name,email,picture.type(large)"));
                this.sendFacebookInfoToController(user.getName(), user.getEmail(), user.getPicture().getURL().toString());
            } catch (FacebookException ex) {
                System.out.println("apiConnect@FacebookConnector");
            }
        }
    }
    
    /**
     * Envia as informações recuperadas pela API para o controller de registro
     * @param name
     * @param email
     * @param imageUrl 
     */
    private void sendFacebookInfoToController(String name, String email, String imageUrl){
        UScene sm = UScene.getInstance();
        CRegister rc = (CRegister) sm.getCurrentController();
        rc.bindFacebookInfo(name, email, imageUrl);
        
    }
    
    /**
     * Constroi conexão com a API com base nos dados recuperados pelo webview
     * @param token 
     */
    private void setConnectionConfiguration(AccessToken token){
        this.connection = new FacebookFactory().getInstance();
        this.connection.setOAuthAppId(this.appId, this.secret);
        this.connection.setOAuthPermissions("email,public_profile,user_friends");
        this.connection.setOAuthAccessToken(token);
    }
    
    public URL getRequestUri() { return requestUri; }
    public void setClientToken(String clientToken) { this.clientToken = clientToken; }
}
