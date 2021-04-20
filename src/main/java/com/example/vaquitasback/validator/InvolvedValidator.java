package com.example.vaquitasback.validator;

import com.example.vaquitasback.entity.Involved;
import com.example.vaquitasback.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.math.BigDecimal;



public class InvolvedValidator implements ConstraintValidator<InvolvedSumUp, Transaction> {
    Logger logger = LoggerFactory.getLogger(InvolvedValidator.class);

    @Override
    public boolean isValid(Transaction transaction, ConstraintValidatorContext constraintValidatorContext) {
        BigDecimal sum = new BigDecimal("0.0");
        for(Involved involved : transaction.getInvolved()){
            sum = sum.add(involved.getAmount());
        }
        logger.warn("sum: "+sum + " val: "+transaction.getAmount());
        return sum.equals(transaction.getAmount().add(new BigDecimal("0.0")));
    }
}
