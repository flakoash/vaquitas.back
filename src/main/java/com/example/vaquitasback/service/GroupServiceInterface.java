package com.example.vaquitasback.service;

import com.example.vaquitasback.entity.Group;

public interface GroupServiceInterface {
    Iterable<Group> getAll(long userId);
    Group add(Group group);
    Group settleUp(long groupId);
}
