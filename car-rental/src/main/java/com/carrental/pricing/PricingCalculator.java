package com.carrental.pricing;

import com.carrental.dto.response.BreakdownLineDto;

import java.util.List;
public interface PricingCalculator {
    double calculateTotal(PricingRequest request);
    List<BreakdownLineDto> calculateBreakdown(PricingRequest request);
}