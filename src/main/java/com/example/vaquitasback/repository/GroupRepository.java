package com.example.vaquitasback.repository;

import com.example.vaquitasback.entity.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigDecimal;

@RestResource(exported = false)
public interface GroupRepository extends CrudRepository<Group, String> {
    @Query("SELECT coalesce(SUM( (Case when t.owner.id= :userId then 1 else -1 end) * t.amount), 0.00) FROM Transaction t inner join t.group g where g.id = :groupId")
    BigDecimal getGroupBalanceById(@Param("groupId") String groupId, @Param("userId") String userId);

    @Query(value = "SELECT coalesce(t.created_At, 0.00) FROM Transactions t WHERE t.group_id = :groupId ORDER BY t.created_At DESC LIMIT 1", nativeQuery = true)
    Long getLastTransactionDate(@Param("groupId") String groupId);
}
