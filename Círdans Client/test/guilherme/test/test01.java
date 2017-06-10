package test;

import controller.CMain;
import javafx.scene.input.KeyCode;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import util.UStage;
import static util.Constants.*;

import java.util.concurrent.TimeUnit;

import util.CirdanTestGui;

public class test01 extends CirdanTestGui {

    @Test public void test() {
    	//teste de cadastro.
        click("#btnRegistration");
        click("#txfName").type("Guilherme");
        click("#txfNick").type("Guilherme");
        click("#txfEmail").
                type("teste").
                press(KeyCode.SHIFT).
                press(KeyCode.DIGIT2).
                release(KeyCode.SHIFT).
                release(KeyCode.DIGIT2).
                type("teste.com");
        click("#pdfPass").type("1234");
        click("#pdfRepass").type("1234");
        click("#btnRegister");
        sleep(3, TimeUnit.SECONDS);
        
        verifyThat("#lblNickname", hasText("Guilherme"));
    }
    @After public void afterTest(){
        this.deleteProfile(EMAIL_TESTE);
    }
}