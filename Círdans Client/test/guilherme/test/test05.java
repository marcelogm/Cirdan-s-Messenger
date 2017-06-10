package test;

import org.junit.After;
import org.junit.Before;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.junit.Assert;

import org.junit.Test;

import static org.junit.Assert.*;
import static util.Constants.*;

import java.util.concurrent.TimeUnit;

import util.CirdanTestGui;

public class test05 extends CirdanTestGui {

    @Test public void test() {
    	//teste status
    	doLogin(EMAIL_TESTE, "1234");
	    click("#cmbStatus");
	    click("Ausente");
	    assertTrue(pegaStatus(2));
	    
	    sleep(1, TimeUnit.SECONDS);

	    click("#cmbStatus");
	    click("Ocupado");
	    assertTrue(pegaStatus(1));
	    sleep(1, TimeUnit.SECONDS);
	    
	    click("#cmbStatus");
	    click("Disponível");
	    assertTrue(pegaStatus(0));
	    
	    sleep(1, TimeUnit.SECONDS);
    	
    }
    

    @Before public void beforeTest() {
    	this.createProfile("teste", "teste", EMAIL_TESTE, "1234");
    }
    
    @After public void afterTest() {
    	this.deleteProfile(EMAIL_TESTE);
    }
}