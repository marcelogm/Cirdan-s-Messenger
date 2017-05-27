package marcelo;

import controller.CMain;
import javafx.scene.input.KeyCode;
import junit.framework.Assert;
import org.junit.Test;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import util.UStage;
import static marcelo.util.Constants.*;
import marcelo.util.CirdanTestGui;
import marcelo.interfaces.IAfterTest;
import org.junit.After;

/**
 * Cenário de teste de primeiro uso
 * 
 * @author Marcelo Gomes Martins
 */
public class CN001CT01 extends CirdanTestGui implements IAfterTest {

    /**
     * Teste do cadastro de um novo usuário no programa
     * 
     * Pré requisitos:
     * Ter o software instalado
     * 
     * @alterado o login foi realizado pelo formulário padrão
     * e não pelo login no Facebook
     */
    @Test public void test() {
        click("#btnRegistration");
        click("#txfName").type(NAME);
        click("#txfNick").type(NICK);
        click("#txfEmail").
                type(SMALL_EMAIL_BODY).
                press(KeyCode.SHIFT).
                press(KeyCode.DIGIT2).
                release(KeyCode.SHIFT).
                release(KeyCode.DIGIT2).
                type(EMAIL_SERVER);
        click("#pdfPass").type(PASS);
        click("#pdfRepass").type(PASS);
        click("#btnRegister");
        Assert.assertEquals(UStage.getInstance().getCurrentController().getClass(), CMain.class);
        verifyThat("#lblNickname", hasText(NICK));
        verifyThat("#lblProfileName", hasText(NAME));
    }
    
    @Override @After public void afterTest() {
        this.deleteProfile(FULL_SMALL_EMAIL);
    }
}
