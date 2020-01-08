package com.pgb.identity.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotations for UNIQUE fields
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCredentialsValidator.class)
public @interface UniqueCredentials {
    String message() default "Credential already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
