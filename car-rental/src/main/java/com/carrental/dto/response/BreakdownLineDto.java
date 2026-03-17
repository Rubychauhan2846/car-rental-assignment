package com.carrental.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BreakdownLineDto(
        String label,
        double amount,
        String type
) {}