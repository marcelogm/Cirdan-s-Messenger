package test;

import controller.CMain;
import javafx.scene.input.KeyCode;
import junit.framework.Assert;
import org.junit.Test;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import util.UStage;
import static util.Constants.*;
import java.util.concurrent.TimeUnit;
import util.CirdanTestGui;
import static org.junit.Assert.*;

public class test03 extends CirdanTestGui {

    @Test public void test() {
    	//teste  de login invalido
        click("#txfEmail").
        type("naoexiste").
        	press(KeyCode.SHIFT).
        	press(KeyCode.DIGIT2).
        	release(KeyCode.SHIFT).
        	release(KeyCode.DIGIT2).
        	type("teste.com");
        click("#psfPassword").type("estasenhanaoexiste");
        click("#btnLogin");
        
        
        verifyThat("#lblFeedBack", hasText("O email digitado não está cadastrado."));
        sleep(3, TimeUnit.SECONDS);
    }
}