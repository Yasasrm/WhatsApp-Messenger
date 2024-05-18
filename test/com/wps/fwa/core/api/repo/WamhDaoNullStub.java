package com.wps.fwa.core.api.repo;

import com.wps.fwa.core.api.domain.Wamh;

import java.util.Optional;

public class WamhDaoNullStub implements WamhDao {

    @Override
    public Optional<Wamh> findMessageInfoFromHistory(String messageId) {
        return Optional.empty();
    }

    @Override
    public void setDeliveryStatus(Wamh wamh) {
        System.out.println("Delivery Status Saved: "+wamh.toString());
    }

    @Override
    public Integer getMessageReTryCount(String wamqMsgId) {
        return null;
    }
}