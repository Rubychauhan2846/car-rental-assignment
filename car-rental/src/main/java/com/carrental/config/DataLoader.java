package com.carrental.config;

import com.carrental.model.entity.Vehicle;
import com.carrental.model.enums.VehicleCategoryEnum;
import com.carrental.model.enums.VehicleStatusEnum;
import com.carrental.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;

    public DataLoader(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void run(String... args) {

        if (vehicleRepository.count() > 0) {
            return;
        }

        List<Vehicle> vehicles = List.of(

                // ─── SEDANs ───────────────────────────────────────────
                Vehicle.builder()
                        .make("Toyota")
                        .model("Camry")
                        .year("2022")
                        .licensePlate("SED-001")
                        .category(VehicleCategoryEnum.SEDAN)
                        .status(VehicleStatusEnum.AVAILABLE)
                        .build(),

                Vehicle.builder()
                        .make("Honda")
                        .model("Accord")
                        .year("2023")
                        .licensePlate("SED-002")
                        .category(VehicleCategoryEnum.SEDAN)
                        .status(VehicleStatusEnum.AVAILABLE)
                        .build(),

                Vehicle.builder()
                        .make("Nissan")
                        .model("Altima")
                        .year("2021")
                        .licensePlate("SED-003")
                        .category(VehicleCategoryEnum.SEDAN)
                        .status(VehicleStatusEnum.AVAILABLE)
                        .build(),

                // ─── VANs ─────────────────────────────────────────────
                Vehicle.builder()
                        .make("Ford")
                        .model("Transit")
                        .year("2022")
                        .licensePlate("VAN-001")
                        .category(VehicleCategoryEnum.VAN)
                        .status(VehicleStatusEnum.AVAILABLE)
                        .build(),

                Vehicle.builder()
                        .make("Mercedes")
                        .model("Sprinter")
                        .year("2023")
                        .licensePlate("VAN-002")
                        .category(VehicleCategoryEnum.VAN)
                        .status(VehicleStatusEnum.AVAILABLE)
                        .build(),

                // ─── SUVs ─────────────────────────────────────────────
                Vehicle.builder()
                        .make("Toyota")
                        .model("RAV4")
                        .year("2023")
                        .licensePlate("SUV-001")
                        .category(VehicleCategoryEnum.SUV)
                        .status(VehicleStatusEnum.AVAILABLE)
                        .build(),

                Vehicle.builder()
                        .make("Honda")
                        .model("CR-V")
                        .year("2022")
                        .licensePlate("SUV-002")
                        .category(VehicleCategoryEnum.SUV)
                        .status(VehicleStatusEnum.AVAILABLE)
                        .build(),

                // ─── PICKUP TRUCKs ────────────────────────────────────
                Vehicle.builder()
                        .make("Ford")
                        .model("F-150")
                        .year("2023")
                        .licensePlate("PCK-001")
                        .category(VehicleCategoryEnum.PICKUP_TRUCK)
                        .status(VehicleStatusEnum.AVAILABLE)
                        .build(),

                Vehicle.builder()
                        .make("Chevrolet")
                        .model("Silverado")
                        .year("2022")
                        .licensePlate("PCK-002")
                        .category(VehicleCategoryEnum.PICKUP_TRUCK)
                        .status(VehicleStatusEnum.AVAILABLE)
                        .build()
        );

        vehicleRepository.saveAll(vehicles);
        System.out.println("✅ DataLoader — seeded " + vehicles.size() + " vehicles");
    }
}