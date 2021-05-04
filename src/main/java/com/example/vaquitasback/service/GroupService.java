package com.example.vaquitasback.service;

import com.example.vaquitasback.entity.Group;
import com.example.vaquitasback.entity.User;
import com.example.vaquitasback.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GroupService implements GroupServiceInterface{

    @Autowired
    private GroupRepository repository;

    @Override
    public Iterable<Group> getAll(long userId) {
        Iterable<Group> allData = this.repository.getAllByMembers_idIsIn(Arrays.asList(new Long[]{userId}));
        allData = StreamSupport.stream(allData.spliterator(),false)
                .map(data -> {
                    data.setBalance(repository.getGroupBalanceById(data.getId(), userId));
                    Long res = repository.getLastTransactionDate(data.getId());
                    data.setLastTransaction(res!=null?res:0);
                    return data;
                })
                .sorted(Comparator.comparingLong(Group::getLastTransaction).reversed())
                .collect(Collectors.toList());
        return allData;
    }

    @Override
    public Group add(Group group) {
        return repository.save(group);
    }

    @Override
    public Group settleUp(long groupId) {
        return null;
    }

    @Override
    public List<User> getMembers(long groupId) {
        return this.repository.getMembersById(groupId);
    }
}
