package guilherme.test;

import javafx.scene.input.KeyCode;
import org.junit.Test;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import java.util.concurrent.TimeUnit;
import guilherme.util.CirdanTestGui;

public class test03 extends CirdanTestGui {

    @Test
    public void test() {
        //teste  de login invalido
        click("#txfEmail").
                type("naoexiste").
                press(KeyCode.SHIFT).
                press(KeyCode.DIGIT2).
                release(KeyCode.SHIFT).
                release(KeyCode.DIGIT2).
                type("teste.com");
        click("#psfPassword").type("estasenhanaoexiste");
        click("#btnLogin");
        verifyThat("#lblFeedBack", hasText("O email digitado n�o est� cadastrado."));
        sleep(3, TimeUnit.SECONDS);
    }
}
