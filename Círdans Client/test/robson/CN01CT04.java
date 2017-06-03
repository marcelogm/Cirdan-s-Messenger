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
public class CN01CT04 extends Teste{
    
    /*
    Caso de teste 04: Teste de cliques no botão entrar
    */
    
     @Test
    public void LoginSemInserirDadosDiversosClicks() {
       for(int i = 0; i < 10;i++){
            click("#btnLogin");
            verifyThat("#lblFeedback", hasText("Insira um email válido!"));
       }
    }  
}
