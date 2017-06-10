package test;

import controller.CMain;
import javafx.scene.input.KeyCode;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import util.UStage;
import static util.Constants.*;

import java.util.concurrent.TimeUnit;

import util.CirdanTestGui;

public class test02 extends CirdanTestGui {

    @Test public void test() {
    	//teste de login valido
        click("#txfEmail").
        type("teste").
        	press(KeyCode.SHIFT).
        	press(KeyCode.DIGIT2).
        	release(KeyCode.SHIFT).
        	release(KeyCode.DIGIT2).
        	type("teste.com");
        click("#psfPassword").type("1234");
        click("#btnLogin");
        
        //sleep(5, TimeUnit.SECONDS);
        verifyThat("#lblNickname", hasText("teste"));
        
    }
    @Before public void beforeTest(){ 
    	this.createProfile("teste", "teste", EMAIL_TESTE, "1234");
    }
    @After public void afterTest(){
        this.deleteProfile(EMAIL_TESTE);
    }
}