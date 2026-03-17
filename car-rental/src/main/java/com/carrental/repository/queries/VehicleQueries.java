package com.carrental.repository.queries;

public final class VehicleQueries {

    private VehicleQueries() {}

    public static final String FIND_AVAILABLE_VEHICLES = """
            SELECT v FROM Vehicle v
            WHERE v.category = :category
            AND v.status = 'AVAILABLE'
            AND v.id NOT IN (
                SELECT r.vehicle.id FROM Reservation r
                WHERE r.status = 'ACTIVE'
                AND r.startDate < :endDate
                AND r.endDate > :startDate
            )
            """;
}