package com.clearsolutions.validation;

import com.clearsolutions.util.TimeUtils;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yaml")
class AdultValidatorTest {

    @InjectMocks
    private AdultValidator adultValidator;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.LeafNodeBuilderCustomizableContext leafNodeBuilderCustomizableContext;
    @Captor
    private ArgumentCaptor<String> messageCaptor;
    @Value("${own.environment.yearOld:18}")
    private Integer yearOld;

    @Test
    void IsValid() {
        //given
        var now = TimeUtils.toLocalDate(new Date());
        ReflectionTestUtils.setField(adultValidator, "yearOld", yearOld);
        LocalDate validDateOfBirth = now.minusYears(yearOld).minusDays(1);

        try (var mockedStatic = Mockito.mockStatic(TimeUtils.class)) {
            mockedStatic.when(
                            () -> TimeUtils.toLocalDate(Mockito.any(Date.class))
                    )
                    .thenReturn(now);

            mockedStatic.when(
                            () -> TimeUtils.toLocalDate(java.sql.Date.valueOf(validDateOfBirth))
                    )
                    .thenReturn(java.sql.Date.valueOf(validDateOfBirth).toLocalDate());

            //when
            var isValid = adultValidator.isValid(java.sql.Date.valueOf(validDateOfBirth), constraintValidatorContext);

            //then
            Assertions.assertTrue(isValid);
            Mockito.verify(constraintValidatorContext, Mockito.never()).buildConstraintViolationWithTemplate(Mockito.anyString());
        }
    }

    @Test
    void IsValid_invalid() {
        //given
        var now = TimeUtils.toLocalDate(new Date());
        var startDateUnLimit = now.minus(Period.ofYears(yearOld));
        var inValidDateOfBirth = now.minusYears(yearOld).plusDays(1);
        var expectedMessage = String.format(
                "DataOfBirth: '%s' is incorrect. Data should be equal to or less than: '%s' ",
                inValidDateOfBirth, startDateUnLimit
        );

        ReflectionTestUtils.setField(adultValidator, "yearOld", yearOld);

        try (var mockedStatic = Mockito.mockStatic(TimeUtils.class)) {
            mockedStatic.when(
                            () -> TimeUtils.toLocalDate(Mockito.any(Date.class))
                    )
                    .thenReturn(now);

            mockedStatic.when(
                            () -> TimeUtils.toLocalDate(java.sql.Date.valueOf(inValidDateOfBirth))
                    )
                    .thenReturn(java.sql.Date.valueOf(inValidDateOfBirth).toLocalDate());

            Mockito.doReturn(constraintViolationBuilder)
                    .when(constraintValidatorContext)
                    .buildConstraintViolationWithTemplate(Mockito.anyString());

            Mockito.doReturn(leafNodeBuilderCustomizableContext)
                    .when(constraintViolationBuilder)
                    .addBeanNode();

            Mockito.doReturn(constraintValidatorContext)
                    .when(leafNodeBuilderCustomizableContext)
                    .addConstraintViolation();

            //when
            var isValid = adultValidator.isValid(java.sql.Date.valueOf(inValidDateOfBirth), constraintValidatorContext);

            //then
            Assertions.assertFalse(isValid);
            Mockito.verify(constraintValidatorContext).buildConstraintViolationWithTemplate(messageCaptor.capture());
            Assertions.assertEquals(expectedMessage, messageCaptor.getValue());
            Mockito.verify(constraintValidatorContext.buildConstraintViolationWithTemplate(Mockito.anyString())).addBeanNode();
        }
    }

}