package com.carrental.dto.response;

import com.carrental.model.enums.VehicleCategoryEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleOptionDto {
    private VehicleCategoryEnum vehicleCategory;
    private int noOfDays;
    private double bookingAmount;
    private String pricingSummary;
    private boolean surchargeApplied;
    private int surchargePercentage;
}