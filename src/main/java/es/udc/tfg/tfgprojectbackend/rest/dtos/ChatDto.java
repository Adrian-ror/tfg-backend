package es.udc.tfg.tfgprojectbackend.rest.dtos;

import java.util.List;

public class ChatDto {
    private Long id;
    private Long initiatorId;
    private String initiatorUserName;
    private Long participantId;
    private String participantUserName;
    private List<MessageDto> messages;
    private int totalMessages;

    public ChatDto() {
    }

    public ChatDto(Long chatId, Long initiatorId, String initiatorUserName, Long participantId, String participantUserName, List<MessageDto> messages, int totalMessages) {
        this.id = chatId;
        this.initiatorId = initiatorId;
        this.initiatorUserName = initiatorUserName;
        this.participantId = participantId;
        this.participantUserName = participantUserName;
        this.messages = messages;
        this.totalMessages = totalMessages;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Long initiatorId) {
        this.initiatorId = initiatorId;
    }

    public String getInitiatorUserName() {
        return initiatorUserName;
    }

    public void setInitiatorUserName(String initiatorUserName) {
        this.initiatorUserName = initiatorUserName;
    }


    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public String getParticipantUserName() {
        return participantUserName;
    }

    public void setParticipantUserName(String participantUserName) {
        this.participantUserName = participantUserName;
    }


    public List<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
    }

    public int getTotalMessages() {
        return totalMessages;
    }

    public void setTotalMessages(int totalMessages) {
        this.totalMessages = totalMessages;
    }
}
