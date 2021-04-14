package com.example.vaquitasback.service;

import com.example.vaquitasback.entity.Balance;
import com.example.vaquitasback.entity.Group;
import com.example.vaquitasback.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GroupService implements GroupServiceInterface{

    @Autowired
    private GroupRepository repository;

    @Override
    public Iterable<Group> getAll(String userId) {
        Iterable<Group> allData = this.repository.findAll();
        allData = StreamSupport.stream(allData.spliterator(),false).map(data -> {
            data.setBalance(repository.getGroupBalanceById(data.getId(), userId));
            Long res = repository.getLastTransactionDate(data.getId());
            data.setLastTransaction(res!=null?res:0);
            return data;
        }).collect(Collectors.toList());
        return allData;
    }

    @Override
    public Group add(Group group) {
        return null;
    }

    @Override
    public Group settleUp(String groupId) {
        return null;
    }
}
