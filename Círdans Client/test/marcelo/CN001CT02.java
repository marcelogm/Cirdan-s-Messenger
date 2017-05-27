package marcelo;

import controller.CMain;
import javafx.scene.input.KeyCode;
import junit.framework.Assert;
import marcelo.util.CirdanTestGui;
import org.junit.Test;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import util.UStage;
import static marcelo.util.Constants.*;
import marcelo.interfaces.IAfterTest;
import marcelo.interfaces.IBeforeTest;
import org.junit.After;
import org.junit.Before;

/**
 * Cenário de teste de primeiro uso
 * 
 * @author Marcelo Gomes Martins
 */
public class CN001CT02 extends CirdanTestGui implements IAfterTest, IBeforeTest {
    
    /**
     * Teste simple de login
     * 
     * @see CN001CT01.java
     * @implementado trabalho implementado por completo
     */
    @Test public void test() {
        click("#txfEmail").
                type(SMALL_EMAIL_BODY).
                press(KeyCode.SHIFT).
                press(KeyCode.DIGIT2).
                release(KeyCode.SHIFT).
                release(KeyCode.DIGIT2).
                type(EMAIL_SERVER);
        click("#psfPassword").type(PASS);
        click("#btnLogin");
        sleep(3000);
        Assert.assertEquals(UStage.getInstance().getCurrentController().getClass(), CMain.class);
        verifyThat("#lblNickname", hasText(NICK));
        verifyThat("#lblProfileName", hasText(NAME));
    }
    
    /**
     * Pre-requisito: usuário cadastrado conforme CN001CT01
     */
    @Override @Before public void beforeTest() {
        this.createProfile(NAME, NICK, FULL_SMALL_EMAIL, PASS);
    }
    
    @Override @After public void afterTest() {
        this.deleteProfile(FULL_SMALL_EMAIL);
    }
}
