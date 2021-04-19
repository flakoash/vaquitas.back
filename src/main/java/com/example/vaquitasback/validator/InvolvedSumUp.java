package com.example.vaquitasback.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;



@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {InvolvedValidator.class})
public @interface InvolvedSumUp {

    String message() default "Involved amounts dont sum up total amount..";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
