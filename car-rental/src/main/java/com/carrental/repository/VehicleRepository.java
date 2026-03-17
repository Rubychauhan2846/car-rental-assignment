package com.carrental.repository;

import com.carrental.model.entity.Vehicle;
import com.carrental.model.enums.VehicleCategoryEnum;
import com.carrental.repository.queries.VehicleQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    @Query(VehicleQueries.FIND_AVAILABLE_VEHICLES)
    List<Vehicle> findAvailableVehicles(@Param("category") VehicleCategoryEnum category, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate );
}
