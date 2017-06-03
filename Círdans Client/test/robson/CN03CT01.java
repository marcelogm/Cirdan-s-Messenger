package robson;


import org.junit.After;
import org.junit.Before;
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
public class CN03CT01 extends Teste{
    /*
    Caso de teste 01: Cliques simultaneos no botão de status
    */
    
    @Test
    public void cliquesStatus(){
        this.logarCirdan();
        for (int i = 0; i < 10; i++){
            click("#cmbStatus");
        }
        
    }
    
    @Before 
    public void cadastro(){
      this.cadastroUsuario(nome, nick, (nick+"@"+email), senha);
    }
    
    @After
    public void delete(){
        this.deleteUsuario((nick+"@"+email));
    }
}
