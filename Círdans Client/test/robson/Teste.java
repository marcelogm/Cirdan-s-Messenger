package robson;


import app.console.Main;
import facade.Facade;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import junit.framework.Assert;
import model.Friendship;
import model.Password;
import model.Profile;
import org.loadui.testfx.GuiTest;
import static org.loadui.testfx.GuiTest.find;
import protocol.EStatus;
import util.UPassword;
import util.UStage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author robsonheinke
 */
public class Teste extends GuiTest{
    public final String nome = "Robson Heinke";
    public final String nick = "robsonheinke";
    public final String email = "hotmail.com";
    public final String senha = "3110";
    public final String senhaErrada = "1310";
    public final String nomeAmigo = "Amigo Teste";
    public final String nickAmigo = "teste";
    
    @Override
    protected Parent getRootNode() {
        Main.DEBUG = false;
        UStage smanager = UStage.getInstance();
        smanager.setStage(stage);
        try {
            smanager.loadScene("GLogin");
        } catch (IOException ex) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
        }
        smanager.showStage("Círdan's Messenger Login", false);
        Parent root = smanager.getStage().getScene().getRoot();
        smanager.getStage().getScene().setRoot(new Region());
        return root;
    }
    
    public void logarCirdan(){
        TextField meuEmail = find("#txfEmail");
        PasswordField password = find("#psfPassword");
        meuEmail.setText(nick + "@" + email);
        password.setText(senha);
        click("#btnLogin");
        sleep(1000);
    }
  
    public void cadastroUsuario(String usuario, String nick, String email, String password) {
         Facade f = Facade.getInstance();
        Password pass = UPassword.generatePassword(password);
        f.save(pass);
        pass = f.findPasswordByHash(pass.getPassword());
        Profile profile = new Profile(
                usuario,
                nick,
                email,
                EStatus.ONLINE.status,
                "Olá, eu estou usando Círdan's Messenger!",
                "127.0.0.1",
                new Date(),
                true,
                "",
                pass.getId()
        );
        f.save(profile);
        profile = f.findProfileByEmail(profile.getEmail());
        Assert.assertNotNull(profile);
        sleep(1000);
    }
    
    public void amizadeUsuarios(){
        Facade f = Facade.getInstance();
        
        cadastroUsuario(nome, nick,(nick+"@"+email), senha);
        
        Profile usuario = f.findProfileByEmail((nick+"@"+email));
        Profile amigo = f.findProfileByEmail((nickAmigo+"@"+email));
        
        f.save(new Friendship(usuario.getId(), amigo.getId()));
            
        Friendship friendship = f.findFriendshipByProfiles(usuario.getId(), amigo.getId());
            friendship.setAccepted(true);
            f.update(friendship);
    }
    
    public void cadastroAmizadeDoisUsuarios(){
        Facade f = Facade.getInstance();
        
        cadastroUsuario(nome, nick,(nick+"@"+email), senha);
        cadastroUsuario(nomeAmigo, nickAmigo,(nickAmigo+"@"+email), senha);
        
        Profile usuario = f.findProfileByEmail((nick+"@"+email));
        Profile amigo = f.findProfileByEmail((nickAmigo+"@"+email));
        
        f.save(new Friendship(usuario.getId(), amigo.getId()));
            
        Friendship friendship = f.findFriendshipByProfiles(usuario.getId(), amigo.getId());
            friendship.setAccepted(true);
            f.update(friendship);
    }
    
    public void deleteUsuario(String email) {
        Facade f = Facade.getInstance();
        Profile profile = f.findProfileByEmail(email);
        Password password = f.findPasswordByProfileId(profile.getId());
        f.delete(profile);
        f.delete(password);
    }
}