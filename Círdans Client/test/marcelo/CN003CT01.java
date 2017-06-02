package marcelo;

import javafx.scene.layout.Pane;
import static junit.framework.Assert.assertTrue;
import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import marcelo.interfaces.IAfterTest;
import marcelo.interfaces.IBeforeTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Cenário de teste de gerenciamento de status
 * 
 * @author Marcelo Gomes Martins
 */
public class CN003CT01 extends CirdanTestGui implements IBeforeTest, IAfterTest{
    
    /**
     * Teste de mudança de status 
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     */
    @Test public void test()
    {
        doLogin(FULL_SMALL_EMAIL, PASS);
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
        assertTrue(colorMatch);
        click("Ausente");
        click("Disponível");
        sleep(1000);
    }

    @Override @Before public void beforeTest() {
        createProfile(NAME, NICK, FULL_SMALL_EMAIL, PASS);
    }

    @Override @After public void afterTest() {
        deleteProfile(FULL_SMALL_EMAIL);
    }
    
}
