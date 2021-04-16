package com.example.vaquitasback.web;

import com.example.vaquitasback.entity.Group;
import com.example.vaquitasback.service.GroupServiceInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/group")
public class GroupController {

    private final GroupServiceInterface groupService;

    public GroupController(GroupServiceInterface groupService){
        this.groupService = groupService;
    }

    @GetMapping
    public Iterable<Group> listAll(long userId) {

//        Group gr[] = {new Group("1", "asd", "asd", Arrays.asList(new User("user_1", "user 1")), new Balance("asd", new BigDecimal(10), new User("user_1", "user 1"), new Date())),
//                new Group("2", "asd", "asd", Arrays.asList(new User("user_1", "user 1")), new Balance("asd", new BigDecimal(10), new User("user_1", "user 1"), new Date())),
//                new Group("3", "asd", "asd", Arrays.asList(new User("user_1", "user 1")), new Balance("asd", new BigDecimal(10), new User("user_1", "user 1"), new Date()))};
        Iterable<Group> gr = groupService.getAll(userId);
        return gr;
    }
}
