/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marcelo;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import junit.framework.Assert;
import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import org.junit.Test;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.GuiTest.find;
import static org.loadui.testfx.controls.Commons.hasText;

/**
 *
 * @author marce
 */
public class CN005CT03 extends CirdanTestGui{
    
    @Test
    public void test(){
        char first = EMAIL_BODY.charAt(0);
        if(Character.isLowerCase(first)){
            first = Character.toUpperCase(first);
        } else {
            first = Character.toLowerCase(first);
        }
        click("#txfEmail").
                type(first + EMAIL_BODY.substring(1)).
                press(KeyCode.SHIFT).
                press(KeyCode.DIGIT2).
                release(KeyCode.SHIFT).
                release(KeyCode.DIGIT2).
                type(EMAIL_SERVER);
        click("#psfPassword").type(PASS);
        click("#btnLogin");
        sleep(2000);
        verifyThat("#lblFeedback", hasText("O email digitado não está cadastrado."));
        TextField name = find("#txfEmail");
        boolean colorMatch = false;
        for(String style: name.getStyleClass()){
           if(style.contains("error"))
           {
               colorMatch = true;
           }
        }
        Assert.assertTrue(colorMatch);
    }
    
}
