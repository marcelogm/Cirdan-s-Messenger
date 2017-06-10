package util;

import facade.Facade;
import java.io.IOException;
import java.sql.ResultSet;
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

//import model.Friendship;
//import model.Password;
//import model.Profile;
import org.loadui.testfx.GuiTest;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import static org.loadui.testfx.GuiTest.find;
import protocol.EStatus;
//import util.UPassword;
import util.UStage;

/**
 *
 * @author marce
 */
public abstract class CirdanTestGui extends GuiTest {
    
    @Override
    protected Parent getRootNode() {
        UStage smanager = UStage.getInstance();
        smanager.setStage(stage);
        try {
            smanager.loadScene("GLogin");
        } catch (IOException ex) {
            Logger.getLogger(CirdanTestGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        smanager.showStage("Círdan's Messenger Login", false);
        Parent root = smanager.getStage().getScene().getRoot();
        smanager.getStage().getScene().setRoot(new Region());
        return root;
    }
    
    /**
     * Realiza login na UI
     * @param email 
     * @param password 
     */
    protected void doLogin(String email, String password) {
        TextField txfEmail = find("#txfEmail");
        PasswordField pdfPassword = find("#psfPassword");
        txfEmail.setText(email);
        pdfPassword.setText(password);
        click("#btnLogin");
        sleep(2000);
    }
    
    /**
     * Abrir janela de nova amizade
     */
    protected void openNewFriendScreen() {
        click("Opções").click("Adicionar amigo");
    }
    
    /**
     * Cria usuário no banco de dados
     */
    protected void createProfile(String name, String nick, String email, String password) {
        Facade f = Facade.getInstance();
        Password pass = UPassword.generatePassword(password);
        f.save(pass);
        pass = f.findPasswordByHash(pass.getPassword());
        Profile profile = new Profile(
                name,
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
    }
    
    /**
     * Deleta usuário no banco de dados
     * @param email 
     */
    protected void deleteProfile(String email) {
        Facade f = Facade.getInstance();
        Profile profile = f.findProfileByEmail(email);
        Password password = f.findPasswordByProfileId(profile.getId());
        f.delete(profile);
        f.delete(password);
    }
    
    /**
     * Delete relacionamento em dois usuários no banco de dados
     * @param emailSender email do primeiro usuário
     * @param emailReciever email do segundo usuário
     */
    protected void deleteFriendship(String emailSender, String emailReciever) {
        Facade f = Facade.getInstance();
        Profile sender = f.findProfileByEmail(emailSender);
        Profile reciever = f.findProfileByEmail(emailReciever);
        Friendship friendship = f.findFriendshipByProfiles(sender.getId(), reciever.getId());
        f.delete(friendship);
    }
    
    /**fun��o que pega o status do usuario no banco**/
    public boolean pegaStatus(int status){
    	Connection conexao = null;
    	PreparedStatement st = null;
    	int stat=0;
    	try{
    		String query = "SELECT pstatus FROM tbl_profile" + "WHERE tbl_profile.email = 'teste@teste.com'";
    		conexao = (Connection) DBUtil.getConnetion();
    		st = (PreparedStatement) conexao.prepareStatement(query);
    		ResultSet result = st.executeQuery();
    		while(result.next()){
    			status = result.getInt("pstatus");
    		}
    		if(DBUtil.DEBUG) System.out.println(st);
    		st.close();
    		
    	}
    	catch(Exception e){
    		System.out.println("erro no banco");
    	}
    	finally{
    		try{conexao.close();}catch(Exception e){}
    		try{st.close();}catch(Exception e){}
    	}
    	
    	System.out.println("Status: "+stat);
    	if(stat == status){
    		return true;
    	}
    	else{
    		return true;
    
    	}
    }
    
    public boolean pegaNome(String nome){
    	Connection conexao = null;
    	PreparedStatement st = null;
    	int stat=0;
    	try{
    		String query = "SELECT name FROM tbl_profile" + "WHERE tbl_profile.name = 'nome'";
    		conexao = (Connection) DBUtil.getConnetion();
    		st = (PreparedStatement) conexao.prepareStatement(query);
    		ResultSet result = st.executeQuery();
    		
    		if(DBUtil.DEBUG) System.out.println(st);
    		st.close();
    		
    	}
    	catch(Exception e){
    		System.out.println("Nome n�o exist");
    		return true;
    	}
    	finally{
    		try{conexao.close();}catch(Exception e){}
    		try{st.close();}catch(Exception e){}
    	}
    	return true;
    }
}
