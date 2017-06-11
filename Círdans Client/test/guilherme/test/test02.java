package guilherme.test;

import javafx.scene.input.KeyCode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static util.Constants.*;
import guilherme.util.CirdanTestGui;

public class test02 extends CirdanTestGui {

    @Test
    public void test() {
        //teste de login valido
        click("#txfEmail").
                type("teste").
                press(KeyCode.SHIFT).
                press(KeyCode.DIGIT2).
                release(KeyCode.SHIFT).
                release(KeyCode.DIGIT2).
                type("teste.com");
        click("#psfPassword").type("1234");
        click("#btnLogin");
        verifyThat("#lblNickname", hasText("teste"));
    }

    @Before
    public void beforeTest() {
        this.createProfile("teste", "teste", EMAIL_TESTE, "1234");
    }

    @After
    public void afterTest() {
        this.deleteProfile(EMAIL_TESTE);
    }
}
