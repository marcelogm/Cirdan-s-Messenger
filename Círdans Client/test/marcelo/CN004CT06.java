package marcelo;

import facade.Facade;
import marcelo.util.CirdanTestGui;
import marcelo.interfaces.IAfterTest;
import marcelo.interfaces.IBeforeTest;
import static marcelo.util.Constants.*;
import model.Friendship;
import model.Profile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Cenário de teste de gerenciamento de amigos
 * 
 * @author Marcelo Gomes Martins
 */
public class CN004CT06 extends CirdanTestGui implements IAfterTest, IBeforeTest{
    
    /**
     * Teste da funcionalidade de "Decidir mais tarde"
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     * Possuir pedido de amizade pendente
     *
     * @see issue: mensagem nunca mais é mostrada novamente
     */
    @Test public void test(){
        doLogin(FULL_SMALL_EMAIL, PASS);
        click("Decidir mais tarde");
    }

    @Override @Before public void beforeTest() {
        this.createProfile(NAME, NICK, FULL_SMALL_EMAIL, PASS);
        this.createProfile(FRIEND_NAME, FRIEND_NICK, FRIEND_FULL_SMALL_EMAIL, PASS);
        Facade f = Facade.getInstance();
        Profile sender = f.findProfileByEmail(FRIEND_FULL_SMALL_EMAIL);
        Profile reciever = f.findProfileByEmail(FULL_SMALL_EMAIL);
        f.save(new Friendship(sender.getId(), reciever.getId()));
    }
    
    @Override @After public void afterTest() {
        this.deleteFriendship(FULL_SMALL_EMAIL, FRIEND_FULL_SMALL_EMAIL);
        this.deleteProfile(FULL_SMALL_EMAIL);
        this.deleteProfile(FRIEND_FULL_SMALL_EMAIL);
    }
    
}
