package util;

import controller.AController;
import java.io.IOException;
import java.net.URL;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Classe Gerenciadora da cena principal
 * @author marce
 */
public class UScene {
    // Referencia prórpia
    private static UScene smanager;
    // Stage principal
    private Stage stage;
    // Laoder
    private FXMLLoader loader;
    
    private UScene(){}
    
    /**
     * Recupera instance do gerenciador
     * @return 
     */
    public static UScene getInstance(){
        if(smanager == null) {
            smanager = new UScene();
        }
        return smanager;
    }
    
    /**
     * Mostra stage principal
     * @param title
     * @param resizable 
     */
    public void showStage(String title, boolean resizable){
        this.stage.setTitle(title);
        this.stage.setResizable(resizable);
        this.stage.show();
    }
    
    /**
     * Carrega novoa Scene para dentro do Stage principal
     * @param url
     * @throws IOException 
     */
    public void loadScene(String url) throws IOException{
        URL location = getClass().getResource("/app/gui/" + url + ".fxml");
        this.loader = new FXMLLoader();
        this.loader.setLocation(location);
        this.loader.setBuilderFactory(new JavaFXBuilderFactory());
        
        Parent root = (Parent)this.loader.load(location.openStream());
        
        AController controller = this.loader.getController();
        controller.setSceneManager(smanager);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);  
    }
    
    /**
     * Recupera controller atual do stage principal
     * @return 
     */
    public AController getCurrentController(){
        AController controller = this.loader.getController();
        return controller;
    }
    
    public Stage getStage() { return stage; }
    
    /**
     * Seta e cria novo Stage
     * @param stage 
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
    }
}
