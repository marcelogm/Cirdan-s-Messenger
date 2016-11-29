package engine;

import java.util.HashMap;

public class ClientTable {
    private static ClientTable table;
    public HashMap<Long, ClientConnection> clients;
    
    private ClientTable(){
        this.clients = new HashMap<>();
    }
    
    public static ClientTable getInstance() {
        if(table == null) {
            table = new ClientTable();
        }
        return table;
    }
}
