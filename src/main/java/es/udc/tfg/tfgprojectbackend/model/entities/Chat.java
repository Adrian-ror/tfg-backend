package es.udc.tfg.tfgprojectbackend.model.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Represents a chat between two users.
 * It allows for the management of chat-related functionalities.
 */
@Entity
public class Chat {

    private Long id;
    private User initiator;
    private User participant;
    private LocalDateTime createdAt;
    private Set<Message> messages = new HashSet<>();

    public Chat() {}

    /**
     * Constructs a new Chat instance.
     *
     * @param initiator   the user who initiates the chat
     * @param participant  the user who participates in the chat
     */
    public Chat(User initiator, User participant, LocalDateTime createdAt) {
        this.initiator = initiator;
        this.participant = participant;
        this.createdAt = createdAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "initiatorUserId")
    public User getInitiator() {
        return initiator;
    }

    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "participantUserId")
    public User getParticipant() {
        return participant;
    }

    public void setParticipant(User participant) {
        this.participant = participant;
    }

    @Column(name = "createdAt", nullable = false)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    /**
     * Adds a message to the chat.
     *
     * @param message the message to add
     */
    public void addMessage(Message message) {
        messages.add(message);
        message.setChat(this);
    }

    /**
     * Retrieves a message by its ID.
     *
     * @param messageId the ID of the message
     * @return an Optional containing the Message if found, otherwise empty
     */
    @Transient
    public Optional<Message> getMessage(Long messageId) {
        return messages.stream()
                .filter(message -> message.getId().equals(messageId))
                .findFirst();
    }

    /**
     * Removes a specific message from the chat.
     *
     * @param message the Message to remove
     */
    public void removeMessage(Message message) {
        messages.remove(message);
        message.setChat(null);
    }

    /**
     * Removes all messages from the chat.
     */
    public void removeAllMessages() {
        messages.clear();
    }

    /**
     * Checks if the chat contains any messages.
     *
     * @return true if there are no messages, false otherwise
     */
    @Transient
    public boolean isEmpty() {
        return messages.isEmpty();
    }

    /**
     * Gets the total number of messages in the chat.
     *
     * @return the total number of messages
     */
    @Transient
    public int getTotalMessages() {
        return messages.size();
    }
}
