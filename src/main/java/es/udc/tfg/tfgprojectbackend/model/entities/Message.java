package es.udc.tfg.tfgprojectbackend.model.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a message sent in a chat.
 * Each message is associated with a specific chat and has a sender.
 */
@Entity
public class Message {

    private Long id;
    private Chat chat;
    private User sender;
    private String content;
    private LocalDateTime sentAt;

    /**
     * Default constructor.
     */
    public Message() {}

    /**
     * Constructs a new Message instance.
     *
     * @param chat    the chat to which this message belongs
     * @param sender  the user who sends the message
     * @param content the content of the message
     * @param sentAt  the timestamp of when the message was sent
     */
    public Message(Chat chat, User sender, String content, LocalDateTime sentAt) {
        this.chat = chat;
        this.sender = sender;
        this.content = content;
        this.sentAt = sentAt;
    }

    /**
     * Gets the unique identifier for the message.
     *
     * @return the message id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the message.
     *
     * @param id the message id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the chat to which this message belongs.
     *
     * @return the chat
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "chatId", nullable = false)
    public Chat getChat() {
        return chat;
    }

    /**
     * Sets the chat to which this message belongs.
     *
     * @param chat the chat
     */
    public void setChat(Chat chat) {
        this.chat = chat;
    }

    /**
     * Gets the user who sent the message.
     *
     * @return the sender user
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "senderId", nullable = false)
    public User getSender() {
        return sender;
    }

    /**
     * Sets the user who sends the message.
     *
     * @param sender the sender user
     */
    public void setSender(User sender) {
        this.sender = sender;
    }

    /**
     * Gets the content of the message.
     *
     * @return the message content
     */
    @Column(name = "content", nullable = false)
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the message.
     *
     * @param content the message content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the timestamp of when the message was sent.
     *
     * @return the sent timestamp
     */
    @Column(name = "sentAt", nullable = false)
    public LocalDateTime getSentAt() {
        return sentAt;
    }

    /**
     * Sets the timestamp of when the message was sent.
     *
     * @param sentAt the sent timestamp
     */
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    /**
     * Formats the sent timestamp into a readable string.
     *
     * @return a formatted string of the sent timestamp
     */
    public String formatSentAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return sentAt.format(formatter);
    }
}
