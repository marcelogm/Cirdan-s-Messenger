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
public class CN02CT02 extends Teste{
    /*
    Caso de teste 02: Cadastrar senhas diferentes
    */
    
    @Test
    public void cadastroUsuarioSenhasDiferentes(){
       click("#btnRegistration");
       click("#txfName").type(nome);
       click("#txfNick").type(nick);
       click("#txfEmail").type(nick).press(KeyCode.SHIFT).press(KeyCode.DIGIT2).release(KeyCode.SHIFT).
                release(KeyCode.DIGIT2).type(email);
       click("#pdfPass").type(senha);
       click("#pdfRepass").type(senhaErrada);
       click("#btnRegister");
       
        verifyThat("#lblFeedback", hasText("As senhas digitadas não conferem."));
       
    }
}
