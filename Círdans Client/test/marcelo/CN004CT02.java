package marcelo;

import javafx.scene.control.TextField;
import marcelo.util.CirdanTestGui;
import org.junit.Assert;
import org.junit.Test;

/**
 * Cenário de teste de gerenciamento de amigos
 * 
 * @author Marcelo Gomes Martins
 */
public class CN004CT02 extends CirdanTestGui {
    
    /**
     * Teste de realização pesquisa de amigos em branco
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     */
    @Test public void test()
    {
        preExecute();
        openNewFriendScreen();
        click("Pesquisar");
        TextField search = find("#txfSearch");
        boolean colorMatch = false;
        for(String style: search.getStyleClass())
        {
           if(style.contains("error"))
           {
               colorMatch = true;
           }
        }
        Assert.assertTrue(colorMatch);
        sleep(1000);
    }
    
}
