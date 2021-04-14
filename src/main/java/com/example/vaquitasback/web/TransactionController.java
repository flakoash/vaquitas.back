package com.example.vaquitasback.web;

import com.example.vaquitasback.entity.Transaction;
import com.example.vaquitasback.service.TransactionServiceInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionServiceInterface transactionService;
    public TransactionController(TransactionServiceInterface transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public Iterable<Transaction> getTransactions(String groupId){
        return this.transactionService.getTransactions(groupId);
    }

}
