package com.carrental.pricing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public abstract class BasePricingStrategy implements PricingStrategy {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public final double calculateTotal(PricingRequest request) {
        double base       = getBaseAmount(request);
        double additional = getAdditionalCharges(base, request);
        double total      = base + additional;

        if (log.isDebugEnabled()) {
            log.debug("{} total: days={}, base={}, additional={}, total={}",
                    getClass().getSimpleName(),
                    request.noOfDays(), base, additional, total);
        }

        return total;
    }
    protected abstract double getDailyRate(PricingRequest request);

    protected double getAdditionalCharges(
            double baseAmount, PricingRequest request) {
        return 0.0;
    }

    protected double getBaseAmount(PricingRequest request) {
        return request.noOfDays() * getDailyRate(request);
    }
}