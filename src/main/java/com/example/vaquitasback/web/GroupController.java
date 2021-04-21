package com.example.vaquitasback.web;

import com.example.vaquitasback.entity.Group;
import com.example.vaquitasback.service.GroupServiceInterface;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/group")
public class GroupController {

    private final GroupServiceInterface groupService;

    public GroupController(GroupServiceInterface groupService){
        this.groupService = groupService;
    }

    @GetMapping
    public Iterable<Group> listAll(long userId) {

        Iterable<Group> gr = groupService.getAll(userId);
        return gr;
    }

    @PostMapping
    public Group addGroup(@Validated @RequestBody Group group) {
        return groupService.add(group);
    }
}
