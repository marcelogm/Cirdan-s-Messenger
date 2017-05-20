/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marcelo;

import java.util.Set;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import junit.framework.Assert;
import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import org.junit.Test;

/**
 *
 * @author marce
 */
public class CN002CT02 extends CirdanTestGui {
    
    @Test
    public void test()
    {
        preExecute();
        click(FRIED_NAME).click(FRIED_NAME);
        click("#txaInput").type(MESSAGE).type(KeyCode.ENTER);
        Set<Node> messages = findAll("#lblMessage");
        boolean hasMessage = false;
        for(Node message : messages)
        {
            if(((Label)message).getText().equals(MESSAGE))
            {
                hasMessage = true;
            }
        }
        Assert.assertTrue(hasMessage);
        sleep(2000);
    }
    
}
