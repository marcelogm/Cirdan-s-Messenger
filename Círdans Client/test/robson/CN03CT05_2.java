package robson;


import facade.Facade;
import javafx.scene.control.TabPane;
import model.Friendship;
import model.Profile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.loadui.testfx.GuiTest.find;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author robsonheinke
 */
public class CN03CT05_2 extends Teste{
    /*
    Caso de teste 05: Abrir uma conversa com um usuário ocupado.
    */
    
    @Before
    public void cadastro(){
        this.amizadeUsuarios();
    }
    
    @Test
    public void teste(){
        this.logarCirdan();
        sleep(500);
        click(nickAmigo).click(nickAmigo);
        TabPane tabPane = find("#tbpMain");
        
        String string = tabPane.getSelectionModel().getSelectedItem().getText();
        Assert.assertTrue(string.contains(nickAmigo));
    }
    
    
    @After
    public void delete(){
        Facade f = Facade.getInstance();
        Profile usuario = f.findProfileByEmail((nick+"@"+email));
        Profile amigo = f.findProfileByEmail((nickAmigo+"@"+email));
        
        Friendship friendship = f.findFriendshipByProfiles(usuario.getId(), amigo.getId());
        f.delete(friendship);
        
        this.deleteUsuario(nick+"@"+email);
    }
}
