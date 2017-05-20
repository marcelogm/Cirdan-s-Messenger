package marcelo;

import java.util.Set;
import javafx.scene.Node;
import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author marce
 */
public class CN002CT01 extends CirdanTestGui {
    @Test
    public void test()
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
