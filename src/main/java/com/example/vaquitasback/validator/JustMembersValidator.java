package com.example.vaquitasback.validator;

import com.example.vaquitasback.entity.Transaction;
import com.example.vaquitasback.entity.User;
import com.example.vaquitasback.repository.UserRepository;
import com.example.vaquitasback.service.GroupServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class JustMembersValidator implements ConstraintValidator<JustMembers, Transaction> {
    Logger logger = LoggerFactory.getLogger(JustMembersValidator.class);
    @Autowired
    private GroupServiceInterface groupService;
    @Autowired
    private UserRepository userService;

    @Override
    public boolean isValid(Transaction transaction, ConstraintValidatorContext constraintValidatorContext) {
        List<User> members = groupService.getMembers(transaction.getGroup().getId());

//        User owner = transaction.getOwner();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User owner = userService.findByUsername(authentication.getName());
            boolean inGroup = members.stream().anyMatch(member -> member.getId() == owner.getId());
            return inGroup;
        }
        return  false;
    }
}
