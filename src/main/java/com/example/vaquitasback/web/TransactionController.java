package com.example.vaquitasback.web;

import com.example.vaquitasback.entity.Transaction;
import com.example.vaquitasback.entity.User;
import com.example.vaquitasback.repository.UserRepository;
import com.example.vaquitasback.service.GroupServiceInterface;
import com.example.vaquitasback.service.TransactionServiceInterface;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionServiceInterface transactionService;
    private final GroupServiceInterface groupService;
    private final UserRepository userService;

    public TransactionController(TransactionServiceInterface transactionService, GroupServiceInterface groupService, UserRepository userService) {
        this.transactionService = transactionService;
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping
    public Iterable<Transaction> getTransactions(long groupId){
        return this.transactionService.getTransactions(groupId);
    }
    @PostMapping
    public Transaction addTransaction(@Validated @RequestBody Transaction transaction){
        return this.transactionService.addTransaction(transaction);
    }

}
