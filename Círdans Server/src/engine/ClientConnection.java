package engine;

import facade.Facade;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Friendship;
import model.Profile;
import protocol.CProtocol;
import protocol.EResponse;
import protocol.EStatus;
import protocol.model.SMessage;
import protocol.model.SProfile;

/**
 * Thread de comunicação individual entre cliente
 * @author Marcelo Gomes Martins
 */
public class ClientConnection extends Thread{
    // Endereço 
    private InetAddress ipv4;
    // Id de representação do usuário
    private final long id;
    // Socket de conexão com o servidor
    private Socket connection;
    // Representação do banco de dados
    private final Facade facade;
    // Instance da  tabela de clientes
    private ClientTable clientTable;
    // Recebe dados do cliente
    private ObjectInputStream fromClient;
    // Envia dados para o cliente
    private ObjectOutputStream toClient;

    /**
     * Construtor da classe de conexão
     * @param ipv4
     * @param id
     * @param connection 
     */
    public ClientConnection(InetAddress ipv4, long id, Socket connection) {
        this.ipv4 = ipv4;
        this.id = id;
        this.connection = connection;
        this.facade = Facade.getInstance();
        this.clientTable = ClientTable.getInstance();
    }
    
    /**
     * Recebe mensagem do cliente e trata informações
     */
    public void recieveMessage() {
        // Flag para continuar continuação
        boolean continueIt = true;
        while(continueIt){
            try {
                this.fromClient = new ObjectInputStream(this.connection.getInputStream());
                CProtocol message = (CProtocol)this.fromClient.readObject();
                switch(message.getType()){
                    case REQUIRE_FRIENDLIST:
                        this.executeRequireFriendList();
                        break;
                    case REQUIRE_CHANGE_STATUS:
                        this.executeRequireChangeStatus(message);
                        break;
                    case FIND_PROFILE_REQUEST:
                        this.executeFindProfileRequest(message);
                        break;
                    case NEW_FRIEND_REQUEST:
                        this.executeNewFriendRequest(message);
                        break;
                    case ACCEPTED_FRIENDSHIP:
                        this.executeAcceptedFriendship(message);
                        break;
                    case REFUSED_FRIENDSHIP:
                        this.executeRefusedFriendship(message);
                        break;
                    case PENDING_FRIENDSHIP_REQUEST:
                        this.sendPendingFriendship();
                        break;
                    case SEND_MESSAGE:
                        this.executeMessageForward(message);
                        break;
                }
            } catch (IOException ex) {
                continueIt = false;
            } catch (ClassNotFoundException ex) {
                continueIt = false;
                System.out.println("recieveMessage@ClientConnection");
            }
        }
    }
    
    /**
     * Execução do protocolo
     * SEND_MESSAGE
     * @param clientMessage 
     */
    private void executeMessageForward(CProtocol clientMessage){
        SMessage message = (SMessage)clientMessage.getPayload();
        Friendship friendship = this.facade.findFriendshipByProfiles(message.getRecieverId(), message.getSenderId());
        if(friendship != null && friendship.isAccepted() && !friendship.isBlocked()){
            this.sendMessage(id, message);
        } else {
            System.out.println("Usuário " + 
                    message.getSenderName() + 
                    " não tem permissão para enviar mensagens para o usuário " + 
                    message.getRecieverName());
        }
    }
    
    /**
     * Excecução do protoclo
     * PENDING_FRIENDSHIP_REQUEST
     * Só para seguir o padrão, coloquei essa função dentro de uma "execute..."
     */
    private void executePendingFriendship(){
        this.sendPendingFriendship();
    }
    
    /**
     * Execução do protocolo
     * REQUIRE_FRIENDLIST
     */
    private void executeRequireFriendList(){
        this.sendFriendList(this.id, this.connection);
    }
    
    /**
     * Execução do protocolo
     * REQUIRE_CHANGE_STATUS
     */
    private void executeRequireChangeStatus(CProtocol message){
        Integer status = (Integer)message.getPayload();
        this.changeClientStatus(status);
        this.sendChangeStatusToFriends(status);
    }
    
