package com.example.vaquitasback.service;

import com.example.vaquitasback.entity.Transaction;
import com.example.vaquitasback.entity.User;
import com.example.vaquitasback.repository.TransactionRepository;
import com.example.vaquitasback.repository.UserRepository;
import com.example.vaquitasback.web.ForbiddenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;


@Service
@Validated
public class TransactionService implements TransactionServiceInterface {

    private final TransactionRepository repository;
    private final GroupService groupService;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository repository, GroupService groupService, UserRepository userRepository){
        this.repository = repository;
        this.groupService = groupService;
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<Transaction> getTransactions(long groupId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User owner = userRepository.findByUsername(authentication.getName());
        boolean isMember = groupService.getMembers(groupId).stream().anyMatch(member -> member.getId() == owner.getId());
        if (isMember)
            return repository.getTransactionsByGroup_idOrderByCreatedAtDesc(groupId);
        else
            throw new ForbiddenException();
    }

    @Override
    public Transaction addTransaction(@Valid Transaction transaction) {
        return repository.save(transaction);
    }
}
