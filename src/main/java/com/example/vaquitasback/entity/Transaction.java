package com.example.vaquitasback.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(value = { "group" })                       // we dont need to send groups through API requests
@NoArgsConstructor(access= AccessLevel.PROTECTED, force = true)  // JPA requires a non args constructor
@RequiredArgsConstructor                                         // we still need an Args constructor
@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {
    @Id
    private final String id;
    private final String title;
    private final String description;
    private final BigDecimal amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private final User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private final Group group;

    private long createdAt;
}
