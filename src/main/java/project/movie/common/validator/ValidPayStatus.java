package project.movie.common.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PayStatusValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPayStatus {
    String message() default "결제 상태는 1,2,3 중 하나여야 합니다.";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};
}
