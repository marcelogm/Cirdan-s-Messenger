package app.console;

import util.UScene;
import engine.Engine;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    // Variavel de modo de desenvolvimento.
    // Ativa algumas funções durante o código
    public static boolean DEBUG;
    
    @Override
    public void start(Stage stage) throws Exception {
        Main.DEBUG = true;
        Engine engine = Engine.getInstance();
        UScene smanager = UScene.getInstance();
        smanager.setStage(stage);
        smanager.loadScene("GLogin");
        smanager.showStage("Círdan's Messenger Login", false);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
