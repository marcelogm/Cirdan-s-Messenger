package model;

import java.util.Date;

public class Password extends AModel {
    public final static String TABLE_NAME = "tbl_message";
    
    private String password;
    private String salt;
    private int iterations;

    public Password() {
    }

    public Password(long id, Date created, Date updated, int iterations, String password, String salt) {
        super.setId(id);
        super.setCreatedAt(created);
        super.setUpdatedAt(updated);
        this.password = password;
        this.salt = salt;
        this.iterations = iterations;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
}
