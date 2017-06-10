package test;

import controller.CMain;
import facade.Facade;
import javafx.scene.input.KeyCode;
import model.Friendship;
import model.Profile;
import interfaces.IAfterTest;
import interfaces.IBeforeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.UStage;
import static util.Constants.*;
import java.util.concurrent.TimeUnit;
import util.CirdanTestGui;

public class test07 extends CirdanTestGui {
	//teste decidir mais tarde erro!

    @Test public void test() {
    	click("#txfEmail").
        type("teste").
        	press(KeyCode.SHIFT).
        	press(KeyCode.DIGIT2).
        	release(KeyCode.SHIFT).
        	release(KeyCode.DIGIT2).
        	type("teste.com");
        click("#psfPassword").type("1234");
        click("#btnLogin");
        sleep(1, TimeUnit.SECONDS);
        click("Decidir mais tarde");
        sleep(3, TimeUnit.SECONDS);
        click("Opções");
        sleep(1, TimeUnit.SECONDS);
        click("Adicionar amigo");
        sleep(2,TimeUnit.SECONDS);
        
    }
    
    @Before public void beforeTest() {
    	this.createProfile("teste", "teste", EMAIL_TESTE, "1234");
    	this.createProfile("teste2", "test2", EMAIL_TESTE2, "1234");
	    Facade f = Facade.getInstance();
	    Profile sender = f.findProfileByEmail(EMAIL_TESTE2);
	    Profile reciever = f.findProfileByEmail(EMAIL_TESTE);
	    f.save(new Friendship(sender.getId(), reciever.getId()));
    }
    
    @After public void afterTest(){
        this.deleteFriendship(EMAIL_TESTE, EMAIL_TESTE2);
        this.deleteProfile(EMAIL_TESTE);
        this.deleteProfile(EMAIL_TESTE2);
    }
}