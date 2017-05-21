package marcelo;

import java.util.Set;
import javafx.scene.Node;
import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Caso de teste de gerenciamento de amigos
 * 
 * @author Marcelo Gomes Martins
 */
public class CN004CT01 extends CirdanTestGui {
    
    /**
     * Teste de convite de novo amigo utilizando parte do nome de usuário
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     * Possuir outros usuários cadastrados que 
     * tenham ANOTHER_FRIEND_NAME.substring(2, 6) e não possuam vínculo
     * de amizade
     */
    @Test public void test(){
        preExecute();
        click("Opções").click("Adicionar amigo");
        click("#txfSearch").type(ANOTHER_FRIEND_NAME.substring(2, 6));
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
