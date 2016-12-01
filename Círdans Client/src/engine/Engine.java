package engine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import protocol.CProtocol;
import protocol.model.SLogin;
import protocol.EResponse;
import protocol.model.SMessage;
import protocol.model.SProfile;
import util.ObservableQueue;
import util.UScene;

/**
 * Motor que realiza a conexão entre o servidor e os controladores
 * @author marce
 */
public class Engine {
    // Ip do servidor
    private String serverIpv4;
    // Porta padrão do servidor
    private String serverPort;
    // Propria referencia 
    private static Engine engine;
    // Informações da conexão
    private ConnectionInfo connection;
    // Informações do usuário
    private ClientInfo informations;
    // Recebe informações do servidor
    private ServerListener serverListerner;
    // Buffer de envio de informações
    private ObjectOutputStream toServer;
    // Buffer de recebimento de informações
    private ObjectInputStream fromServer;
    
    /**
     * Construtor privado
     */
    private Engine(){
        this.serverPort = "5555";
        this.serverIpv4 = "localhost";
        connection = new ConnectionInfo(
                null,
                null,
                0,
                null
        );
        this.serverListerner = new ServerListener(this);
    }
   
    /**
     * Recupera motor em qualquer lugar da aplicação
     * @return instance
     */
    public static Engine getInstance(){
        if(engine == null){
            engine = new Engine();
        }
        return engine;
    }
    
    /**
     * Cria nova conexão com o servidor
     * @throws IOException 
     */
    private void createNewConnection() throws IOException{
        Socket s = new Socket(this.serverIpv4, Integer.parseInt(this.serverPort));
        this.connection.socket = s;
        this.connection.ipv4 = s.getLocalAddress();
        this.connection.serveripv4 = s.getInetAddress();
    }
    
    /**
     * Envia mensagem de protocolo ao servidor
     * @param message
     * @throws IOException 
     */
    protected void sendStream(CProtocol message) throws IOException{
        this.toServer = new ObjectOutputStream(connection.socket.getOutputStream());
        this.toServer.writeObject(message);
    }
    
    /**
     * Recebe mensagem de protocolo do servidor
     * @return
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    protected CProtocol receiveStream() throws IOException, ClassNotFoundException{
        this.fromServer = new ObjectInputStream(connection.socket.getInputStream());
        CProtocol message = (CProtocol)this.fromServer.readObject();
        return message;
    }
    
    /**
     * Envia requisição de login
     * @param email
     * @param password
     * @return tipo de resposta
     */
    public EResponse sendLoginRequest(String email, String password){
        CProtocol response = null;
        try {
            this.createNewConnection();
            this.sendStream(new CProtocol(
                    connection.socket.getLocalAddress(),
                    EResponse.REQUIRE_AUTH,
                    new SLogin(email, password)
            ));
            
            response = this.receiveStream();
            if(response.getType() == EResponse.AUTH_SUCCESSFUL){
                this.connection.id = response.getSenderId();
                this.informations = this.deserializeProfile((SProfile)response.getPayload());
            }
        } catch (Exception ex) {
            return EResponse.ERROR;
        } 
        return response.getType();
    }
    
    /**
     * Envia requisição de cadastro
     * @param name
     * @param email
     * @param nick
     * @param pass
     * @param profileImage
     * @return tipo de resposta
     */
    public EResponse sendRegistrationRequest(String name, String email, String nick, String pass, String profileImage){
        CProtocol response = null;
        try {
            this.createNewConnection();
            this.sendStream(new CProtocol(
                    connection.socket.getLocalAddress(),
                    EResponse.REQUIRE_NEW_ACCOUNT,
                    new SProfile(name, nick, email, profileImage, pass)
            ));
            
            response = this.receiveStream();
            if(response.getType() == EResponse.REGISTRATION_SUCESSFUL){
                this.connection.id = response.getSenderId();
                this.informations = this.deserializeProfile((SProfile)response.getPayload());
            }
        } catch (Exception ex) {
            return EResponse.ERROR;
        } 
        return response.getType();
    }
    
    /**
     * Transforma perfil recebido pelo servidor
     * em informações de usuários que serão utilizadas no motor
     * @param profile
     * @return 
     */
    private ClientInfo deserializeProfile(SProfile profile){
        return new ClientInfo(
                profile.getName(),
                profile.getNick(),
                profile.getEmail(),
                profile.getSubnick(),
                profile.getImageUrl(),
                profile.getStatus()
        );
    }
    
