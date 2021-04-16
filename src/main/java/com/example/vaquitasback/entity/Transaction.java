package com.example.vaquitasback.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(value = { "group", "involved" })            // we dont need to send groups through API requests
@NoArgsConstructor(access= AccessLevel.PROTECTED, force = true)  // JPA requires a non args constructor
@RequiredArgsConstructor                                         // we still need an Args constructor
@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {
    @Id
    private final long id;
    private final String title;
    private final String description;
    private final BigDecimal amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private final User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @NotNull
    private final Group group;

    @OneToMany(
            mappedBy = "transaction",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Size(min = 1, message = "You should have at least one involved per transaction")
    private List<Involved> involved = new ArrayList<>();

    private long createdAt;

    public void setInvolved(List<Involved> involvedUsers) {
        this.involved = new ArrayList<>();
        for (Involved involved : involvedUsers) {
            this.involved.add(involved);
            involved.setTransaction(this);
        }
    }

    public void removeInvolvedUsers(List<Involved> involvedUsers) {
        for (Involved involved : involvedUsers) {
            this.involved.remove(involved);
            involved.setTransaction(null);
        }
    }
}
