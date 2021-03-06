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

public class test08 extends CirdanTestGui {

    @Test
    public void test() {
        //teste mandar msg para amigo offline
        click("#txfEmail").
                type("teste").
                press(KeyCode.SHIFT).
                press(KeyCode.DIGIT2).
                release(KeyCode.SHIFT).
                release(KeyCode.DIGIT2).
                type("teste.com");
        click("#psfPassword").type("1234");
        click("#btnLogin");
        //sleep(2, TimeUnit.SECONDS);
        click("Sim");
        sleep(2, TimeUnit.SECONDS);
        click("#imgProfile");
        sleep(2, TimeUnit.SECONDS);

    }

    @Before
    public void beforeTest() {
        this.createProfile("teste", "teste", EMAIL_TESTE, "1234");
        this.createProfile("teste2", "test2", EMAIL_TESTE2, "1234");
        Facade f = Facade.getInstance();
        Profile sender = f.findProfileByEmail(EMAIL_TESTE2);
        Profile reciever = f.findProfileByEmail(EMAIL_TESTE);
        f.save(new Friendship(sender.getId(), reciever.getId()));
    }

    @After
    public void afterTest() {
        this.deleteFriendship(EMAIL_TESTE, EMAIL_TESTE2);
        this.deleteProfile(EMAIL_TESTE);
        this.deleteProfile(EMAIL_TESTE2);
    }
}
