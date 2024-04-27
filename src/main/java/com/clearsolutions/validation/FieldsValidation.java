package com.clearsolutions.validation;

import com.clearsolutions.entity.User;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FieldsValidation {

    public void validFields(Map<String, Object> attributes) {
        var empty = attributes.keySet().stream()
                .filter(key -> !getAllFieldNames().contains(key))
                .collect(Collectors.toSet())
                .isEmpty();
        if (!empty) {
            throw new ValidationException("Invalid field(s)");
        }
    }

    private Set<String> getAllFieldNames() {
        var fieldNames = new HashSet<String>();
        ReflectionUtils.doWithFields(User.class, field -> fieldNames.add(field.getName()));
        return fieldNames;
    }
}
