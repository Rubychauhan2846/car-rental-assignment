package com.carrental.pricing;

import com.carrental.dto.response.BreakdownLineDto;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class SedanPricingStrategy extends BasePricingStrategy {

    private static final double RATE_UNDER_10_DAYS = 20.0;
    private static final double RATE_10_DAYS_OR_MORE = 15.0;
    private static final int DAY_THRESHOLD = 10;

    @Override
    protected double getDailyRate(PricingRequest request) {
        return request.noOfDays() < DAY_THRESHOLD ? RATE_UNDER_10_DAYS : RATE_10_DAYS_OR_MORE;
    }

    @Override
    public List<BreakdownLineDto> calculateBreakdown(PricingRequest request) {
        int days = request.noOfDays();
        boolean isShortRental = days < DAY_THRESHOLD;
        double rate = isShortRental ? RATE_UNDER_10_DAYS : RATE_10_DAYS_OR_MORE;
        double base = days * rate;
        String rateLabel = isShortRental ? "under 10 days" : "10+ days";

        return List.of(
                new BreakdownLineDto(
                        "Daily rate (" + rateLabel + ")", rate, "BASE"),
                new BreakdownLineDto(
                        days + " days × $" + (int) rate + ".00", base, "BASE")
        );
    }

    @Override
    public String getPricingSummary(PricingRequest request) {
        return request.noOfDays() < DAY_THRESHOLD
                ? "$" + (int) RATE_UNDER_10_DAYS + "/day — short rental rate"
                : "$" + (int) RATE_10_DAYS_OR_MORE + "/day — long rental rate (10+ days)";
    }
}