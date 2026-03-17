package com.carrental.model.entity;
import com.carrental.model.enums.VehicleCategoryEnum;
import com.carrental.model.enums.VehicleStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(name = "manufacture_year",nullable = false)
    private String year;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleCategoryEnum category;

    @Version
    private Long version;

    public void reserve() {
        this.status = VehicleStatusEnum.RESERVED;
    }

    public void release() {
        this.status = VehicleStatusEnum.AVAILABLE;
    }
    public boolean isAvailable() {
        return this.status == VehicleStatusEnum.AVAILABLE;
    }

    public boolean isReserved() {
        return this.status == VehicleStatusEnum.RESERVED;
    }
}
