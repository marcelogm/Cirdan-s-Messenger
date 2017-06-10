package test;

import controller.CMain;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import interfaces.IAfterTest;
import interfaces.IBeforeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.UStage;

import static org.junit.Assert.*;
import static util.Constants.*;
import java.util.concurrent.TimeUnit;
import util.CirdanTestGui;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.loadui.testfx.Assertions.verifyThat;

public class test06 extends CirdanTestGui {

    @Test public void test() {
        doLogin(EMAIL_TESTE,"1234");
        click("Opções");
        click("Adicionar amigo");
        click("#txfSearch").type("teste2");
        click("Pesquisar");
        sleep(1, TimeUnit.SECONDS);
       assertTrue(pegaNome("teste2"));
    }
    @Before public void beforeTest() {
        this.createProfile("teste", "teste", EMAIL_TESTE, "1234");
    }
    @After public void afterTest(){
    	this.deleteProfile(EMAIL_TESTE);
    }
}