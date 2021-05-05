package com.example.vaquitasback.service;

import com.example.vaquitasback.entity.Transaction;

import javax.validation.Valid;


public interface TransactionServiceInterface {
    Iterable<Transaction> getTransactions(long groupId);
    Transaction addTransaction(@Valid Transaction transaction);
}
