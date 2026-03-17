package com.carrental.exception;

public class DuplicateReservationException extends RuntimeException {

    public DuplicateReservationException(String message) {
        super(message);
    }
}