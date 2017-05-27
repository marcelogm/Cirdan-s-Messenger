package marcelo;

import facade.Facade;
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
 * Cenário de teste de gerenciamento de amigos 
 * 
 * @author Marcelo Gomes Martins
 */
public class CN004CT07 extends CirdanTestGui implements IBeforeTest, IAfterTest{
    
    /**
     * Teste de envio de "chamar a atenção"
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     * Possuir outro usuário ONLINE com vínculo de amizade ativo e que 
     * possua o nome Constants.FRIEND_NAME
     */
    @Test public void test(){
        doLogin(FULL_SMALL_EMAIL, PASS);
        click(ONLINE_FRIEND_NAME).click(ONLINE_FRIEND_NAME);
        click("Chamar a atenção");
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
