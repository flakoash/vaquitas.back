package com.example.vaquitasback.service;

import com.example.vaquitasback.entity.Transaction;


public interface TransactionServiceInterface {
    Iterable<Transaction> getTransactions(long groupId);
    Transaction addTransaction(Transaction transaction);
}
