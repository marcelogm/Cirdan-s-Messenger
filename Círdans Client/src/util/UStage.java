package util;

import controller.AController;
import java.io.IOException;
import java.net.URL;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * Classe Gerenciadora da cena principal
 * @author marce
 */
public class UStage {
    // Referencia prórpia
    private static UStage smanager;
    // Stage principal
    private Stage stage;
    // Laoder
    private FXMLLoader loader;
    
    private int x, y;
    
    private UStage(){}
    
    /**
     * Recupera instance do gerenciador
     * @return 
     */
    public static UStage getInstance(){
        if(smanager == null) {
            smanager = new UStage();
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
    
    /**
     * Balança o stage
     */
    public void shakeStage() {
        this.x = 0;
        this.y = 0;
        this.timelineYShake();
        this.timelineXShake();
    }
    
    /**
     * Mexe tela no angulo Y
     */
    public void timelineYShake(){
        Timeline timeY = new Timeline(new KeyFrame(Duration.seconds(0.05), (ActionEvent t) -> {
            if (y == 0) {
                stage.setY(stage.getY() + 4);
                y = 1;
            } else {
                stage.setY(stage.getY() - 4);
                y = 0;
            }
        }));

        timeY.setCycleCount(8);
        timeY.setAutoReverse(false);
        timeY.play();
    }
    
    /**
     * Mexe tela no angulo X
     */
    public void timelineXShake(){
        Timeline timeX = new Timeline(new KeyFrame(Duration.seconds(0.05), (ActionEvent t) -> {
            if (x == 0) {
                stage.setX(stage.getX() + 4);
                x = 1;
            } else {
                stage.setX(stage.getX() - 4);
                x = 0;
            }
        }));

        timeX.setCycleCount(8);
        timeX.setAutoReverse(false);
        timeX.play();
    }
}
