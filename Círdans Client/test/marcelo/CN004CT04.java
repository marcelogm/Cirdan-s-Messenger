/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marcelo;

import java.math.BigInteger;
import java.security.SecureRandom;
import marcelo.util.CirdanTestGui;
import org.junit.Test;

/**
 *
 * @author marce
 */
public class CN004CT04 extends CirdanTestGui {
    @Test
    public void test(){
        preExecute();
        openNewFriendScreen();
        click("#txfSearch").type(new BigInteger(40, new SecureRandom()).toString(32));
        click("Pesquisar");
        find("usuário não foi encontrado.");
    }
}
