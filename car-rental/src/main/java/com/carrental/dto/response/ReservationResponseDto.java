package com.carrental.dto.response;

import com.carrental.model.enums.ReservationStatusEnum;
import com.carrental.model.enums.VehicleCategoryEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationResponseDto {

    private Long reservationId;
    private String customerName;
    private String customerEmail;
    private String vehicleMake;
    private String vehicleModel;
    private int vehicleYear;
    private String licensePlate;
    private VehicleCategoryEnum vehicleCategory;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfDays;
    private Double dailyMileage;
    private Integer yearsLicensed;
    private List<BreakdownLineDto> breakdown;
    private Double totalAmount;
    private ReservationStatusEnum status;
    private LocalDateTime createdAt;

}
