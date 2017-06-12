package protocol;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;

public class CProtocol implements Serializable {
    private InetAddress sender;
    private long senderId;
    private long receiverId;
    private Date timestamp;
    private EResponse type;
    private Object payload;

    public CProtocol() {
        this.timestamp = new Date();
    }    
    
    public CProtocol(EResponse type) {
         this.timestamp = new Date();
        this.type = type;
    }

    public CProtocol(InetAddress sender, EResponse type) {
        this.timestamp = new Date();
        this.sender = sender;
        this.type = type;
    }
    
    public CProtocol(InetAddress sender, long senderUnique, EResponse type) {
        this.timestamp = new Date();
        this.sender = sender;
        this.senderId = senderUnique;
        this.type = type;
    }

    public CProtocol(InetAddress sender, EResponse type, Object payload) {
        this.timestamp = new Date();
        this.sender = sender;
        this.type = type;
        this.payload = payload;
    }

    public CProtocol(InetAddress sender, long senderId, EResponse type, Object payload) {
        this.timestamp = new Date();
        this.sender = sender;
        this.senderId = senderId;
        this.type = type;
        this.payload = payload;
    }

    public CProtocol(InetAddress sender, long senderId, long receiverId, Date timestamp, EResponse type, Object payload) {
        this.sender = sender;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.timestamp = timestamp;
        this.type = type;
        this.payload = payload;
    }
    
    
    
    public InetAddress getSender() {
        return sender;
    }

    public void setSender(InetAddress sender) {
        this.sender = sender;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public EResponse getType() {
        return type;
    }

    public void setType(EResponse type) {
        this.type = type;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
