package com.clearsolutions.dto.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class SearchRequestTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void testSearchRequest() {
        //given
        var fromDate = new Date();
        var toDate = new Date(fromDate.getTime() + 86400000);
        var searchRequest = new SearchRequest(fromDate, toDate);

        //when
        var violations = validator.validate(searchRequest);

        //then
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void testSearchRequest_WithNullFrom() {
        //given
        var to = new Date();
        var searchRequest = new SearchRequest(null, to);

        //when
        var violations = validator.validate(searchRequest);

        //then
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        Assertions.assertEquals("From can not be null", violation.getMessage());
    }

    @Test
    void testSearchRequest_WithNullTo() {
        //given
        var from = new Date();
        var searchRequest = new SearchRequest(from, null);

        //when
        var violations = validator.validate(searchRequest);

        //then
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        Assertions.assertEquals("To can not be null", violation.getMessage());
    }
}