package cz.idomatojde.dao;

import cz.idomatojde.dao.common.BaseDAOImpl;
import cz.idomatojde.entity.TimetableChatMessage;
import cz.idomatojde.entity.TimetableEntry;
import cz.idomatojde.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO implementation for Timetable chat messages
 *
 * @author Michal Hazdra
 */
@Repository
public class ChatMessagesDAOImpl extends BaseDAOImpl<TimetableChatMessage> implements ChatMessagesDAO {

    public ChatMessagesDAOImpl() {
        super(TimetableChatMessage.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMessage(User sender, TimetableEntry entry, String text) {
        TimetableChatMessage message = new TimetableChatMessage();
        message.setSender(sender);
        message.setTimetableEntry(entry);
        message.setText(text);

        em.persist(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TimetableChatMessage> getAllMessagesForEntry(TimetableEntry entry) {
        return em.createQuery("select tcm from TimetableChatMessage tcm where tcm.timetableEntry = :entry",
                TimetableChatMessage.class)
                .setParameter("entry", entry)
                .getResultList();
    }
}
