package robson;


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
public class CN02CT03 extends Teste{
    
    /*
    Caso de teste 03: Cadastro com email invalido
    */
    
    @Test
    public void cadastroComEmailInvalido(){
        click("#btnRegistration");
        click("#txfName").type(nome);
        click("#txfNick").type(nick);
        click("#txfEmail").type(nick);
        click("#pdfPass").type(senha);
        click("#pdfRepass").type(senhaErrada);
        click("#btnRegister");
       
        verifyThat("#lblFeedback", hasText("Insira um email válido!"));
    }
}
