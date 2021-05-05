package com.example.vaquitasback.entity;

import com.example.vaquitasback.validator.InvolvedSumUp;
import com.example.vaquitasback.validator.JustMembers;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access= AccessLevel.PROTECTED, force = true)  // JPA requires a non args constructor
@RequiredArgsConstructor                                         // we still need an Args constructor
@Entity
@Table(name = "TRANSACTIONS")
@InvolvedSumUp
@JustMembers
public class Transaction {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private final long id;

    @NotNull
    private final String title;

    private final String description;

    @NotNull
    private final BigDecimal amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private final User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final Group group;

    @OneToMany(
            mappedBy = "transaction",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 1, message = "You should have at least one involved per transaction")
    private List<Involved> involved = new ArrayList<>();

    private long createdAt;

    @PrePersist
    void unixTimestamps() {
        this.createdAt = Instant.now().getEpochSecond();
    }

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
