package com.example.vaquitasback.repository;

import com.example.vaquitasback.entity.Involved;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(exported = false)
public interface InvolvedRepository extends CrudRepository<Involved, Long> {
    Iterable<Involved> getInvolvedByTransaction_Id (long groupId);
}
