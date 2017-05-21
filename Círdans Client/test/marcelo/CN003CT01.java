package marcelo;

import javafx.scene.layout.Pane;
import junit.framework.Assert;
import marcelo.util.CirdanTestGui;
import org.junit.Test;

/**
 * Cenário de teste de gerenciamento de status
 * 
 * @author Marcelo Gomes Martins
 */
public class CN003CT01 extends CirdanTestGui {
    
    /**
     * Teste de mudança de status 
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     */
    @Test public void test()
    {
        preExecute();
        click("Disponível");
        click("Ausente");
        Pane pane = find("#panProfileBorder");
        boolean colorMatch = false;
        for(String style: pane.getStyleClass()){
           if(style.contains("away"))
           {
               colorMatch = true;
           }
        }
        Assert.assertTrue(colorMatch);
        click("Ausente");
        click("Disponível");
        sleep(1000);
    }
    
}
