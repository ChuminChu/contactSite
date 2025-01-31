package contactSite.message;

import jakarta.persistence.*;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String senderId;

    @Column(nullable = false)
    private String receiverId;

    private String senderName;

    public Message() {
    }


    public Message(String senderId, String receiverId, String senderName) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.senderName = senderName;
    }

    public Long getId() {
        return id;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getSenderName() {
        return senderName;
    }
}
