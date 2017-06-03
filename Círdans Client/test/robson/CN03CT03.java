package robson;


import javafx.scene.layout.Pane;
import org.junit.After;
import org.junit.Assert;
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
public class CN03CT03 extends Teste{
    
    /*
    Caso de teste 03: Alterar Status de perfil (3x cada)
    */
    @Test
    public void cliquesStatus(){
        int aux = 0; //0 = disponivel , 1 = ocupado, 2 = ausente
        Pane pane;
        String string, teste;
        this.logarCirdan();
        for(int i = 0; i < 15; i++){
            if(aux == 0){
                teste = "busy";
                click("Disponível");
                click("Ocupado");
                aux = 1;
                pane = find("#panProfileBorder");
                string = pane.getStyleClass().toString();
                
                Assert.assertTrue(string.contains(teste));
                sleep(1000);
            }
            else if(aux == 1){
                teste = "away";
                click("Ocupado");
                click("Ausente");
                aux = 2;
                pane = find("#panProfileBorder");
                string = pane.getStyleClass().toString();
                
                Assert.assertTrue(string.contains(teste));
                sleep(1000);
            }
            else{
                teste = "online";
                click("Ausente");
                click("Disponível");
                aux = 0;
                pane = find("#panProfileBorder");
                string = pane.getStyleClass().toString();
                
                Assert.assertTrue(string.contains(teste));
                sleep(1000);
            }
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
