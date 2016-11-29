package app.gui.component;

import engine.Engine;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import protocol.EStatus;

/**
 * Componente Listener de mudança de status
 * @author marce
 */
public class CStatusListener implements ChangeListener{
    private Engine engine;    
    // Referencia ao Pane que receberá o estilo
    private Pane changeIt;
    
    /**
     * Construtor
     * @param changeIt referencia do Pane de Status 
     */
    public CStatusListener(Pane changeIt) {
        this.engine = Engine.getInstance();
        this.changeIt = changeIt;
    }
    
    /**
     * Disparado ao mudar a selação da ComboBox responsável pelo status
     * @param observable Não utilizado
     * @param oldValue Não utilizado
     * @param newValue Novo valor selecionado
     */
    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        Integer value = (Integer)newValue;
        this.profileStatusBorderReset();        
        switch(EStatus.get(value)){
            case ONLINE:
                this.profileStatusSet("online");
                break;
            case BUSY:
                this.profileStatusSet("busy");
                break;
            case AWAY:
                this.profileStatusSet("away");
                break;
            default:
                System.out.println("Offline");
        }
        // envia aviso de mudança status para o servidor
        this.engine.sendStatusChange(EStatus.get(value).status);
    }
    
    /**
     * Retira estilos da Pane
     */
    private void profileStatusBorderReset(){
        changeIt.getStyleClass().remove("away");
        changeIt.getStyleClass().remove("online");
        changeIt.getStyleClass().remove("busy");
    }
    
    /**
     * Muda estilo da Pane
     * @param status novo estilo
     */
    private void profileStatusSet(String status){
        changeIt.getStyleClass().add(status);
    }
}
