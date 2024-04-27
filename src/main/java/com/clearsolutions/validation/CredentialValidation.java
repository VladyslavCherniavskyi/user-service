package com.clearsolutions.validation;

import com.clearsolutions.util.TimeUtils;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Period;
import java.util.Date;

@Component
public class CredentialValidation {

    @Value("${own.environment.yearOld}")
    private Integer yearOld;

    public void dataLimitValidate(Date date) {
        var now = TimeUtils.toLocalDate(new Date());
        var startDateUnLimit = now.minus(Period.ofYears(yearOld));

        if (startDateUnLimit.isBefore(TimeUtils.toLocalDate(date))) {
            throw new ValidationException(
                    String.format(
                            "DataOfBirth: '%s' is incorrect. Data should be equal to or less than: '%s' ",
                            TimeUtils.toLocalDate(date), startDateUnLimit
                    )
            );
        }
    }

}
