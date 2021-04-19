package com.example.vaquitasback.web;

import com.example.vaquitasback.entity.Transaction;
import com.example.vaquitasback.service.TransactionServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionServiceInterface transactionService;
    public TransactionController(TransactionServiceInterface transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public Iterable<Transaction> getTransactions(long groupId){
        return this.transactionService.getTransactions(groupId);
    }
    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction){
        return this.transactionService.addTransaction(transaction);
    }

}
