package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.Chat;
import es.udc.tfg.tfgprojectbackend.model.entities.Message;
import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.PermissionException;

import java.util.List;

/**
 * Service interface for managing chat functionalities between users.
 */
public interface ChatService {

    /**
     * Creates a new chat between two users.
     *
     * @param initiatorId the ID of the user initiating the chat
     * @param participantUserName the username of the user being invited to the chat
     * @return the created Chat object
     * @throws InstanceNotFoundException if the user with the given username does not exist
     * @throws DuplicateInstanceException if a chat already exists between the users
     */
    Chat createChat(Long initiatorId, String participantUserName) throws InstanceNotFoundException, DuplicateInstanceException;

    /**
     * Retrieves a chat by its ID.
     *
     * @param initiatorId the ID of the user who initiated the chat
     * @param chatId the ID of the chat
     * @return the Chat object
     * @throws InstanceNotFoundException if the chat with the given ID does not exist
     */
    Chat getChat(Long initiatorId, Long chatId) throws InstanceNotFoundException;

    /**
     * Retrieves all the chats of a user.
     *
     * @param userId the ID of the user whose chats are to be retrieved
     * @return a list of Chat objects
     */
    List<Chat> getUserChats(Long userId);

    /**
     * Deletes a chat.
     *
     * @param userId the ID of the user requesting the deletion
     * @param chatId the ID of the chat to be deleted
     * @throws InstanceNotFoundException if the chat with the given ID does not exist
     */
    void deleteChat(Long userId, Long chatId) throws InstanceNotFoundException;

    /**
     * Sends a message in a chat.
     *
     * @param chatId the ID of the chat where the message will be sent
     * @param senderId the ID of the user sending the message
     * @param content the content of the message
     * @return the sent Message object
     * @throws InstanceNotFoundException if the chat or user does not exist
     */
    Message sendMessage(Long chatId, Long senderId, String content) throws InstanceNotFoundException;

    /**
     * Retrieves all messages exchanged between two users.
     *
     * @param userId the ID of the user whose messages are to be retrieved
     * @param userName the username of the other user in the conversation
     * @return a list of Message objects exchanged between the two users
     * @throws InstanceNotFoundException if the user or messages cannot be found
     */
    List<Message> getMessages(Long userId, String userName) throws InstanceNotFoundException;
}
