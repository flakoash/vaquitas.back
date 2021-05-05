package com.example.vaquitasback.service;

import com.example.vaquitasback.entity.Transaction;
import com.example.vaquitasback.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;


@Service
@Validated
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
    public Transaction addTransaction(@Valid Transaction transaction) {
        return repository.save(transaction);
    }
}
