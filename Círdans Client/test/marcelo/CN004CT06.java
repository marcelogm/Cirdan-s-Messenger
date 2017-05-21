package marcelo;

import marcelo.util.CirdanTestGui;
import org.junit.Test;

/**
 * Cenário de teste de gerenciamento de amigos
 * 
 * @author Marcelo Gomes Martins
 */
public class CN004CT06 extends CirdanTestGui{
    
    /**
     * Teste da funcionalidade de "Decidir mais tarde"
     * 
     * Pré requisitos:
     * Estar logado no sistema.
     * Possuir pedido de amizade pendente
     *
     * @see issue: mensagem nunca mais é mostrada novamente
     */
    @Test public void test(){
        preExecute();
        click("Decidir mais tarde");
    }
}
