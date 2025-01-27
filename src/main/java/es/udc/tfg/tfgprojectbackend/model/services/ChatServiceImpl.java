package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatDao chatDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Chat createChat(Long initiatorId, String participantUserName) throws InstanceNotFoundException, DuplicateInstanceException {

        User initiator = userDao.findById(initiatorId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.user", initiatorId));
        User participant = userDao.findByUserName(participantUserName)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.user", participantUserName));

        Optional<Chat> existingChat = chatDao.findChatByInitiatorIdAndParticipantIdOrInitiatorIdAndParticipantId(
                initiatorId, participant.getId(), participant.getId(), initiatorId);

        if (existingChat.isPresent()) {
            throw new DuplicateInstanceException("project.entities.chat", initiatorId);
        }

        Chat chat = new Chat(initiator, participant, LocalDateTime.now());
        return chatDao.save(chat);
    }

    @Override
    public Chat getChat(Long initiatorId, Long chatId) throws InstanceNotFoundException {

        Optional<Chat> optionalChat = chatDao.findById(chatId);

        return optionalChat.filter(chat ->
                        chat.getInitiator().getId().equals(initiatorId) ||
                                chat.getParticipant().getId().equals(initiatorId))
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.chat", chatId));
    }

    @Override
    public List<Chat> getUserChats(Long userId) {


        List<Chat> initiatorChats = chatDao.findByInitiatorId(userId);
        List<Chat> participantChats = chatDao.findByParticipantId(userId);

        initiatorChats.addAll(participantChats);
        return initiatorChats;
    }

    @Override
    public void deleteChat(Long userId, Long chatId) throws InstanceNotFoundException {

        Optional<Chat> optionalChat = chatDao.findById(chatId);

        Chat chat = optionalChat.filter(c ->
                        c.getInitiator().getId().equals(userId) ||
                                c.getParticipant().getId().equals(userId))
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.chat", chatId));

        for (Message message : chat.getMessages()) {
            messageDao.delete(message);
        }

        chat.removeAllMessages();

        chatDao.delete(chat);
    }

    @Override
    public Message sendMessage(Long chatId, Long senderId, String content) throws InstanceNotFoundException {

        Chat chat = chatDao.findById(chatId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.chat", chatId));

        if (!chat.getInitiator().getId().equals(senderId) && !chat.getParticipant().getId().equals(senderId)) {
            throw new InstanceNotFoundException("project.entities.user", senderId);
        }

        User sender = userDao.findById(senderId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.user", senderId));

        Message message = new Message(chat, sender, content, LocalDateTime.now());
        chat.addMessage(message);
        return messageDao.save(message);
    }

    @Override
    public List<Message> getMessages(Long userId, String userName) throws InstanceNotFoundException {

        User participant = userDao.findByUserName(userName)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.user", userName));

        Chat chat = chatDao.findChatByInitiatorIdAndParticipantIdOrInitiatorIdAndParticipantId(
                        userId, participant.getId(), participant.getId(), userId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.chat", userName));


        if (!chat.getInitiator().getId().equals(userId) && !chat.getParticipant().getId().equals(userId)) {
            throw new InstanceNotFoundException("project.entities.user", userId);
        }

        return chat.getMessages().stream()
                .sorted(Comparator.comparing(Message::getSentAt))
                .collect(Collectors.toList());
    }
}
