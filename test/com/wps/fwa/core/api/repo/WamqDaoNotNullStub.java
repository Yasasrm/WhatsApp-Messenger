package com.wps.fwa.core.api.repo;

import com.wps.fwa.core.api.domain.Wamq;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WamqDaoNotNullStub implements WamqDao{


    @Override
    public Optional<Wamq> findMessageInfoFromQueue(String messageId) {
        Wamq wamq = new Wamq();
        wamq.setWamqMsgId("1");
        return Optional.ofNullable(wamq);
    }

    @Override
    public Optional<List<String>> getMessageIdListFromBatchId(String batchId) {
        List<String> batchList = new ArrayList<>();
        batchList.add("1");
        batchList.add("2");
        return Optional.ofNullable(batchList);
    }

    @Override
    public void deleteMessageInfoFromQueue(String messageId) {
        System.out.println("Delete message for messageId: "+messageId);
    }

    @Override
    public void deleteBulk(String batchId) {
        System.out.println("Delete bulk for batchId: "+batchId);
    }
}