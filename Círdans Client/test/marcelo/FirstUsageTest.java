/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marcelo;

import app.console.Main;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.Assertions;
import static org.loadui.testfx.Assertions.assertNodeExists;
import static org.loadui.testfx.Assertions.verifyThat;
import org.loadui.testfx.GuiTest;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.loadui.testfx.controls.impl.VisibleNodesMatcher.visible;
import org.loadui.testfx.utils.FXTestUtils;
import static org.testfx.util.NodeQueryUtils.isVisible;
import util.UStage;

/**
 *
 * @author marce
 */
public class FirstUsageTest extends GuiTest{

    private final String EMAIL_BODY = "testeauto1";
    private final String EMAIL_SERVER = "hotmail.com";
    private final String NAME = "Marcelo Gomes Martins";
    private final String NICK = "MarceloGM1911";
    private final String PASS = "testando";
    private final String REPASS = "t3st4ndo";
    
    @Override
    protected Parent getRootNode() {
        Main.DEBUG = false;
        UStage smanager = UStage.getInstance();
        smanager.setStage(stage);
        try {
            smanager.loadScene("GLogin");
        } catch (IOException ex) {
            Logger.getLogger(FirstUsageTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        smanager.showStage("Círdan's Messenger Login", false);
        Parent root = smanager.getStage().getScene().getRoot();
        smanager.getStage().getScene().setRoot(new Region());
        return root;
    }

    @Test
    public void test() {
        try {
            click("#btnRegistration");
            click("#txfName").type(NAME);
            click("#txfNick").type(NICK);
            click("#txfEmail").
                    type(EMAIL_BODY).
                    press(KeyCode.SHIFT).
                    press(KeyCode.DIGIT2).
                    release(KeyCode.SHIFT).
                    release(KeyCode.DIGIT2).
                    type(EMAIL_SERVER);
            click("#pdfPass").type(PASS);
            click("#pdfRepass").type(PASS);
            click("#btnRegister");
            assertNodeExists("#lblProfileName");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void wait(int i, TimeUnit timeUnit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
