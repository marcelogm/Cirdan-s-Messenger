package marcelo;

import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.FRIED_NAME;
import org.junit.Test;

/**
 * Cenário de teste de gerenciamento de amigos 
 * 
 * @author Marcelo Gomes Martins
 */
public class CN004CT07 extends CirdanTestGui{
    
    /**
     * Teste de envio de "chamar a atenção"
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     * Possuir outro usuário ONLINE com vínculo de amizade ativo e que 
     * possua o nome Constants.FRIEND_NAME
     */
    @Test public void test(){
        preExecute();
        click(FRIED_NAME).click(FRIED_NAME);
        click("Chamar a atenção");
    }
}
