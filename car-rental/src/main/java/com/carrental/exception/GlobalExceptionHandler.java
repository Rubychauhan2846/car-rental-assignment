package com.carrental.exception;

import com.carrental.dto.response.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ─── 1. No Vehicle Available ─────────────────────────────────────────────

    @ExceptionHandler(NoVehicleAvailableException.class)
    public ResponseEntity<ErrorResponseDto> handleNoVehicleAvailable(
            NoVehicleAvailableException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.CONFLICT,
                "No Vehicle Available",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    // ─── 2. Reservation Not Found ────────────────────────────────────────────

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleReservationNotFound(
            ReservationNotFoundException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Reservation Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    // ─── 3. Duplicate Reservation ────────────────────────────────────────────

    @ExceptionHandler(DuplicateReservationException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateReservation(
            DuplicateReservationException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.CONFLICT,
                "Duplicate Reservation",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    // ─── 4. Concurrent Booking Conflict ──────────────────────────────────────

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponseDto> handleOptimisticLocking(
            ObjectOptimisticLockingFailureException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.CONFLICT,
                "Booking Conflict",
                "This vehicle was just booked by someone else. Please try again.",
                request.getRequestURI(),
                null
        );
    }

    // ─── 5. Validation Errors (@Valid failures) ───────────────────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<String> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                "One or more fields are invalid",
                request.getRequestURI(),
                validationErrors
        );
    }

    // ─── 6. Illegal State (modify cancelled reservation etc) ─────────────────

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalState(
            IllegalStateException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid Operation",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    // ─── 7. Illegal Argument (bad category, missing mileage etc) ─────────────

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid Request",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    // ─── 8. Catch All ────────────────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred. Please try again later.",
                request.getRequestURI(),
                null
        );
    }

    // ─── Private Builder ─────────────────────────────────────────────────────

    private ResponseEntity<ErrorResponseDto> buildResponse(
            HttpStatus status,
            String error,
            String message,
            String path,
            List<String> validationErrors) {

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .status(status.value())
                .error(error)
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .validationErrors(validationErrors)
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }
}