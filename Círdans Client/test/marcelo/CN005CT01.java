package marcelo;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import junit.framework.Assert;
import marcelo.util.CirdanTestGui;
import marcelo.util.Constants;
import static marcelo.util.Constants.*;
import org.junit.Test;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;

/**
 * Cenário de teste de login
 * 
 * @author Marcelo Gomes Martins
 */
public class CN005CT01 extends CirdanTestGui{
    
    /**
     * Teste do cadastro com caracteres especiais
     * 
     * Pré requisitos:
     * Ter o software instalado
     */
    @Test public void test(){
        click("#btnRegistration");
        click("#txfName").
                type(NAME).
                press(KeyCode.ALT).
                press(KeyCode.NUMPAD1).
                release(KeyCode.ALT).
                release(KeyCode.NUMPAD1).
                press(KeyCode.ALT).
                press(KeyCode.NUMPAD2).
                release(KeyCode.ALT).
                release(KeyCode.NUMPAD2);
        click("#txfNick").type(NICK);
        click("#txfEmail").
                type(Constants.RANDOM_EMAIL_BODY).
                press(KeyCode.SHIFT).
                press(KeyCode.DIGIT2).
                release(KeyCode.SHIFT).
                release(KeyCode.DIGIT2).
                type(EMAIL_SERVER);
        click("#pdfPass").type(PASS);
        click("#pdfRepass").type(PASS);
        click("#btnRegister");
        sleep(2000);
        verifyThat("#lblFeedback", hasText("Insira um nome válido!"));
        TextField name = find("#txfName");
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
