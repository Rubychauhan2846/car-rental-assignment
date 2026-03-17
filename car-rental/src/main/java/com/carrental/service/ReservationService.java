package com.carrental.service;

import com.carrental.dto.request.ReservationRequestDto;
import com.carrental.dto.response.ReservationResponseDto;
import com.carrental.dto.response.VehicleOptionDto;

import java.util.List;

public interface ReservationService {

    ReservationResponseDto reserveCar(ReservationRequestDto request);

    ReservationResponseDto modifyReservation(
            Long reservationId,
            ReservationRequestDto request);

    void cancelReservation(Long reservationId);

    List<VehicleOptionDto> getOptions(
            int numberOfDays,
            double estimatedDailyMileage,
            int yearsLicensed);

    ReservationResponseDto getReservation(Long reservationId);
}