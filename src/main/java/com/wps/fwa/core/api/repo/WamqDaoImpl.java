package com.wps.fwa.core.api.repo;

import com.wps.fwa.core.api.domain.Wamq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Repository
public class WamqDaoImpl implements WamqDao {

    @Autowired
    EntityManager em;

    @Override
    public Optional<Wamq> findMessageInfoFromQueue(String messageId) {
        Wamq wamq = null;
        try {
            Query query = em.createNamedQuery("wamq.findMessage");
            query.setParameter(1, messageId);
            wamq = (Wamq) query.getSingleResult();
        } catch (Exception e) {
        }
        return Optional.ofNullable(wamq);
    }

    @Override
    public Optional<List<String>> getMessageIdListFromBatchId(String batchId) {
        List<String> bulk = null;
        try {
            Query query = em.createNamedQuery("wamq.findBulk");
            query.setParameter(1, batchId);
            bulk = query.getResultList();
        } catch (Exception e) {
        }
        return Optional.ofNullable(bulk);
    }

    @Override
    @Transactional
    public void deleteMessageInfoFromQueue(String messageId) {
        Query query = em.createNamedQuery("wamq.deleteMessage");
        query.setParameter(1, messageId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void deleteBulk(String batchId) {
        Query query = em.createNamedQuery("wamq.deleteBulk");
        query.setParameter(1, batchId);
        query.executeUpdate();
    }

}
