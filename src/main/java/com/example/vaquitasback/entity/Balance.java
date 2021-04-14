package com.example.vaquitasback.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Balance {
    private final BigDecimal value;
    private final User to;
    private final Date createdAt;
}
