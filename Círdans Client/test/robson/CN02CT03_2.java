package robson;


import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author robsonheinke
 */
public class CN02CT03_2 extends Teste{
    
    /*
    Caso de teste 03: Cadastro com o Facebook
    */
    
    @Test
    public void cadastroFacebook(){
       click("#btnRegistration");
       click("#btnFacebook");
       
        sleep(5000);
       /*api facebook nao esta funcionando*/
    }
    
}
