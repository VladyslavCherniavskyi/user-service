package com.clearsolutions.validation;

import com.clearsolutions.util.TimeUtils;
import com.clearsolutions.validation.anotation.Adult;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Period;
import java.util.Date;

@Component
public class AdultValidator implements ConstraintValidator<Adult, Date> {

    @Value("${own.environment.yearOld}")
    private Integer yearOld;

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        var now = TimeUtils.toLocalDate(new Date());
        var startDateUnLimit = now.minus(Period.ofYears(yearOld));

        if (startDateUnLimit.isBefore(TimeUtils.toLocalDate(date))) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    String.format(
                            "DataOfBirth: '%s' is incorrect. Data should be equal to or less than: '%s' ",
                            TimeUtils.toLocalDate(date), startDateUnLimit
                    )
            ).addBeanNode().addConstraintViolation();
            return false;
        }
        return true;
    }
}
