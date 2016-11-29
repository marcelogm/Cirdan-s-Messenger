package model;

import java.util.Date;

public class Profile extends AModel {
    public final static String TABLE_NAME = "tbl_profile";
    
    private String name;
    private String nick;
    private String email;
    private int status;
    private String subnick;
    private String ipv4;
    private Date onlineAt;
    private boolean activated;
    private String imageUrl;
    private long password;

    public Profile() {
    }

    public Profile(String name, String nick, String email, String subnick, String imageUrl) {
        this.name = name;
        this.nick = nick;
        this.email = email;
        this.subnick = subnick;
        this.imageUrl = imageUrl;
    }

    public Profile(String name, String nick, String email, int status, String subnick, String ipv4, Date onlineAt, boolean activated, String imageUrl, long password) {
        this.name = name;
        this.nick = nick;
        this.email = email;
        this.status = status;
        this.subnick = subnick;
        this.ipv4 = ipv4;
        this.onlineAt = onlineAt;
        this.activated = activated;
        this.imageUrl = imageUrl;
        this.password = password;
    }

    public Profile(long id, Date created, Date updated, boolean activated, String name, String nick, String email, String subnick, String ipv4, Date onlineAt, String imageUrl, int status,long password) {
        super.setId(id);
        super.setCreatedAt(created);
        super.setUpdatedAt(updated);
        this.activated = activated;
        this.name = name;
        this.nick = nick;
        this.email = email;
        this.subnick = subnick;
        this.ipv4 = ipv4;
        this.onlineAt = onlineAt;
        this.imageUrl = imageUrl;
        this.status = status;
        this.password = password;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSubnick() {
        return subnick;
    }

    public void setSubnick(String subnick) {
        this.subnick = subnick;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public Date getOnlineAt() {
        return onlineAt;
    }

    public void setOnlineAt(Date onlineAt) {
        this.onlineAt = onlineAt;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getPassword() {
        return password;
    }

    public void setPassword(long password) {
        this.password = password;
    }
}