    /**
     * Execução do protocolo
     * FIND_PROFILE_REQUEST
     */
    private void executeFindProfileRequest(CProtocol message){
        String likeThis = (String)message.getPayload();
        this.sendProfileList(likeThis);
    }
    
    /**
     * Execução do protocolo
     * NEW_FRIEND_REQUEST
     */
    private void executeNewFriendRequest(CProtocol message){
        Long friend = (Long)message.getPayload();
        this.createFriendRelation(friend);
        if(this.clientTable.clients.containsKey(id)){
            this.sendFriendshipQuestion(friend);
        }
    }
    
    /**
     * Execução do protocolo
     * ACCEPTED_FRIENDSHIP
     */ 
    private void executeAcceptedFriendship(CProtocol message){
        Long idAccepted = message.getRecieverId();
        this.setFriendshipAccepted(idAccepted);
        if(this.clientTable.clients.containsKey(idAccepted)){
            Socket client = this.clientTable.clients.get(idAccepted).getConnection();
            this.sendFriendList(idAccepted, client);
        }
        this.sendFriendList(this.id, this.connection);
    }
    
    /**
     * Execução do protocolo
     * REFUSED_FRIENDSHIP
     */
    private void executeRefusedFriendship(CProtocol message){
        Long idRefused = message.getRecieverId();
        this.deleteFriendRelation(idRefused);
    }
    
    private void sendMessage(long id, SMessage message){
        try {
            this.sendStream(this.clientTable.clients.get(id).connection,
                new CProtocol(
                        this.connection.getInetAddress(),
                        this.id,
                        EResponse.SEND_MESSAGE,
                        message
            ));
        } catch (IOException ex) {
            Logger.getLogger("sendMessage@ClientConnection");
        }
    }
    
    /**
     * Envia mensagem de requisição de amizade
     * @param id amigo a ser adicionado
     */
    private void sendFriendshipQuestion(long id){
        Profile me =  facade.findProfileById(this.id);
        try {
            this.sendStream(this.clientTable.clients.get(id).connection,
                new CProtocol(
                        this.connection.getInetAddress(),
                        this.id,
                        EResponse.FRIENDSHIP_QUESTION,
                        me.getName()
            ));
        } catch (IOException ex) {
            System.out.println("sendFriendshipQuestion@ClientConnection");
        }
    }
    
    
     /**
     * Envia lista de amizades pendentes para usuário
     */
    private void sendPendingFriendship(){
        ArrayList<Profile> profiles = facade.findPendingFriends(id);
        HashMap<Long, String> response = new HashMap<>();
        for(Profile profile: profiles){
            response.put(profile.getId(), profile.getName());
        }
        try {
            this.sendStream(this.clientTable.clients.get(id).connection,
                new CProtocol(
                        this.connection.getInetAddress(),
                        this.id,
                        EResponse.PENDING_FRIENDSHIP_RESPONSE,
                        response
            ));
        } catch (IOException ex) {
            System.out.println("sendPendingFriendship@ClientConnection");
        }
    }
    
    /**
     * Envia lista de contatos que tenham o nome como likeThis
     * @param likeThis nome do amigo
     */
    private void sendProfileList(String likeThis){
        ArrayList<Profile> profiles = facade.findNonFriendList(this.id, likeThis);
        ArrayList<SProfile> nonfriends = new ArrayList<>();
        for(Profile p : profiles){
            nonfriends.add(new SProfile(
                    p.getId(),
                    p.getName(),
                    p.getNick(),
                    p.getImageUrl()
            ));
        }
        try {
            this.sendStream(connection, new CProtocol(
                   connection.getInetAddress(),
                   EResponse.FIND_PROFILE_RESPONSE,
                   nonfriends
            ));
        } catch (IOException ex) {
            System.out.println("sendProfileList@ClientConnection");
        }
    }
    
