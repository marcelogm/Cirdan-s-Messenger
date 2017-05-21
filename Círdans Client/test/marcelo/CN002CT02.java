package marcelo;

import java.util.Set;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import junit.framework.Assert;
import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import org.junit.Test;

/**
 * Cenário de teste de utilização básica
 * 
 * @author Marcelo Gomes Martins
 */
public class CN002CT02 extends CirdanTestGui {
    
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
        preExecute();
        click(FRIED_NAME).click(FRIED_NAME);
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
    
}
