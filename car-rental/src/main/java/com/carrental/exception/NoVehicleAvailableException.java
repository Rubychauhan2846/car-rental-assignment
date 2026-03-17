package com.carrental.exception;

import com.carrental.model.enums.VehicleCategoryEnum;

public class NoVehicleAvailableException extends RuntimeException {

    public NoVehicleAvailableException(VehicleCategoryEnum category) {
        super("No vehicle available for category: " + category.name());
    }
}