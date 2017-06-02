package marcelo;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import static junit.framework.Assert.assertTrue;
import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import marcelo.interfaces.IAfterTest;
import marcelo.interfaces.IBeforeTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.GuiTest.find;
import static org.loadui.testfx.controls.Commons.hasText;

/**
 * Cenário de teste de login
 * 
 * @author Marcelo Gomes Martins
 */
public class CN005CT03 extends CirdanTestGui implements IBeforeTest, IAfterTest {
    
    /**
     * Teste de login com email com lower e upper case
     * 
     * Pré requisitos:
     * Ter o software instalado
     */
    @Test public void test(){
        click("#txfEmail").
                type(getCaseChange() + SMALL_EMAIL_BODY.substring(1)).
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
        assertTrue(colorMatch);
    }
    
    /**
     * Pre-requisito: usuário cadastrado conforme CN001CT01
     */
    @Override @Before public void beforeTest() {
        
        this.createProfile(NAME, NICK, getCaseChange() + SMALL_EMAIL_BODY.substring(1), PASS);
    }
    
    @Override @After public void afterTest() {
        this.deleteProfile(getCaseChange() + SMALL_EMAIL_BODY.substring(1));
    }
    
    private char getCaseChange()
    {
        char first = SMALL_EMAIL_BODY.charAt(0);
        return Character.isLowerCase(first)? Character.toUpperCase(first) : Character.toLowerCase(first); 
    }
    
}
