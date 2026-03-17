package com.carrental.dto.request;

import com.carrental.model.enums.VehicleCategoryEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDto {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Customer email is required")
    @Email(message="Provide valid email")
    private String customerEmail;

    @NotNull(message = "Vehicle category is required")
    private VehicleCategoryEnum vehicleCategory;

    @NotNull(message = "Start Date is required")
    @FutureOrPresent(message = "Start date cannot be in the past")
    private LocalDate startDate;

    @Future(message = "End date must be in the future")
    @NotNull(message = "End date is required")
    private LocalDate endDate;

    private Double dailyMileage;
    private Integer yearsLicensed;
}
