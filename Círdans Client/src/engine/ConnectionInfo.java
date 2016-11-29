package engine;

import java.net.InetAddress;
import java.net.Socket;

/**
 * Classe de encapsulamento de dados de conexão
 * @author Marcelo Gomes Martins
 */
public class ConnectionInfo {
    public InetAddress ipv4;
    public InetAddress serveripv4;
    public long id;
    public Socket socket;

    public ConnectionInfo(InetAddress ipv4, InetAddress server, long id, Socket connection) {
        this.ipv4 = ipv4;
        this.serveripv4 = server;
        this.id = id;
        this.socket = connection;
    }
}
