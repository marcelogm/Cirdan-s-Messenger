/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marcelo.util;

import app.console.Main;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import static marcelo.util.Constants.EMAIL_BODY;
import static marcelo.util.Constants.EMAIL_SERVER;
import static marcelo.util.Constants.PASS;
import org.loadui.testfx.GuiTest;
import static org.loadui.testfx.GuiTest.find;
import util.UStage;

/**
 *
 * @author marce
 */
public abstract class CirdanTestGui extends GuiTest {
    
    @Override
    protected Parent getRootNode() {
        Main.DEBUG = false;
        UStage smanager = UStage.getInstance();
        smanager.setStage(stage);
        try {
            smanager.loadScene("GLogin");
        } catch (IOException ex) {
            Logger.getLogger(CirdanTestGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        smanager.showStage("Círdan's Messenger Login", false);
        Parent root = smanager.getStage().getScene().getRoot();
        smanager.getStage().getScene().setRoot(new Region());
        return root;
    }
    
    protected void preExecute()
    {
        TextField email = find("#txfEmail");
        PasswordField password = find("#psfPassword");
        email.setText(EMAIL_BODY + "@" + EMAIL_SERVER);
        password.setText(PASS);
        click("#btnLogin");
        sleep(2000);
    }
    
    protected void openNewFriendScreen(){
        click("Opções").click("Adicionar amigo");
    }
}
