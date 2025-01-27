package es.udc.tfg.tfgprojectbackend.rest.dtos;

public class MessageDto {
    private Long id;
    private Long chatId;
    private Long senderId;
    private String content;
    private Long sentAt;

    public MessageDto() {
    }

    public MessageDto(Long id, Long chatId, Long senderId, String content, Long sentAt) {
        this.id = id;
        this.chatId = chatId;
        this.senderId = senderId;
        this.content = content;
        this.sentAt = sentAt;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSentAt() {
        return sentAt;
    }

    public void setSentAt(Long sentAt) {
        this.sentAt = sentAt;
    }
}