    /**
     * Envia mensagem para servidor
     * chegando lá, ele repassará para o usuário
     * @param friend id do destinatario
     * @param message mensagem
     */
    public void sendMessage(Long friend, SMessage message, boolean isAttention){
        try {
            this.sendStream(new CProtocol(
                    this.connection.ipv4,
                    this.connection.id,
                    friend,
                    new Date(),
                    (isAttention ? EResponse.SEND_TAKE_ATTENTION : EResponse.SEND_MESSAGE),
                    message
            ));
        } catch (IOException ex) {
            System.out.println("sendMessage@Engine");
        }
    }
    
    /**
     * Envia requisição de nova amizade
     * @param friend id do usuario
     */
    public void sendNewFriendRequest(Long friend){
        try {
            this.sendStream(new CProtocol(
                    this.connection.ipv4,
                    EResponse.NEW_FRIEND_REQUEST,
                    friend
            ));
        } catch (IOException ex) {
            System.out.println("sendNewFriendRequest@Engine");
        }
    }
    
    /**
     * Envia resposta a positiva pedido de amizade
     * @param id id do usuário
     */
    public void sendFriendshipRespose(Long id, boolean bool){
        try {
            CProtocol protocol = new CProtocol();
            protocol.setType(bool ? EResponse.ACCEPTED_FRIENDSHIP : EResponse.REFUSED_FRIENDSHIP);
            protocol.setRecieverId(id);
            protocol.setSenderId(this.connection.id);
            protocol.setSender(this.connection.ipv4);
            this.sendStream(protocol);
        } catch (IOException ex) {
            System.out.println("sendFriendshipRespose@Engine");
        }
    }
    
    /**
     * Envia requisição de lista de pedido de amizade pendentes
     */
    public void sendPendingFriendshipRequest(){
        try {
            this.sendStream(new CProtocol(
                    this.connection.ipv4,
                    EResponse.PENDING_FRIENDSHIP_REQUEST
            ));
        } catch (IOException ex) {
            System.out.println("sendPEdingFriendshipRequest@Engine");
        }
    }
    
    /**
     * Envia requisição de lista de amigos
     */
    public void sendFriendListRequest(){
        try {
            this.sendStream(new CProtocol(
                    this.connection.ipv4,
                    EResponse.REQUIRE_FRIENDLIST
            ));
        } catch (IOException ex) {
            System.out.println("sendFriendListRequest@Engine");
        }
    }
    
    /**
     * Envia requisição de lista de usuários nomes silimiares a likeThis
     * @param likeThis nome do usuario
     */
    public void sendProfileListRequest(String likeThis){
        try {
            this.sendStream(new CProtocol(
                    this.connection.ipv4,
                    EResponse.FIND_PROFILE_REQUEST,
                    likeThis
            ));
        } catch (IOException ex) {
            System.out.println("sendProfileListRequest@Engine");
        }
    }
    
    /**
     * Envia mudança no status para o servidor
     * @param status 
     */
    public void sendStatusChange(Integer status){
        try{
            this.sendStream(new CProtocol(
                    this.connection.ipv4,
                    EResponse.REQUIRE_CHANGE_STATUS,
                    status
            ));
        } catch (IOException ex){
            System.out.println("sendStatusChange");
        }
    }
    
    /**
     * Ação disparada com falha na conexão
     * Deve abortar outras janelas e redirecionar para a janela de login
     */
    protected void connectionFail(){
        try {
            Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setTitle("Falha na conexão");
            dialog.setHeaderText("Você está sem acesso ao servidor de mensagens.");
            dialog.setContentText("Verifique sua conexão e tente novamente mais tarde.");
            dialog.showAndWait();
            UScene smanager = UScene.getInstance();
            smanager.loadScene("GLogin");
            smanager.showStage("Círdan's Messenger Login", true);
        } catch (IOException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Começa a ouvir servidor
     * @param messageQueue 
     */
    public void startListening(ObservableQueue<CProtocol> messageQueue){
        this.serverListerner.setServerMessage(messageQueue);
        this.serverListerner.start();
    }
    
    public ConnectionInfo getClient() { return connection; }
    public ClientInfo getClientInfo() { return informations; }
    public void setClientInfo(ClientInfo clientInfo) { this.informations = clientInfo; }
    public String getServerIpv4() { return serverIpv4; }
    public void setServerIpv4(String serverIpv4) { this.serverIpv4 = serverIpv4; }
    
    /**
     * Destroi conexão
     * Ainda não implementada
     */
    public void destroy(){
        try {
            this.connection.socket.close();
        } catch (IOException ex) {
            System.out.println("Saindo.");
        } finally {
            this.connection = null;
            this.informations = null;
        }
    }
}
