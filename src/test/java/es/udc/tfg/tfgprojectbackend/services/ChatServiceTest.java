package es.udc.tfg.tfgprojectbackend.services;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.services.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ChatServiceTest {

    private final Long NON_EXISTENT_ID = -1L;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ChatDao chatDao;

    private User createUser(String userName) {
        return new User(userName, "password", "FirstName", "LastName",
                userName + "@example.com", "123456789", null, User.RoleType.CLIENT, User.StatusType.ACTIVE);
    }

    private Chat createChat(User initiator, User participant) {
        return new Chat(initiator, participant, LocalDateTime.now());
    }

    @Test
    public void testCreateChat() throws InstanceNotFoundException, DuplicateInstanceException {
        User initiator = createUser("user1");
        User participant = createUser("user2");

        userDao.save(initiator);
        userDao.save(participant);

        Chat chat = chatService.createChat(initiator.getId(), participant.getUserName());

        assertNotNull(chat);
        assertEquals(initiator, chat.getInitiator());
        assertEquals(participant, chat.getParticipant());
    }

    @Test
    public void testCreateDuplicateChat() throws InstanceNotFoundException, DuplicateInstanceException {
        User initiator = createUser("user1");
        User participant = createUser("user2");

        userDao.save(initiator);
        userDao.save(participant);

        chatService.createChat(initiator.getId(), participant.getUserName());

        assertThrows(DuplicateInstanceException.class, () ->
                chatService.createChat(initiator.getId(), participant.getUserName()));
    }

    @Test
    public void testGetChat() throws InstanceNotFoundException, DuplicateInstanceException {
        User initiator = createUser("user1");
        User participant = createUser("user2");

        userDao.save(initiator);
        userDao.save(participant);

        Chat chat = chatService.createChat(initiator.getId(), participant.getUserName());

        Chat retrievedChat = chatService.getChat(initiator.getId(), chat.getId());

        assertNotNull(retrievedChat);
        assertEquals(chat, retrievedChat);
    }

    @Test
    public void testGetMessages() throws InstanceNotFoundException, DuplicateInstanceException {
        User initiator = createUser("user1");
        User participant = createUser("user2");

        userDao.save(initiator);
        userDao.save(participant);

        Chat chat = chatService.createChat(initiator.getId(), participant.getUserName());

        chatService.sendMessage(chat.getId(), initiator.getId(), "Hello");
        chatService.sendMessage(chat.getId(), participant.getId(), "Hi there");

        List<Message> messages = chatService.getMessages(initiator.getId(), participant.getUserName());

        assertEquals(2, messages.size());
        assertEquals("Hello", messages.get(0).getContent());
        assertEquals("Hi there", messages.get(1).getContent());
    }

    @Test
    public void testDeleteChat() throws InstanceNotFoundException, DuplicateInstanceException {
        User initiator = createUser("user1");
        User participant = createUser("user2");

        userDao.save(initiator);
        userDao.save(participant);

        Chat chat = chatService.createChat(initiator.getId(), participant.getUserName());

        chatService.deleteChat(initiator.getId(), chat.getId());

        assertThrows(InstanceNotFoundException.class, () ->
                chatService.getChat(initiator.getId(), chat.getId()));
    }

    @Test
    public void testSendMessageToNonExistentChat() {
        assertThrows(InstanceNotFoundException.class, () ->
                chatService.sendMessage(NON_EXISTENT_ID, NON_EXISTENT_ID, "Message"));
    }

    @Test
    public void testGetMessagesForNonExistentChat() {
        assertThrows(InstanceNotFoundException.class, () ->
                chatService.getMessages(NON_EXISTENT_ID, "nonexistent"));
    }

    @Test
    public void testSendMessageWithUnauthorizedUser() throws InstanceNotFoundException, DuplicateInstanceException {
        User initiator = createUser("user1");
        User participant = createUser("user2");

        userDao.save(initiator);
        userDao.save(participant);

        Chat chat = chatService.createChat(initiator.getId(), participant.getUserName());

        assertThrows(InstanceNotFoundException.class, () ->
                chatService.sendMessage(chat.getId(), NON_EXISTENT_ID, "Message"));
    }
}
