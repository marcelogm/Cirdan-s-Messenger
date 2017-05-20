/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marcelo;

import java.util.Set;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import junit.framework.Assert;
import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import org.junit.Test;

/**
 *
 * @author marce
 */
public class CN004CT05 extends CirdanTestGui {
    
    @Test
    public void test(){
        preExecute();
        click("Opções").click("Adicionar amigo");
        click("#txfSearch").type(ANOTHER_FRIEND_NAME);
        click("Pesquisar");
        click("Adicionar");
        click(FRIED_NAME).click(FRIED_NAME);
        TextArea input = find("#txaInput");
        click("#txaInput");
        for (int i = 0; i < SENT_MESSAGE; i++) {
            input.setText(MESSAGE);
            type(KeyCode.ENTER);
        }
        Set<Node> messages = findAll("#lblMessage");
        int total = 0;
        for(Node message : messages)
        {
            if(((Label)message).getText().equals(MESSAGE))
            {
                total++;
            }
        }
        Assert.assertEquals(total, SENT_MESSAGE);
        sleep(2000);
    }
}
