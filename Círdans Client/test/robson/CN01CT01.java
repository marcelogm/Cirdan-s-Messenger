package robson;


import javafx.scene.input.KeyCode;
import org.junit.After;
import org.junit.Before;
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
public class CN01CT01 extends Teste{
    
    /*
    Caso de teste 01: Conectar com senha inválida.
    */
    @Before
    public void cadastro(){
        this.cadastroUsuario(nome, nick,(nick+"@"+email), senha);
    }
    
    @Test
    public void loginSenhaInvalida(){
        click("#txfEmail").type(nick).press(KeyCode.SHIFT).press(KeyCode.DIGIT2).release(KeyCode.SHIFT).
                release(KeyCode.DIGIT2).type(email);
        click("#psfPassword").type(senhaErrada);
        click("#btnLogin");
        verifyThat("#lblFeedback", hasText("A senha informada não confere."));
    }
    
    @After
    public void deleta(){
        this.deleteUsuario((nick+"@"+email));
    }
}
