package com.example.vaquitasback.service;

import com.example.vaquitasback.entity.Group;
import com.example.vaquitasback.entity.User;

import java.util.List;

public interface GroupServiceInterface {
    Iterable<Group> getAll(long userId);
    Group add(Group group);
    Group settleUp(long groupId);
    List<User> getMembers(long groupId);
}
