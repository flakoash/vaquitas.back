package com.example.vaquitasback.repository;

import com.example.vaquitasback.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(exported = false)
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Iterable<Transaction> getTransactionsByGroup_id(long groupId);
}
