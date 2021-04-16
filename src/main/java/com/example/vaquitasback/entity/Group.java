package com.example.vaquitasback.entity;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/*
*
* id: "1",
    name: "Group 1",
    members: [
      { id: "user_id", name: "User 1", photo: "https://i.pravatar.cc/50" },
    ],
    icon: "https://picsum.photos/50",
    balance: {
      id: "balance_id",
      value: -230,
      to: null,
      createdAt: 1617475021,
    },
* */
@Data
@NoArgsConstructor(access= AccessLevel.PROTECTED, force = true) // JPA requires a non args constructor
@RequiredArgsConstructor // we still need an Args constructor
@Entity
@Table(name = "Groups")
public class Group {
    @Id
    private final long id;
    @NotNull
    private final String name;
    private final String icon;

    @ManyToMany(targetEntity = User.class)
    @JoinTable(
            name = "User_Groups",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Size(min = 1, message = "You must have at least one user in the group")
    private final List<User> members;

    private long createdAt;
    private long lastSettleUp;

    @Transient
    private BigDecimal balance;
    @Transient
    private long lastTransaction;


    @PrePersist
    void unixTimestamps() {
        this.createdAt = Instant.now().getEpochSecond();
        this.lastSettleUp = Instant.now().getEpochSecond();
    }
}
