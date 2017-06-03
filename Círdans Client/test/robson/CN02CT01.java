package robson;


import javafx.scene.input.KeyCode;
import org.junit.Test;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author robsonheinke
 */
public class CN02CT01 extends Teste{
    
    /*
    Caso de teste 01: Cadastrar nome com caracteres especiais
    */
    
    @Test
    public void cadastroUsuarioSenhasDiferentes(){
        click("#btnRegistration");
        
        click("#txfName").press(KeyCode.SHIFT).
               press(KeyCode.DIGIT3).
               press(KeyCode.DIGIT7).
               press(KeyCode.DIGIT2).
               press(KeyCode.DIGIT8).
               press(KeyCode.DIGIT4).
               press(KeyCode.DIGIT0).
               release(KeyCode.SHIFT).
               release(KeyCode.DIGIT3).
               release(KeyCode.DIGIT7).
               release(KeyCode.DIGIT2).
               release(KeyCode.DIGIT8).
               release(KeyCode.DIGIT4).
               release(KeyCode.DIGIT0);
        click("#txfNick").type(nick);
        click("#txfEmail").type(nick).press(KeyCode.SHIFT).press(KeyCode.DIGIT2).release(KeyCode.SHIFT).
                release(KeyCode.DIGIT2).type(email);
        click("#pdfPass").type(senha);
        click("#pdfRepass").type(senha);
        click("#btnRegister");
        verifyThat("#lblFeedback", hasText("Insira um nome válido!"));
    }
    
}
