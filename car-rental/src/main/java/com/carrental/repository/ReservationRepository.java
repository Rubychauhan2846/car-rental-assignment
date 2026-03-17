package com.carrental.repository;

import com.carrental.model.entity.Reservation;
import com.carrental.model.entity.Vehicle;
import com.carrental.model.enums.VehicleCategoryEnum;
import com.carrental.repository.queries.ReservationQueries;
import com.carrental.repository.queries.VehicleQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query(ReservationQueries.EXISTS_OVERLAPPING_RESERVATION)
    boolean existsOverlappingReservation(
            @Param("email") String email,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    Optional<Reservation> findByReservationId(Long reservationId);
}
