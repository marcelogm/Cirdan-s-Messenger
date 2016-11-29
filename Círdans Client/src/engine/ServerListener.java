package engine;

import java.io.IOException;
import javafx.application.Platform;
import protocol.CProtocol;
import util.ObservableQueue;

/**
 * Classe responsavel por escutar mensagens do servidor
 * @author Marcelo Gomes Martins
 */
public class ServerListener extends Thread {     
    // Intance de motor
    private Engine engine;
    // Fila observavel de mensagens do servidor
    private ObservableQueue<CProtocol> serverMessage;
    
    public ServerListener(Engine engine) { this.engine = engine; }
    public ObservableQueue<CProtocol> getServerMessage() { return serverMessage; }
    public void setServerMessage(ObservableQueue<CProtocol> serverMessage) {
        this.serverMessage = serverMessage;
    }
    
    /**
     * Inicia processo de reebimento de mensagens
     */
    @Override
    public void run(){
        Engine engine = Engine.getInstance();
        boolean continueIt = true;
        while(continueIt){
            try {
                CProtocol message = engine.receiveStream();
                this.serverMessage.add(message);
            } catch (IOException ex) {
                continueIt = false;
                Platform.runLater(() -> {
                    engine.connectionFail();
                });
            } catch (ClassNotFoundException ex) {
                System.out.println("Protocolo desconhecido");
            }
        }
    }
}
