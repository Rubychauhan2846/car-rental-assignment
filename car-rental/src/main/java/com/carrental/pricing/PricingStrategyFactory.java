package com.carrental.pricing;

import com.carrental.model.enums.VehicleCategoryEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.EnumMap;
import java.util.Map;

@Component
public class PricingStrategyFactory {

    private static final Logger log = LoggerFactory.getLogger(PricingStrategyFactory.class);
    private final Map<VehicleCategoryEnum, PricingStrategy> strategyMap;


    public PricingStrategyFactory(SedanPricingStrategy sedanPricingStrategy,SuvPricingStrategy suvPricingStrategy,VanPricingStrategy vanPricingStrategy,PickUpTruckPricingStrategy pickUpTruckPricingStrategy) {

    strategyMap = new EnumMap<>(VehicleCategoryEnum.class);
    strategyMap.put(VehicleCategoryEnum.PICKUP_TRUCK,pickUpTruckPricingStrategy);
    strategyMap.put(VehicleCategoryEnum.SEDAN,sedanPricingStrategy);
    strategyMap.put(VehicleCategoryEnum.VAN,vanPricingStrategy);
    strategyMap.put(VehicleCategoryEnum.SUV,suvPricingStrategy);
    }


    public PricingStrategy getStrategy(VehicleCategoryEnum vehicleCategory) {
        PricingStrategy strategy = strategyMap.get(vehicleCategory);

        if (strategy == null) {
            log.error("No pricing strategy found for category: {}", vehicleCategory);
            throw new IllegalArgumentException(
                    "No Price Strategy found for vehicle category: " + vehicleCategory
            );
        }

        return strategy;
    }
}
