package guilherme.test;

import facade.Facade;
import javafx.scene.input.KeyCode;
import model.Friendship;
import model.Profile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static util.Constants.*;
import java.util.concurrent.TimeUnit;
import guilherme.util.CirdanTestGui;

public class test09 extends CirdanTestGui {

    @Test
    public void test() {
        //teste abrir o chat.
        click("#txfEmail").
                type("teste").
                press(KeyCode.SHIFT).
                press(KeyCode.DIGIT2).
                release(KeyCode.SHIFT).
                release(KeyCode.DIGIT2).
                type("teste.com");
        click("#psfPassword").type("1234");
        click("#btnLogin");
        click("Sim");
        sleep(2, TimeUnit.SECONDS);
        click("testando");
        sleep(2, TimeUnit.SECONDS);
    }

    @Before
    public void beforeTest() {
        this.createProfile("teste", "teste", EMAIL_TESTE, "1234");
        Facade f = Facade.getInstance();
        Profile sender = f.findProfileByEmail(EMAIL_TESTANDO);
        Profile reciever = f.findProfileByEmail(EMAIL_TESTE);
        f.save(new Friendship(sender.getId(), reciever.getId()));
    }

    @After
    public void afterTest() {
        this.deleteFriendship(EMAIL_TESTE, EMAIL_TESTANDO);
        this.deleteProfile(EMAIL_TESTE);
    }
}
