package com.wps.fwa.core.api.repo;

import com.wps.fwa.core.api.domain.Wamh;

import java.util.Optional;

public class WamhDaoNotNullStub implements WamhDao {

    @Override
    public Optional<Wamh> findMessageInfoFromHistory(String messageId) {
        Wamh wamh = new Wamh();
        wamh.setWamhId(1);
        wamh.setWamhMsgId("1");
        return Optional.ofNullable(wamh);
    }

    @Override
    public void setDeliveryStatus(Wamh wamh) {
        System.out.println("Delivery Status Saved: "+wamh.toString());
    }

    @Override
    public Integer getMessageReTryCount(String wamqMsgId) {
        return 6;
    }
}