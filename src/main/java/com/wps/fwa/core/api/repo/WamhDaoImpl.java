package com.wps.fwa.core.api.repo;

import com.wps.fwa.core.api.domain.Wamh;
import com.wps.fwa.core.api.enums.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Repository
public class WamhDaoImpl implements WamhDao {

    @Autowired
    EntityManager em;

    private Optional<Wamh> findById(Integer id) {
        Wamh wamh = null;
        try {
            Query query = em.createNamedQuery("wamh.findById");
            query.setParameter(1, id);
            wamh = (Wamh) query.getSingleResult();
        } catch (Exception e) {
        }
        return Optional.ofNullable(wamh);
    }

    @Override
    public Optional<Wamh> findMessageInfoFromHistory(String messageId) {
        Wamh wamh = null;
        try {
            Query query = em.createNamedQuery("wamh.findMessage");
            query.setParameter(1, messageId);
            wamh = (Wamh) query.getSingleResult();
        } catch (Exception e) {
        }
        return Optional.ofNullable(wamh);
    }

    @Override
    @Transactional
    public void setDeliveryStatus(Wamh wamh) {
        Wamh wamhFetched = findById(wamh.getWamhId()).orElse(wamh);
        if (wamhFetched.getWamhId() == null) {
            wamhFetched.setWamhId(0);
            em.persist(wamhFetched);
        } else {
            if (wamhFetched.getWamhBulkId() == null) wamhFetched.setWamhBulkId(wamh.getWamhBulkId());
            if (wamhFetched.getWamhPendingMsg() == null) wamhFetched.setWamhPendingMsg(wamh.getWamhPendingMsg());
            if (wamhFetched.getWamhPendingTime() == null) wamhFetched.setWamhPendingTime(wamh.getWamhPendingTime());
            if (wamhFetched.getWamhMsgCount() == null) wamhFetched.setWamhMsgCount(wamh.getWamhMsgCount());
            if (wamhFetched.getWamhCostPrMsg() == null) wamhFetched.setWamhCostPrMsg(wamh.getWamhCostPrMsg());
            if (wamhFetched.getWamhCurrCode() == null) wamhFetched.setWamhCurrCode(wamh.getWamhCurrCode());
            if (wamhFetched.getWamhDeliverMsg() == null) wamhFetched.setWamhDeliverMsg(wamh.getWamhDeliverMsg());
            if (wamhFetched.getWamhDeliverTime() == null) wamhFetched.setWamhDeliverTime(wamh.getWamhDeliverTime());
            if (wamhFetched.getWamhErrorCode() == null) wamhFetched.setWamhErrorCode(wamh.getWamhErrorCode());
            if (wamhFetched.getWamhError() == null) wamhFetched.setWamhError(wamh.getWamhError());
            if (wamhFetched.getWamhDoneAt() == null) wamhFetched.setWamhDoneAt(wamh.getWamhDoneAt());
            if (wamhFetched.getWamhSentAt() == null) wamhFetched.setWamhSentAt(wamh.getWamhSentAt());
            if (wamhFetched.getWamhSeenAt() == null) wamhFetched.setWamhSeenAt(wamh.getWamhSeenAt());
            if (wamhFetched.getWamhDeleteAt() == null) wamhFetched.setWamhDeleteAt(wamh.getWamhDeleteAt());
            wamhFetched.setWamhMUser(ApiConfig.DEFAULT_USER.getCode());
            em.merge(wamhFetched);
        }
    }

    @Override
    public Integer getMessageReTryCount(String wamqMsgId) {
        Query query = em.createNamedQuery("wamh.messageCount");
        query.setParameter(1, wamqMsgId);
        return castToInteger(query.getSingleResult());
    }

    private Integer castToInteger(Object result) {
        if (result instanceof Integer)
            return (Integer) result;
        if (result instanceof BigDecimal)
            return ((BigDecimal) result).intValueExact();
        if (result instanceof Long)
            return ((Long) result).intValue();
        return 0;
    }
}
