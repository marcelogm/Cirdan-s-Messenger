package marcelo;

import facade.Facade;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import junit.framework.Assert;
import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import marcelo.interfaces.IAfterTest;
import marcelo.interfaces.IBeforeTest;
import model.Friendship;
import model.Profile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Cenário de teste de utilização básica
 * 
 * @author Marcelo Gomes Martins
 */
public class CN002CT02 extends CirdanTestGui implements IAfterTest, IBeforeTest {
    
    /**
     * Teste de envio de mensagem
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     * Possuir outro usuário ONLINE com vínculo de amizade ativo e que 
     * possua o nome Constants.FRIEND_NAME
     */
    @Test public void test()
    {
        doLogin(FULL_SMALL_EMAIL, PASS);
        click(ONLINE_FRIEND_NAME).click(ONLINE_FRIEND_NAME);
        click("#txaInput").type(MESSAGE).type(KeyCode.ENTER);
        Set<Node> messages = findAll("#lblMessage");
        boolean hasMessage = false;
        for(Node message : messages)
        {
            if(((Label)message).getText().equals(MESSAGE))
            {
                hasMessage = true;
            }
        }
        Assert.assertTrue(hasMessage);
        sleep(2000);
    }

    @Override @Before public void beforeTest() {
        this.createProfile(NAME, NICK, FULL_SMALL_EMAIL, PASS);
        Facade f = Facade.getInstance();
        Profile sender = f.findProfileByEmail(FULL_SMALL_EMAIL);
        Profile reciever = f.findProfileByEmail(ONLINE_FRIEND_EMAIL);
        f.save(new Friendship(sender.getId(), reciever.getId()));
        Friendship friendship = f.findFriendshipByProfiles(sender.getId(), reciever.getId());
        friendship.setAccepted(true);
        f.update(friendship);
    }
    
    @Override @After public void afterTest() {
        this.deleteFriendship(FULL_SMALL_EMAIL, ONLINE_FRIEND_EMAIL);
        this.deleteProfile(FULL_SMALL_EMAIL);
    }

    
}
