package marcelo;

import facade.Facade;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import junit.framework.Assert;
import marcelo.util.CirdanTestGui;
import static marcelo.util.Constants.*;
import marcelo.interfaces.IAfterTest;
import marcelo.interfaces.IBeforeTest;
import model.Friendship;
import model.Profile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import protocol.model.SMessage;

/**
 * Cenário de teste de gerenciamento de amigos
 * Apesar de estar classificado como teste gerenciamento de amigos,
 * aparenta ser um teste de stress
 * 
 * @author Marcelo Gomes Martins
 */
public class CN004CT05 extends CirdanTestGui implements IAfterTest, IBeforeTest{
    
    /**
     * Teste de envio de solicitação de amizade em um ambiente de stress
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     * Possui outro usuário conectado @see CN002CT02
     */
    @Test public void test(){
        doLogin(FULL_SMALL_EMAIL, PASS);
        /*
        // Parte do teste suprimida
        click("Opções").click("Adicionar amigo");
        click("#txfSearch").type(ANOTHER_FRIEND_NAME);
        click("Pesquisar");
        click("Adicionar");
        */        
        click(ONLINE_FRIEND_NAME).click(ONLINE_FRIEND_NAME);
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

    @Override @Before public void beforeTest() {
        this.createProfile(NAME, NICK, FULL_SMALL_EMAIL, PASS);
        Facade f = Facade.getInstance();
        Profile sender = f.findProfileByEmail(FULL_SMALL_EMAIL);
        Profile reciever = f.findProfileByEmail(ONLINE_FRIEND_EMAIL);
        f.save(new Friendship(sender.getId(), reciever.getId()));
        Friendship friendship = f.findFriendshipByProfiles(sender.getId(), reciever.getId());
        friendship.setAccepted(true);
        f.update(friendship);
    }
    
    @Override @After public void afterTest() {
        this.deleteFriendship(FULL_SMALL_EMAIL, ONLINE_FRIEND_EMAIL);
        this.deleteProfile(FULL_SMALL_EMAIL);
    }

}
