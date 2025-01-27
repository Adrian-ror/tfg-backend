package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and managing Chat entities.
 * Extends CrudRepository and PagingAndSortingRepository for CRUD and pagination operations.
 */
public interface ChatDao extends CrudRepository<Chat, Long>, PagingAndSortingRepository<Chat, Long> {

    /**
     * Finds a chat by its initiator ID and chat ID.
     *
     * @param initiatorId the ID of the initiator of the chat.
     * @param id the ID of the chat.
     * @return an Optional containing the chat if found.
     */
    Optional<Chat> findByInitiatorIdAndId(Long initiatorId, Long id);

    /**
     * Finds all chats initiated by a specific user.
     *
     * @param initiatorId the ID of the initiator of the chats.
     * @return a list of chats initiated by the given user.
     */
    List<Chat> findByInitiatorId(long initiatorId);

    /**
     * Finds all chats in which a specific user is a participant.
     *
     * @param participantId the ID of the participant.
     * @return a list of chats in which the given user is a participant.
     */
    List<Chat> findByParticipantId(long participantId);

    /**
     * Finds a chat between two users, either initiator 1 and participant 1 or initiator 2 and participant 2.
     *
     * @param initiatorId1 the ID of the first initiator.
     * @param participantId1 the ID of the first participant.
     * @param initiatorId2 the ID of the second initiator.
     * @param participantId2 the ID of the second participant.
     * @return an Optional containing the chat if found.
     */
    Optional<Chat> findChatByInitiatorIdAndParticipantIdOrInitiatorIdAndParticipantId(
            Long initiatorId1, Long participantId1, Long initiatorId2, Long participantId2);
}
