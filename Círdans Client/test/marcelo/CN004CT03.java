package marcelo;

import javafx.scene.control.TextField;
import marcelo.util.CirdanTestGui;
import org.junit.Assert;
import org.junit.Test;
import static org.loadui.testfx.GuiTest.find;

/**
 * Cenário de teste de gerenciamento de amigos
 * 
 * Exatamente igual ao caso de teste anterior
 * @see CN004CT2  
 * @author Marcelo Gomes Martins
 */
public class CN004CT03 extends CirdanTestGui {
    
    @Test public void test(){
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
