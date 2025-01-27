package es.udc.tfg.tfgprojectbackend.rest.dtos;

import es.udc.tfg.tfgprojectbackend.model.entities.Chat;
import es.udc.tfg.tfgprojectbackend.model.entities.Message;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class ChatConversor {

    public static ChatDto toChatDto(Chat chat) {
        List<MessageDto> messageDtos = chat.getMessages().stream()
                .map(ChatConversor::toMessageDto)
                .collect(Collectors.toList());

        return new ChatDto(chat.getId(), chat.getInitiator().getId(), chat.getInitiator().getUserName(), chat.getParticipant().getId(), chat.getParticipant().getUserName(), messageDtos, chat.getTotalMessages());
    }

    public static MessageDto toMessageDto(Message message) {
        return new MessageDto(message.getId(), message.getChat().getId(), message.getSender().getId(), message.getContent(), toMillis(message.getSentAt()));
    }

    public static List<MessageDto> toMessageDtos(List<Message> messages) {
        return messages.stream()
                .map(ChatConversor::toMessageDto)
                .collect(Collectors.toList());
    }


    public static List<ChatDto> toChatDtoList(List<Chat> chats) {
        return chats.stream()
                .map(ChatConversor::toChatDto)
                .collect(Collectors.toList());
    }

    private static long toMillis(LocalDateTime date) {
        return date.truncatedTo(ChronoUnit.MINUTES)
                .atZone(ZoneOffset.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

}
