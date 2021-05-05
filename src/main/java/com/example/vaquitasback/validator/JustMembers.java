package com.example.vaquitasback.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {JustMembersValidator.class})
public @interface JustMembers {

    String message() default "Only members of the group can add transactions";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
