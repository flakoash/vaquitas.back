package com.example.vaquitasback.service;

import com.example.vaquitasback.entity.Involved;
import com.example.vaquitasback.entity.Transaction;

import java.util.List;

public interface TransactionServiceInterface {
    Iterable<Transaction> getTransactions(long groupId);
    Transaction addTransaction(Transaction transaction);
}
