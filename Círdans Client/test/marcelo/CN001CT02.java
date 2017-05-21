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

/**
 * Cenário de teste de primeiro uso
 * 
 * @author Marcelo Gomes Martins
 */
public class CN001CT02 extends CirdanTestGui {
    
    /**
     * Teste simple de login
     * 
     * Pré requisitos:
     * Já ter executado o caso de teste CN001CT01
     * @see CN001CT01.java
     */
    @Test public void test()
    {
        click("#txfEmail").
                type(EMAIL_BODY).
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
}
