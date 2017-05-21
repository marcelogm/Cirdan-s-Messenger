package marcelo;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import junit.framework.Assert;
import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import org.junit.Test;
import protocol.model.SMessage;

/**
 * Cenário de teste de gerenciamento de amigos
 * Apesar de estar classificado como teste gerenciamento de amigos,
 * aparenta ser um teste de stress
 * 
 * @author Marcelo Gomes Martins
 */
public class CN004CT05 extends CirdanTestGui {
    
    /**
     * Teste de envio de solicitação de amizade em um ambiente de stress
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     * Possui outro usuário conectado @see CN002CT02
     */
    @Test public void test(){
        preExecute();
        /*
        // Parte do teste suprimida
        click("Opções").click("Adicionar amigo");
        click("#txfSearch").type(ANOTHER_FRIEND_NAME);
        click("Pesquisar");
        click("Adicionar");
        */        
        click(FRIED_NAME).click(FRIED_NAME);
        TextArea input = find("#txaInput");
        click("#txaInput");
        for (int i = 0; i < MESSAGE_AMOUNT; i++) {
            input.setText(MESSAGE);
            type(KeyCode.ENTER);
        }
        ListView<SMessage> list = find("#lvwMessages");
        Assert.assertEquals(list.getItems().size(), MESSAGE_AMOUNT);
        sleep(2000);
    }
}
