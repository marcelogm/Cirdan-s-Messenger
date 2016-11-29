/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Date;

public class Friendship extends AModel {
    public final static String TABLE_NAME = "rel_profile_profile";
    
    private long sender;
    private long reciever;
    private boolean accepted = false;
    private boolean blocked = false;

    public Friendship() {
    }

    public Friendship(long sender, long reciever) {
        this.sender = sender;
        this.reciever = reciever;
    }

    public Friendship(long id, Date created, Date updated, boolean accepted, boolean blocked, long reciever, long sender) {
        super.setId(id);
        super.setCreatedAt(created);
        super.setUpdatedAt(updated);
        this.accepted = accepted;
        this.blocked = blocked;
        this.sender = sender;
        this.reciever = reciever;
    }
    
    
    
    public long getSender() {
        return sender;
    }

    public void setSender(long sender) {
        this.sender = sender;
    }

    public long getReciever() {
        return reciever;
    }

    public void setReciever(long reciever) {
        this.reciever = reciever;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}