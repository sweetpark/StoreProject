package project.movie.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PayStatusValidator implements ConstraintValidator<ValidPayStatus, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && (value ==1 || value ==2 || value==3);
    }
}
