package engine;

import app.console.Main;
import facade.Facade;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Password;
import model.Profile;
import protocol.CProtocol;
import protocol.model.SLogin;
import protocol.EResponse;
import protocol.EStatus;
import protocol.model.SProfile;
import util.UPassword;

public class Engine extends Thread{
    public static final int PORT = 5556;
    public boolean abort = false;

    private static Engine engine;
    
    private ClientTable clientTable;
    private ServerSocket server;
    
    private Facade f = Facade.getInstance();
    
    private Engine() {
        try {
            this.clientTable = ClientTable.getInstance();
            this.server = new ServerSocket(PORT);
        } catch (IOException ex) {
            System.out.println("Já existe uma conexão ativa. \nFeche todos o serviços operando na porta 5555.");
            System.exit(-1);
        }
    }
    
    public static Engine getInstance() {
        if(engine == null) {
            engine = new Engine();
        }
        return engine;
    }
    
    @Override
    public void run(){
        System.out.println("Serviço iniciado.");
        while(!this.abort){
            try {
                Socket socket = this.server.accept();
                this.newClient(socket);
            } catch (IOException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.destroy();
    }
    
    private void newClient(Socket socket) throws IOException, ClassNotFoundException {
        boolean connected = false;
        CProtocol message = this.receiveStream(socket);
        switch(message.getType()){
            case REQUIRE_AUTH:
                if(Main.DEBUG_WATCHER) System.out.println(socket.getInetAddress() + " tentando autenticar-se.");
                connected = requireAuth(message, socket);
                break;
            case REQUIRE_NEW_ACCOUNT:
                if(Main.DEBUG_WATCHER) System.out.println(socket.getInetAddress() + " tentando cadastrar-se.");
                connected = this.requireNewAccount(message, socket);
                break;
            case REQUIRE_PASSWORD_RESET:
                break;
            default:
                System.out.println("Requisição fora dos padrões.");
        }
        if (!connected) socket.close();
    }
    
    /**
     * Executa protocolo
     * REQUIRE_AUTH
     * @param message protocolo
     * @param socket conexão
     * @return se está autenticado
     * @throws IOException 
     */
    private boolean requireAuth(CProtocol message, Socket socket) throws IOException{
        Profile profile =  null;
        boolean connected = false;
        SLogin credential = ((SLogin)message.getPayload());
        profile = f.findProfileByEmail(credential.getEmail());
        if (profile == null){
            this.sendErrorMessage(socket, EResponse.EMAIL_NOT_FOUND);
        } else if (this.authenticateClient(profile, credential.getPassword())) {
            this.createNewConnection(socket, profile);
            this.sendSucessful(socket, profile, EResponse.AUTH_SUCCESSFUL);
            connected = true;
        } else {
            this.sendErrorMessage(socket, EResponse.PASSWORD_DOESNT_MATCH);        
        }
        return connected;
    }
    
    /**
     * Executa protocolo 
     * REQUIRE_NEW_ACCOUNT
     * @param message protocolo
     * @param socket conexão
     * @return se está conectado
     * @throws IOException 
     */
    private boolean requireNewAccount(CProtocol message, Socket socket) throws IOException{
        Profile profile =  null;
        boolean connected = false;
        SProfile profileInfo = ((SProfile)message.getPayload());
        profile = f.findProfileByEmail(profileInfo.getEmail());
        if (profile != null){
            this.sendErrorMessage(socket, EResponse.EMAIL_ALREADY_EXISTS);
        } else {
            profile = this.createNewAccount(socket, profileInfo);
            this.createNewConnection(socket, profile);
            this.sendSucessful(socket, profile, EResponse.REGISTRATION_SUCESSFUL);
            connected = true;
        }
        return connected;
    }
    
    private void sendSucessful(Socket socket, Profile profile, EResponse what) throws IOException{
        this.sendStream(socket, new CProtocol(
                socket.getLocalAddress(),
                profile.getId(),
                what,
                new SProfile(
                        profile.getName(),
                        profile.getNick(),
                        profile.getEmail(),
                        profile.getSubnick(),
                        profile.getImageUrl(),
                        profile.getStatus()
                )
        ));
    }
    
    private void sendErrorMessage(Socket socket, EResponse what) throws IOException{
        this.sendStream(socket, new CProtocol(
                socket.getInetAddress(),
                what
        ));
    }

    private void createNewConnection(Socket socket, Profile profile){
        ClientConnection conn = new ClientConnection(
                socket.getLocalAddress(),
                profile.getId(),
                socket
        );
        this.clientTable.clients.put(profile.getId(), conn);
        conn.start();
    }
    
    private Profile createNewAccount(Socket socket, SProfile profileInfo) {
        Facade f = Facade.getInstance();
        Password pass = UPassword.generatePassword(profileInfo.getPassword());
        f.save(pass);
        pass = f.findPasswordByHash(pass.getPassword());
        Profile profile = new Profile(
                profileInfo.getName(),
                profileInfo.getNick(),
                profileInfo.getEmail(),
                EStatus.ONLINE.status,
                "Olá, eu estou usando Círdan's Messenger!",
                socket.getInetAddress().toString(),
                new Date(),
                true,
                profileInfo.getImageUrl(),
                pass.getId()
        );
        f.save(profile);
        profile = f.findProfileByEmail(profile.getEmail());
        return profile;
    }
    
    private boolean authenticateClient(Profile profile, String bidPass){
        Password pass = f.findPasswordByProfileId(profile.getId());
        String encBidPass = UPassword.passwordIterate(
                bidPass, 
                pass.getSalt(),
                pass.getIterations()
        );
        if(pass.getPassword().equals(encBidPass)) return true;
        return false;
    }
    
    private void sendStream(Socket s, CProtocol message) throws IOException{
        ObjectOutputStream toServer = new ObjectOutputStream(s.getOutputStream());
        toServer.writeObject(message);
    }
    
    private CProtocol receiveStream(Socket s) throws IOException, ClassNotFoundException{
        ObjectInputStream fromServer = new ObjectInputStream(s.getInputStream());
        CProtocol message = (CProtocol)fromServer.readObject();
        return message;
    }
    
    @Override
    public void destroy(){
        try {
            server.close();
            clientTable.clients.forEach((k, v) ->{
                v.destroy();
            });
            clientTable.clients.clear();
        } catch (IOException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}