package com.carrental.pricing;

import com.carrental.dto.response.BreakdownLineDto;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class PickUpTruckPricingStrategy extends BasePricingStrategy {

    private static final double DAILY_RATE                = 30.0;
    private static final double SURCHARGE_PERCENTAGE      = 0.10;
    private static final int    SURCHARGE_YEARS_THRESHOLD = 3;

    @Override
    protected double getDailyRate(PricingRequest request) {
        return DAILY_RATE;
    }

    @Override
    protected double getAdditionalCharges(double baseAmount, PricingRequest request) {
        return hasSurcharge(request.yearsLicensed()) ? baseAmount * SURCHARGE_PERCENTAGE : 0.0;
    }

    @Override
    public List<BreakdownLineDto> calculateBreakdown(PricingRequest request) {
        int days = request.noOfDays();
        double base = days * DAILY_RATE;
        boolean surcharge = hasSurcharge(request.yearsLicensed());
        int surchargePercent = (int)(SURCHARGE_PERCENTAGE * 100);

        List<BreakdownLineDto> lines = new ArrayList<>(3);

        lines.add(new BreakdownLineDto("Daily rate", DAILY_RATE, "BASE"));
        lines.add(new BreakdownLineDto(days + " days × $" + (int) DAILY_RATE + ".00", base, "BASE"));

        if (surcharge) {
            lines.add(new BreakdownLineDto(
                    "Inexperienced driver surcharge (" + surchargePercent + "%)",
                    base * SURCHARGE_PERCENTAGE, "SURCHARGE"));
        }

        return lines;
    }

    @Override
    public String getPricingSummary(PricingRequest request) {
        int surchargePercent = (int)(SURCHARGE_PERCENTAGE * 100);
        return hasSurcharge(request.yearsLicensed())
                ? "$" + (int) DAILY_RATE + "/day + "
                + surchargePercent + "% surcharge (licensed under "
                + SURCHARGE_YEARS_THRESHOLD + " yrs)"
                : "$" + (int) DAILY_RATE + "/day — no surcharge";
    }

    public boolean hasSurcharge(int yearsLicensed) {
        return yearsLicensed < SURCHARGE_YEARS_THRESHOLD;
    }

    public int getSurchargePercentage() {
        return (int)(SURCHARGE_PERCENTAGE * 100);
    }
}