package com.carrental.pricing;

import com.carrental.model.enums.VehicleCategoryEnum;

public interface IPricingStrategyFactory {
    PricingStrategy getStrategy(VehicleCategoryEnum category);
}