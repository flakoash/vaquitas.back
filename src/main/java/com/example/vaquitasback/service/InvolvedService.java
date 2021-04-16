package com.example.vaquitasback.service;

import com.example.vaquitasback.entity.Involved;
import com.example.vaquitasback.repository.InvolvedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvolvedService implements InvolvedServiceInterface{
    @Autowired
    InvolvedRepository repository;

    @Override
    public Iterable<Involved> getAll(long transactionId) {
        return repository.getInvolvedByTransaction_Id(transactionId);
    }

    @Override
    public Involved add(Involved involved) {
        return repository.save(involved);
    }
}
