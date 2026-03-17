package com.carrental.pricing;

public record PricingRequest(
        int noOfDays,
        double dailyMileage,
        int yearsLicensed
) {
    public PricingRequest {
        if (noOfDays <= 0) throw new IllegalArgumentException(
                "Number of days must be positive"
        );
        if (dailyMileage < 0) throw new IllegalArgumentException(
                "Daily mileage cannot be negative"
        );
        if (yearsLicensed < 0) throw new IllegalArgumentException(
                "Years licensed cannot be negative"
        );
    }
}