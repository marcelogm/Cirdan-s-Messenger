package protocol.model;

import java.io.Serializable;
/**
 * Entidade de modelo para o Usuário
 * @author Marcelo Gomes Martins
 */
public class SProfile implements Serializable{
    private Long id;
    private String name;
    private String nick;
    private String email;
    private Integer status;
    private String subnick;
    private String imageUrl;
    private String password;

    public SProfile(Long id, String name, String nick, Integer status, String subnick, String imageUrl) {
        this.id = id;
        this.name = name;
        this.nick = nick;
        this.status = status;
        this.subnick = subnick;
        this.imageUrl = imageUrl;
    }
    public SProfile(Long id, String name, String nick, String imageUrl) {
        this.id = id;
        this.name = name;
        this.nick = nick;
        this.imageUrl = imageUrl;
    }

    public SProfile(String name, String nick, String email, String imageUrl, String password) {
        this.name = name;
        this.nick = nick;
        this.email = email;
        this.imageUrl = imageUrl;
        this.password = password;
    }
    
    public SProfile(String name, String nick, String email, String subnick, String imageUrl, Integer status) {
        this.name = name;
        this.nick = nick;
        this.email = email;
        this.subnick = subnick;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SProfile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSubnick() {
        return subnick;
    }

    public void setSubnick(String subnick) {
        this.subnick = subnick;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}