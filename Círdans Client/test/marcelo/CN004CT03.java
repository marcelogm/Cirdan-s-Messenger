/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marcelo;

import javafx.scene.control.TextField;
import marcelo.util.CirdanTestGui;
import org.junit.Assert;
import org.junit.Test;
import static org.loadui.testfx.GuiTest.find;

/**
 *
 * @author marce
 */
public class CN004CT03 extends CirdanTestGui {
    @Test
    public void test(){
        preExecute();
        openNewFriendScreen();
        click("Pesquisar");
        TextField search = find("#txfSearch");
        boolean colorMatch = false;
        for(String style: search.getStyleClass())
        {
           if(style.contains("error"))
           {
               colorMatch = true;
           }
        }
        Assert.assertTrue(colorMatch);
        sleep(1000);
    }
}
