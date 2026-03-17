package com.carrental.service.util;

import com.carrental.dto.request.ReservationRequestDto;
import com.carrental.dto.response.BreakdownLineDto;
import com.carrental.dto.response.ReservationResponseDto;
import com.carrental.model.entity.Reservation;
import com.carrental.model.entity.Vehicle;
import com.carrental.pricing.PricingRequest;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public final class ReservationServiceUtil {
    private ReservationServiceUtil() {}

    public static void validateDates(LocalDate startDate, LocalDate endDate) {
        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
    }

    public static int calculateNumberOfDays(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static void validateCategorySpecificFields(ReservationRequestDto request) {
        switch (request.getVehicleCategory()) {
            case SUV -> {
                if (request.getDailyMileage() == null) {
                    throw new IllegalArgumentException(
                            "Estimated daily mileage is required for SUV");
                }
            }
            case PICKUP_TRUCK -> {
                if (request.getYearsLicensed() == null) {
                    throw new IllegalArgumentException(
                            "Years licensed is required for Pickup Truck");
                }
            }
            default -> { /* no extra fields needed */ }
        }
    }

    public static PricingRequest buildPricingRequest(
            ReservationRequestDto request,
            int numberOfDays) {

        double mileage = request.getDailyMileage() != null
                ? request.getDailyMileage() : 0.0;
        int years = request.getYearsLicensed() != null
                ? request.getYearsLicensed() : 0;

        return new PricingRequest(numberOfDays, mileage, years);
    }

    public static PricingRequest buildPricingRequest(
            int numberOfDays,
            double estimatedDailyMileage,
            int yearsLicensed) {
        return new PricingRequest(numberOfDays, estimatedDailyMileage, yearsLicensed);
    }

    public static ReservationResponseDto mapToResponseDto(
            Reservation reservation,
            int numberOfDays) {

        Vehicle vehicle = reservation.getVehicle();

        return ReservationResponseDto.builder()
                .reservationId(reservation.getReservationId())
                .customerName(reservation.getCustomerName())
                .customerEmail(reservation.getCustomerEmail())
                .vehicleMake(vehicle.getMake())
                .vehicleModel(vehicle.getModel())
                .vehicleYear(Integer.parseInt(vehicle.getYear()))
                .licensePlate(vehicle.getLicensePlate())
                .vehicleCategory(vehicle.getCategory())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .numberOfDays(numberOfDays)
                .dailyMileage(reservation.getDailyMileage())
                .yearsLicensed(reservation.getYearsLicensed())
                .totalAmount(reservation.getBookingAmount())
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
                .build();
    }

    public static ReservationResponseDto mapToResponseDto(
            Reservation reservation,
            int numberOfDays,
            List<BreakdownLineDto> breakdown) {

        Vehicle vehicle = reservation.getVehicle();

        return ReservationResponseDto.builder()
                .reservationId(reservation.getReservationId())
                .customerName(reservation.getCustomerName())
                .customerEmail(reservation.getCustomerEmail())
                .vehicleMake(vehicle.getMake())
                .vehicleModel(vehicle.getModel())
                .vehicleYear(Integer.parseInt(vehicle.getYear()))
                .licensePlate(vehicle.getLicensePlate())
                .vehicleCategory(vehicle.getCategory())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .numberOfDays(numberOfDays)
                .dailyMileage(reservation.getDailyMileage())
                .yearsLicensed(reservation.getYearsLicensed())
                .totalAmount(reservation.getBookingAmount())
                .breakdown(breakdown)
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}