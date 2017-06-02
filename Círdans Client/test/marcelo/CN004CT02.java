package marcelo;

import javafx.scene.control.TextField;
import static junit.framework.Assert.assertTrue;
import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import marcelo.interfaces.IAfterTest;
import marcelo.interfaces.IBeforeTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Cenário de teste de gerenciamento de amigos
 * 
 * @author Marcelo Gomes Martins
 */
public class CN004CT02 extends CirdanTestGui implements IAfterTest, IBeforeTest{
    
    /**
     * Teste de realização pesquisa de amigos em branco
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     */
    @Test public void test()
    {
        doLogin(FULL_SMALL_EMAIL, PASS);
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
        assertTrue(colorMatch);
        sleep(1000);
    }
    
     @Override @Before public void beforeTest() {
        this.createProfile(NAME, NICK, FULL_SMALL_EMAIL, PASS);
        this.createProfile(FRIEND_NAME, FRIEND_NICK, FRIEND_FULL_SMALL_EMAIL, FRIEND_PASS);
    }
    
    @Override @After public void afterTest() {
        this.deleteProfile(FULL_SMALL_EMAIL);
        this.deleteProfile(FRIEND_FULL_SMALL_EMAIL);
    }
    
}
