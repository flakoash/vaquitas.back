package com.example.vaquitasback.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor(access= AccessLevel.PROTECTED, force = true) // JPA requires a non args constructor
@RequiredArgsConstructor // we still need an Args constructor
@Entity
public class Involved {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private final long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private final User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transaction transaction;

    @NotNull
    private final BigDecimal amount;

    private long createdAt;

    @PrePersist
    void unixTimestamps() {
        this.createdAt = Instant.now().getEpochSecond();
    }

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

    @Override
    public String toString() {
        return "Involved{" +
                "id=" + id +
                ", user=" + user.getId() +
//                ", transaction=" + transaction.getId() +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                '}';
    }
}
