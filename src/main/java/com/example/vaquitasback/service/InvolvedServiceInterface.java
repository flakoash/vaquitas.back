package com.example.vaquitasback.service;

import com.example.vaquitasback.entity.Involved;

public interface InvolvedServiceInterface {
    Iterable<Involved> getAll(long transactionId);
    Involved add(Involved involved);
}
