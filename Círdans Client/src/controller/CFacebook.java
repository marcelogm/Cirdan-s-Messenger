package controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import util.FacebookConnector;

/**
 * Controlador de conexão com o Facebook
 * @author marce
 */
public class CFacebook extends AController {
    @FXML private WebView wbvMain;
    
    /**
     * Script de inicialização
     */
    public void initialize() {
        FacebookConnector fb = FacebookConnector.getInstance();;
        WebEngine engine = this.createEngine();
        engine.load(fb.getRequestUri().toString());
        /**
         * Listener de mudança de URL atual
         * Caso encontre a URL de sucesso, retorna 
         * Token de acesso do usuário
         */
        engine.locationProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(newValue.contains("https://www.facebook.com/connect/login_success.html")){
                String code = newValue.substring(newValue.indexOf("=") + 1, newValue.indexOf("&exp"));
                fb.setClientToken(code);
                fb.closeDialog();
            }
        });
    }    
    
    /**
     * Cria novo motor com base na WebView
     * @return motor de navegação
     */
    public WebEngine createEngine(){
        WebEngine engine = this.wbvMain.getEngine();
        this.wbvMain.setContextMenuEnabled(false);
        engine.setJavaScriptEnabled(true);
        engine.setUserAgent("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en)AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");
        return engine;
    }
}
