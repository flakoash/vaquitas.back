package com.example.vaquitasback.service;

import com.example.vaquitasback.entity.Involved;
import com.example.vaquitasback.entity.Transaction;
import com.example.vaquitasback.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService implements TransactionServiceInterface {

    private final TransactionRepository repository;
    private final InvolvedService involvedService;

    public TransactionService(TransactionRepository repository, InvolvedService involvedService){
        this.repository = repository;
        this.involvedService = involvedService;
    }

    @Override
    public Iterable<Transaction> getTransactions(long groupId) {
        return repository.getTransactionsByGroup_idOrderByCreatedAtDesc(groupId);
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        return repository.save(transaction);
    }
}
