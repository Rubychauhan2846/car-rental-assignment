package com.carrental.pricing;

import com.carrental.dto.response.BreakdownLineDto;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class VanPricingStrategy extends BasePricingStrategy {

    private static final double DAILY_RATE = 22.0;
    private static final double CLEANING_FEE_PERCENTAGE = 0.10;

    @Override
    protected double getDailyRate(PricingRequest request) {
        return DAILY_RATE;
    }

    @Override
    protected double getAdditionalCharges(double baseAmount, PricingRequest request) {
        return baseAmount * CLEANING_FEE_PERCENTAGE;
    }

    @Override
    public List<BreakdownLineDto> calculateBreakdown(PricingRequest request) {
        int days = request.noOfDays();
        double base = days * DAILY_RATE;
        double cleaningFee = base * CLEANING_FEE_PERCENTAGE;
        int feePercent = (int)(CLEANING_FEE_PERCENTAGE * 100);

        return List.of(
                new BreakdownLineDto("Daily rate", DAILY_RATE, "BASE"),
                new BreakdownLineDto(
                        days + " days × $" + (int) DAILY_RATE + ".00", base, "BASE"),
                new BreakdownLineDto(
                        "Cleaning fee (" + feePercent + "% of base)",
                        cleaningFee, "EXTRA")
        );
    }

    @Override
    public String getPricingSummary(PricingRequest request) {
        int feePercent = (int)(CLEANING_FEE_PERCENTAGE * 100);
        return "$" + (int) DAILY_RATE + "/day + "
                + feePercent + "% cleaning fee included";
    }
}