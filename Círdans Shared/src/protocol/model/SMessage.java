
package protocol.model;

import java.io.Serializable;
import java.util.Date;

public class SMessage implements Serializable{
    private Long senderId;
    private String senderName;
    private Long recieverId;
    private String recieverName;
    private String message;
    private Date sentAt;

    public SMessage() {
    }

    public SMessage(Long senderId, Long recieverId, String message) {
        this.senderId = senderId;
        this.recieverId = recieverId;
        this.message = message;
    }

    public SMessage(Long senderId, Long recieverId, String message, Date sentAt) {
        this.senderId = senderId;
        this.recieverId = recieverId;
        this.message = message;
        this.sentAt = sentAt;
    }

    public SMessage(Long senderId, String senderName, Long recieverId, String recieverName, String message, Date sentAt) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.recieverId = recieverId;
        this.recieverName = recieverName;
        this.message = message;
        this.sentAt = sentAt;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Long getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(Long recieverId) {
        this.recieverId = recieverId;
    }

    public String getRecieverName() {
        return recieverName;
    }

    public void setRecieverName(String recieverName) {
        this.recieverName = recieverName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }
}
