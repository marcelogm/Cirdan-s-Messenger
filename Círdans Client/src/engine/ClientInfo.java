package engine;

/**
 * Classe de encapsulamento de dados do usuário
 * @author Marcelo Gomes Martins
 */
public class ClientInfo {
    public String name;
    public String nick;
    public String email;
    public Integer status;
    public String subnick;
    public String imageUrl;
    
    public ClientInfo(String name, String nick, String email, String subnick, String imageUrl, Integer status) {
        this.name = name;
        this.nick = nick;
        this.email = email;
        this.subnick = subnick;
        this.imageUrl = imageUrl;
        this.status = status;
    }
}
