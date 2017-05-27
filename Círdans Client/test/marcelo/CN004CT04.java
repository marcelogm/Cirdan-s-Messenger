package marcelo;

import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import marcelo.interfaces.IAfterTest;
import marcelo.interfaces.IBeforeTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Cenário de teste de gerenciamento de amigos
 * 
 * @author Marcelo Gomes Martins
 */
public class CN004CT04 extends CirdanTestGui implements IAfterTest, IBeforeTest{
    
    /**
     * Teste de pesquisa por usuário inexistente
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     */
    @Test public void test(){
        doLogin(FULL_SMALL_EMAIL, PASS);
        openNewFriendScreen();
        click("#txfSearch").type(FRIEND_NAME);
        click("Pesquisar");
        click("usuário não foi encontrado.");
    }
    
       
    @Override @Before public void beforeTest() {
        this.createProfile(NAME, NICK, FULL_SMALL_EMAIL, PASS);
    }
    
    @Override @After public void afterTest() {
        this.deleteProfile(FULL_SMALL_EMAIL);
    }
}