    /**
     * Envia lista de amigos do usuário
     * @param id
     * @param connection 
     */
    private void sendFriendList(Long id, Socket connection){
        ArrayList<Profile> profiles = facade.findFriendList(id);
        ArrayList<SProfile> friends = new ArrayList<>();
        
        for(Profile p : profiles){
            if(!this.clientTable.clients.containsKey(p.getId())){
                p.setStatus(EStatus.OFFLINE.status);
            }
            friends.add(new SProfile(
                    p.getId(),
                    p.getName(),
                    p.getNick(),
                    p.getStatus(),
                    p.getSubnick(),
                    p.getImageUrl()
            ));
        }
        
        try {
            this.sendStream(connection, new CProtocol(
                   this.connection.getInetAddress(),
                   EResponse.FRIENDLIST_RESPONSE,
                   friends
            ));
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Envia mudança de status para amigos
     * @param status do perfil
     */
    private void sendChangeStatusToFriends(Integer status){
        ArrayList<Profile> profiles = facade.findFriendList(this.id);
        
        for(Profile p : profiles){
            if(this.clientTable.clients.containsKey(p.getId())){
                try {
                    this.sendStream(this.clientTable.clients.get(p.getId()).connection, 
                            new CProtocol(
                                this.connection.getInetAddress(),
                                this.id,
                                EResponse.FRIEND_CHANGE_STATUS,
                                status
                    ));
                } catch (IOException ex) {
                    Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /**
     * Envia aviso que usuário está online
     */
    private void sendFriendIsOnline(){
        Profile profile = facade.findProfileById(id);
        this.sendChangeStatusToFriends(profile.getStatus());
    }
    
    /**
     * Envia aviso que usuário está offline
     */
    private void sendFriendIsOffline(){
        this.sendChangeStatusToFriends(EStatus.OFFLINE.status);
    }
    
    /**
     * Exclui um relacionamento de amizade entre o usuário
     * @param id amigo a ser excluído
     */
    private void deleteFriendRelation(Long id){
        Friendship relation = facade.findFriendshipByProfiles(id, this.id);
        facade.delete(relation);
    }
    
    /**
     * Aceita pedido de ammizade
     * @param id  amigo a ser aceito
     */
    private void setFriendshipAccepted(Long id){
        Friendship relation = facade.findFriendshipByProfiles(id,this.id);
        relation.setAccepted(true);
        facade.update(relation);
    }
    
    /**
     * Cria relacionamento entre amigos
     * @param id amigo
     */
    private void createFriendRelation(Long id){    
        facade.save(new Friendship(this.id, id));
    }

    /**
     * Grava mudança de status no banco
     * @param status do perfil
     */
    private void changeClientStatus(Integer status){
        Profile self = facade.findProfileById(id);
        self.setStatus(status);
        facade.update(self);
    }
    
    /**
     * Envia dados para usuário
     * @param s socket para usuário
     * @param message mensagem para usuário
     * @throws IOException 
     */
    private void sendStream(Socket s, CProtocol message) throws IOException{
        ObjectOutputStream toServer = new ObjectOutputStream(s.getOutputStream());
        toServer.writeObject(message);
    }
   
    /**
     * Inicia thread de usuário
     */
    @Override
    public void run(){
        this.sendFriendIsOnline();
        System.out.println("Cliente " + this.id + " conectado por " + this.ipv4);
        this.recieveMessage();
        System.out.println("Cliente " + this.id + " desconectado. ");
        this.sendFriendIsOffline();
        this.destroy();
    }
    
    /**
     * Destroi thread do usuário após conexão
     */
    @Override
    public void destroy(){
        try {
            this.clientTable.clients.remove(this.id);
            this.ipv4 = null;
            this.connection.close();
            this.connection = null;
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InetAddress getIpv4() { return ipv4; }
    public void setIpv4(InetAddress ipv4) { this.ipv4 = ipv4; }
    public Socket getConnection() { return connection; }
    public void setConnection(Socket connection) { this.connection = connection; }
    public ClientTable getClientTable() { return clientTable; }
    public void setClientTable(ClientTable clientTable) { this.clientTable = clientTable; }
}
