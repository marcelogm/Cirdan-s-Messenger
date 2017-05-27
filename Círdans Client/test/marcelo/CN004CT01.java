package marcelo;

import facade.Facade;
import java.util.Set;
import javafx.scene.Node;
import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import marcelo.interfaces.IAfterTest;
import marcelo.interfaces.IBeforeTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Caso de teste de gerenciamento de amigos
 * 
 * @author Marcelo Gomes Martins
 */
public class CN004CT01 extends CirdanTestGui implements IAfterTest, IBeforeTest{
    
    /**
     * Teste de convite de novo amigo utilizando parte do nome de usuário
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     * Possuir outros usuários cadastrados que 
     * tenham parte do nome digitado e não possuam vínculo
     * de amizade
     */
    @Test public void test(){
        doLogin(FULL_SMALL_EMAIL, PASS);
        click("Opções").click("Adicionar amigo");
        click("#txfSearch").type(FRIEND_NAME.substring(2, 6));
        click("Pesquisar");
        click("Adicionar");
        Set<Node> buttons = findAll("#btnAdd");
        boolean hasDisable = false;
        for(Node button : buttons)
        {
            if(button.isDisable() == true)
            {
                hasDisable = true;
            }
        }
        Assert.assertTrue(hasDisable);
    }

    @Override @Before public void beforeTest() {
        this.createProfile(NAME, NICK, FULL_SMALL_EMAIL, PASS);
        this.createProfile(FRIEND_NAME, FRIEND_NICK, FRIEND_FULL_SMALL_EMAIL, FRIEND_PASS);
    }
    
    @Override @After public void afterTest() {
        this.deleteFriendship(FULL_SMALL_EMAIL, FRIEND_FULL_SMALL_EMAIL);
        this.deleteProfile(FULL_SMALL_EMAIL);
        this.deleteProfile(FRIEND_FULL_SMALL_EMAIL);
    }

}
