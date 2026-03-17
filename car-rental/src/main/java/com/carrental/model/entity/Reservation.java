package com.carrental.model.entity;

import com.carrental.model.enums.ReservationStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Table(name = "reservations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_email",nullable = false)
    private String customerEmail;

    @Column(name = "start_date",nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;

    @Column(name = "daily_mileage")
    private Double dailyMileage;

    @Column(name = "booking_amount",nullable = false)
    private Double bookingAmount;

    @Column(name = "year_licensed")
    private Integer yearsLicensed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatusEnum status;


    @Column(name = "created_at" ,nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "reservation_id", unique = true, nullable = false, updatable = false)
    private Long reservationId;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.reservationId = generateReservationId();
    }

    private Long generateReservationId() {
        return (long)(Math.random() * 90_000_000) + 10_000_000;
    }

    public void cancel() {
        this.status = ReservationStatusEnum.CANCELLED;
    }

    public boolean isActive() {
        return this.status == ReservationStatusEnum.ACTIVE;
    }

    public boolean isCancelled() {
        return this.status == ReservationStatusEnum.CANCELLED;
    }

    public void updateDates(
            LocalDate newStartDate,
            LocalDate newEndDate,
            Double newDailyMileage,
            Integer newYearsLicensed,
            double newBookingAmount,
            Vehicle newVehicle,
            String newCustomerName) {
        this.startDate = newStartDate;
        this.endDate = newEndDate;
        this.dailyMileage = newDailyMileage;
        this.yearsLicensed = newYearsLicensed;
        this.bookingAmount = newBookingAmount;
        this.vehicle = newVehicle;
        this.customerName  = newCustomerName;
    }

}
