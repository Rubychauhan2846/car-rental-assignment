package com.carrental.repository.queries;

public final class ReservationQueries {

    private ReservationQueries() {}

    public static final String EXISTS_OVERLAPPING_RESERVATION = """
            SELECT COUNT(r) > 0 FROM Reservation r
            WHERE r.customerEmail = :email
            AND r.status = 'ACTIVE'
            AND r.startDate < :endDate
            AND r.endDate > :startDate
            """;
}