package es.udc.tfg.tfgprojectbackend.rest.controllers;

import es.udc.tfg.tfgprojectbackend.model.entities.Chat;
import es.udc.tfg.tfgprojectbackend.model.entities.Message;
import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.PermissionException;
import es.udc.tfg.tfgprojectbackend.model.services.ChatService;
import es.udc.tfg.tfgprojectbackend.rest.dtos.ChatDto;
import es.udc.tfg.tfgprojectbackend.rest.dtos.MessageDto;
import es.udc.tfg.tfgprojectbackend.rest.dtos.ChatParamsDto;
import es.udc.tfg.tfgprojectbackend.rest.dtos.MessageParamsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static es.udc.tfg.tfgprojectbackend.rest.dtos.ChatConversor.*;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ChatDto createChat(@RequestAttribute Long userId, @RequestBody ChatParamsDto params) throws InstanceNotFoundException, DuplicateInstanceException {
        Chat chat = chatService.createChat(userId, params.getParticipantUserName());
        return toChatDto(chat);
    }

    @GetMapping("/{chatId}")
    public ChatDto getChat(@RequestAttribute Long userId, @PathVariable Long chatId) throws InstanceNotFoundException {
        Chat chat = chatService.getChat(userId, chatId);
        return toChatDto(chat);
    }

    @GetMapping("/user")
    public List<ChatDto> getUserChats(@RequestAttribute Long userId) {
        List<Chat> chats = chatService.getUserChats(userId);
        return toChatDtoList(chats);
    }

    @DeleteMapping("/{chatId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChat(@RequestAttribute Long userId, @PathVariable Long chatId) throws InstanceNotFoundException {
        chatService.deleteChat(userId, chatId);
    }

    @PostMapping("/{chatId}/messages")
    public MessageDto sendMessage(@PathVariable Long chatId, @RequestAttribute Long userId, @RequestBody MessageParamsDto params) throws InstanceNotFoundException {
        Message message = chatService.sendMessage(chatId, userId, params.getContent());

        messagingTemplate.convertAndSend("/topic/chat/" + chatId, toMessageDto(message));


        return toMessageDto(message);
    }

    @GetMapping("/{participantUserName}/messages")
    public List<MessageDto> getMessages(@RequestAttribute Long userId, @PathVariable String participantUserName) throws InstanceNotFoundException {
        List<Message> messages = chatService.getMessages(userId, participantUserName);
        return toMessageDtos(messages);
    }
}
