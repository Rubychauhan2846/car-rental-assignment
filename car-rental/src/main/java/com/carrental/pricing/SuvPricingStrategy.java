package com.carrental.pricing;

import com.carrental.dto.response.BreakdownLineDto;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SuvPricingStrategy extends BasePricingStrategy {

    private static final double DAILY_RATE = 15.0;
    private static final double MILEAGE_RATE = 0.50;

    @Override
    protected double getDailyRate(PricingRequest request) {
        return DAILY_RATE;
    }

    @Override
    protected double getAdditionalCharges(double baseAmount, PricingRequest request) {
        return MILEAGE_RATE * request.dailyMileage() * request.noOfDays();
    }

    @Override
    public List<BreakdownLineDto> calculateBreakdown(PricingRequest request) {
        int days = request.noOfDays();
        double miles = request.dailyMileage();
        double base = days * DAILY_RATE;
        double mileageCharge = MILEAGE_RATE * miles * days;

        return List.of(
                new BreakdownLineDto("Daily rate", DAILY_RATE, "BASE"),
                new BreakdownLineDto(
                        days + " days × $" + (int) DAILY_RATE + ".00", base, "BASE"),
                new BreakdownLineDto(
                        "Mileage: $" + MILEAGE_RATE + " × "
                                + (int) miles + " mi × " + days + " days",
                        mileageCharge, "EXTRA")
        );
    }

    @Override
    public String getPricingSummary(PricingRequest request) {
        double miles = request.dailyMileage();
        return miles > 0
                ? "$" + (int) DAILY_RATE + "/day + $"
                + MILEAGE_RATE + " × " + (int) miles + " mi/day"
                : "$" + (int) DAILY_RATE + "/day + $"
                + MILEAGE_RATE + " per mile per day";
    }
}