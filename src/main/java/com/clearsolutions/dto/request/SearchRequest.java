package com.clearsolutions.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record SearchRequest(

        @NotNull(message = "From can not be null")
        Date from,

        @NotNull(message = "To can not be null")
        Date to

) {
}
