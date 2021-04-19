package com.example.vaquitasback.validator;

import com.example.vaquitasback.entity.Involved;
import com.example.vaquitasback.entity.Transaction;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.math.BigDecimal;



public class InvolvedValidator implements ConstraintValidator<InvolvedSumUp, Transaction> {
    @Override
    public boolean isValid(Transaction transaction, ConstraintValidatorContext constraintValidatorContext) {
        BigDecimal sum = BigDecimal.valueOf(0);
        for(Involved involved : transaction.getInvolved()){
            sum = sum.add(involved.getAmount());
        }
        return sum.equals(transaction.getAmount());
    }
}
