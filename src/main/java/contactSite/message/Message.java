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

    private boolean deletedBySender = false;

    private boolean deletedByReceiver = false;

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

    public boolean isDeletedBySender() {
        return deletedBySender;
    }

    public boolean isDeletedByReceiver() {
        return deletedByReceiver;
    }

    public void deleteBySender() {
        this.deletedBySender = true;
    }

    public void deleteByReceiver() {
        this.deletedByReceiver = true;
    }
}
