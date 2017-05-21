/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marcelo;

import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.FRIED_NAME;
import org.junit.Test;

/**
 *
 * @author marce
 */
public class CN004CT07 extends CirdanTestGui{
    
    @Test
    public void test(){
        preExecute();
        click(FRIED_NAME).click(FRIED_NAME);
        click("Chamar a atenção");
    }
}
