package com.wps.fwa.core.api.repo;

import com.wps.fwa.core.api.domain.Wamh;

import java.util.Optional;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
public interface WamhDao {

    Optional<Wamh> findMessageInfoFromHistory(String messageId);

    void setDeliveryStatus(Wamh wamh);

    Integer getMessageReTryCount(String wamqMsgId);
}
