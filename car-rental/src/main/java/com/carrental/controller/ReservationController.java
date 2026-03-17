package com.carrental.controller;

import com.carrental.dto.request.ReservationRequestDto;
import com.carrental.dto.response.ReservationResponseDto;
import com.carrental.dto.response.VehicleOptionDto;
import com.carrental.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing car reservations.
 * Exposes endpoints under {/api/reservations} to handle the full
 * reservation lifecycle: creating, modifying, cancelling, and retrieving
 * reservations, as well as fetching available vehicle options.
 */
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * @param {reservationService} the service handling reservation business logic
     */
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Creates a new car reservation.
     *
     * @param request the reservation details provided by the client; must be valid
     * @return a ResponseEntity containing the created ReservationResponseDto
     * with HTTP status {@code 201 Created}
     */
    @PostMapping
    public ResponseEntity<ReservationResponseDto> reserveCar(
            @Valid @RequestBody ReservationRequestDto request) {
        ReservationResponseDto response = reservationService.reserveCar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * Modifies an existing reservation identified by its ID.
     *
     * @param id the unique identifier of the reservation to update
     * @param request the updated reservation details; must be valid
     * @return a ResponseEntity containing the updated ReservationResponseDto
     * with HTTP status {@code 200 OK}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> modifyReservation(
            @PathVariable Long id,
            @Valid @RequestBody ReservationRequestDto request) {
        ReservationResponseDto response =
                reservationService.modifyReservation(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Cancels an existing reservation identified by its ID.
     *
     * @param id the unique identifier of the reservation to cancel
     * @return a ResponseEntity with HTTP status {@code 204 No Content}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves available vehicle options based on rental criteria.
     *
     * <p>Results are filtered and ranked according to the provided rental duration,
     * expected daily mileage, and the driver's years of experience.
     *
     * @param numberOfDays the total number of rental days
     * @param estimatedDailyMileage the estimated miles driven per day (defaults to {@code 0})
     * @param yearsLicensed the number of years the driver has held a licence (defaults to {@code 0})
     * @return a ResponseEntity containing a list of VehicleOptionDto
     * with HTTP status {@code 200 OK}
     */
    @GetMapping("/options")
    public ResponseEntity<List<VehicleOptionDto>> getOptions(
            @RequestParam int numberOfDays,
            @RequestParam(defaultValue = "0") double estimatedDailyMileage,
            @RequestParam(defaultValue = "0") int yearsLicensed) {
        List<VehicleOptionDto> options = reservationService.getOptions(
                numberOfDays, estimatedDailyMileage, yearsLicensed);
        return ResponseEntity.ok(options);
    }

    /**
     * Retrieves the details of a specific reservation by its ID.
     *
     * @param id the unique identifier of the reservation to retrieve
     * @return a ResponseEntity containing the ReservationResponseDto
     * with HTTP status {@code 200 OK}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getReservation(
            @PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservation(id));
    }
}