package guilherme.test;

import javafx.scene.control.Button;
import guilherme.util.CirdanTestGui;
import static org.junit.Assert.*;
import static util.Constants.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class test04 extends CirdanTestGui {
    @Test
    public void test() {
        //adicionar amigo
        doLogin(EMAIL_TESTE, "1234");
        click("Op��es");
        click("Adicionar amigo");
        click("#txfSearch").type("teste2");
        click("Pesquisar");
        click("#btnAdd");

        Button button = (Button) find("#btnAdd");
        sleep(1000);
        Boolean desativado = (button.disabledProperty().get());
        sleep(1000);

        assertTrue(desativado);
    }

    @Before
    public void beforeTest() {
        this.createProfile("teste", "teste", EMAIL_TESTE, "1234");
        this.createProfile("teste2", "test2", EMAIL_TESTE2, "1234");
    }

    @After
    public void afterTest() {
        this.deleteFriendship(EMAIL_TESTE, EMAIL_TESTE2);
        this.deleteProfile(EMAIL_TESTE);
        this.deleteProfile(EMAIL_TESTE2);
    }
}
