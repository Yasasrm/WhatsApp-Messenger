package com.wps.fwa.core.api.repo;

import com.wps.fwa.core.api.domain.Wamq;

import java.util.List;
import java.util.Optional;

public class WamqDaoNullStub implements WamqDao{


    @Override
    public Optional<Wamq> findMessageInfoFromQueue(String messageId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<String>> getMessageIdListFromBatchId(String batchId) {
        return Optional.empty();
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