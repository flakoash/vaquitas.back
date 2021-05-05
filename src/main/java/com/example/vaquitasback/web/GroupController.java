package com.example.vaquitasback.web;

import com.example.vaquitasback.entity.Group;
import com.example.vaquitasback.repository.UserRepository;
import com.example.vaquitasback.service.GroupServiceInterface;
import com.example.vaquitasback.validator.JustMembersValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;


@RestController
@RequestMapping("/api/group")
public class GroupController {

    private final GroupServiceInterface groupService;
    private final UserRepository userRepository;

    public GroupController(GroupServiceInterface groupService, UserRepository userRepository){
        this.groupService = groupService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<Group> listAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Iterable<Group> gr = groupService.getAll(userRepository.findByUsername(auth.getName()).getId());
        return gr;
    }

    @PostMapping
    public Group addGroup(@Validated @RequestBody Group group) {
        return groupService.add(group);
    }
}
