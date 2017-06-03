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
public class CN01CT03 extends Teste{

    /*
    caso de teste 03: Logar sem inserir os dados
    */
    @Test
    public void testeLoginSemInserirDados() {
        click("#btnLogin");
        verifyThat("#lblFeedback", hasText("Insira um email válido!"));
    }  
}
