package com.example.vaquitasback.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor(access= AccessLevel.PROTECTED, force = true) // JPA requires a non args constructor
@RequiredArgsConstructor // we still need an Args constructor
@Entity
public class Involved {
    @Id
    private final long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private final User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transaction transaction;

    private final BigDecimal amount;

    private final long createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Involved )) return false;
        return id != 0 && id==((Involved) o).getId();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
