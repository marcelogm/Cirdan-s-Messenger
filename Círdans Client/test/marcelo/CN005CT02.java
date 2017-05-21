package marcelo;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import junit.framework.Assert;
import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import org.junit.Test;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.GuiTest.find;
import static org.loadui.testfx.controls.Commons.hasText;

/**
 * Cenário de teste de login
 * 
 * @author Marcelo Gomes Martins
 */
public class CN005CT02 extends CirdanTestGui{
    
    /**
     * Teste do login com usuário inexistente
     * 
     * Pré requisitos:
     * Ter o software instalado
     */
    @Test
    public void test(){
        click("#txfEmail").
                type(RANDOM_EMAIL_BODY).
                press(KeyCode.SHIFT).
                press(KeyCode.DIGIT2).
                release(KeyCode.SHIFT).
                release(KeyCode.DIGIT2).
                type(EMAIL_SERVER);
        click("#psfPassword").type(PASS);
        click("#btnLogin");
        sleep(2000);
        verifyThat("#lblFeedback", hasText("O email digitado não está cadastrado."));
        TextField name = find("#txfEmail");
        boolean colorMatch = false;
        for(String style: name.getStyleClass()){
           if(style.contains("error"))
           {
               colorMatch = true;
           }
        }
        Assert.assertTrue(colorMatch);
    }
}
