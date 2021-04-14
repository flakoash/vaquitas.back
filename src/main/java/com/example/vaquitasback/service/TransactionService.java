package com.example.vaquitasback.service;

import com.example.vaquitasback.entity.Transaction;
import com.example.vaquitasback.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService implements TransactionServiceInterface {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository){
        this.repository = repository;
    }


    @Override
    public Iterable<Transaction> getTransactions(String groupId) {
        return repository.getTransactionsByGroup_id(groupId);
    }
}
