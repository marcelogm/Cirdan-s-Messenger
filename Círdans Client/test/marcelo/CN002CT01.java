package marcelo;

import java.util.Set;
import javafx.scene.Node;
import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Cenário de teste de utilização básica
 * 
 * @author Marcelo Gomes Martins
 */
public class CN002CT01 extends CirdanTestGui {
    
    /**
     * Teste de adicionar novo amigo
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     * Outro usuário cadastrado no banco de dados utilizando o nome
     * Constants.FRIEND_NAME que NÃO seja amigo do usuário de teste
     */
    @Test public void test()
    {
        preExecute();
        click("Opções").click("Adicionar amigo");
        click("#txfSearch").type(FRIED_NAME);
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
}
