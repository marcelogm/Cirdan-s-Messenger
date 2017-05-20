/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marcelo;

import javafx.scene.layout.Pane;
import junit.framework.Assert;
import marcelo.util.CirdanTestGui;
import org.junit.Test;

/**
 *
 * @author marce
 */
public class CN003CT01 extends CirdanTestGui {
    
    @Test
    public void test()
    {
        preExecute();
        click("Disponível");
        click("Ausente");
        Pane pane = find("#panProfileBorder");
        boolean colorMatch = false;
        for(String style: pane.getStyleClass()){
           if(style.contains("away"))
           {
               colorMatch = true;
           }
        }
        Assert.assertTrue(colorMatch);
        click("Ausente");
        click("Disponível");
        sleep(1000);
    }
    
}
