package marcelo;

import java.math.BigInteger;
import java.security.SecureRandom;
import marcelo.util.CirdanTestGui;
import org.junit.Test;

/**
 * Cenário de teste de gerenciamento de amigos
 * 
 * @author Marcelo Gomes Martins
 */
public class CN004CT04 extends CirdanTestGui {
    
    /**
     * Teste de pesquisa por usuário inexistente
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     */
    @Test public void test(){
        preExecute();
        openNewFriendScreen();
        click("#txfSearch").type(new BigInteger(40, new SecureRandom()).toString(32));
        click("Pesquisar");
        click("usuário não foi encontrado.");
    }
}
