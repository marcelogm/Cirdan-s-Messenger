package protocol.model;

import java.io.Serializable;
import java.util.Date;

public class SMessage implements Serializable {

    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private String message;
    private Date sentAt;

    public SMessage() {
    }

    public SMessage(Long senderId, Long receiverId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }

    public SMessage(Long senderId, Long receiverId, String message, Date sentAt) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.sentAt = sentAt;
    }

    public SMessage(Long senderId, String senderName, Long receiverId, String receiverName, String message, Date sentAt) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
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

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
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
