package com.carrental.service;

import com.carrental.dto.request.ReservationRequestDto;
import com.carrental.dto.response.BreakdownLineDto;
import com.carrental.dto.response.ReservationResponseDto;
import com.carrental.dto.response.VehicleOptionDto;
import com.carrental.exception.DuplicateReservationException;
import com.carrental.exception.NoVehicleAvailableException;
import com.carrental.exception.ReservationNotFoundException;
import com.carrental.model.entity.Reservation;
import com.carrental.model.entity.Vehicle;
import com.carrental.model.enums.ReservationStatusEnum;
import com.carrental.model.enums.VehicleCategoryEnum;
import com.carrental.pricing.PickUpTruckPricingStrategy;
import com.carrental.pricing.PricingRequest;
import com.carrental.pricing.PricingStrategy;
import com.carrental.pricing.PricingStrategyFactory;
import com.carrental.repository.ReservationRepository;
import com.carrental.repository.VehicleRepository;
import com.carrental.service.util.ReservationServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Logger log =
            LoggerFactory.getLogger(ReservationServiceImpl.class);

    private final ReservationRepository reservationRepository;
    private final VehicleRepository vehicleRepository;
    private final PricingStrategyFactory pricingStrategyFactory;

    public ReservationServiceImpl(
            ReservationRepository reservationRepository,
            VehicleRepository vehicleRepository,
            PricingStrategyFactory pricingStrategyFactory) {
        this.reservationRepository  = reservationRepository;
        this.vehicleRepository      = vehicleRepository;
        this.pricingStrategyFactory = pricingStrategyFactory;
    }

    /** Validates input, checks for duplicates, assigns an available vehicle, computes pricing, and persists a new reservation. */
    @Override
    @Transactional
    public ReservationResponseDto reserveCar(ReservationRequestDto request) {

        log.info("Reserving car: category={}, customer={}, dates={} to {}",
                request.getVehicleCategory(), request.getCustomerEmail(),
                request.getStartDate(), request.getEndDate());

        ReservationServiceUtil.validateDates(
                request.getStartDate(), request.getEndDate());
        ReservationServiceUtil.validateCategorySpecificFields(request);

        boolean alreadyBooked = reservationRepository.existsOverlappingReservation(
                request.getCustomerEmail(),
                request.getStartDate(),
                request.getEndDate()
        );
        if (alreadyBooked) {
            log.warn("Duplicate booking: email={}", request.getCustomerEmail());
            throw new DuplicateReservationException(
                    "You already have a booking overlapping these dates");
        }

        List<Vehicle> availableVehicles = vehicleRepository.findAvailableVehicles(
                request.getVehicleCategory(),
                request.getStartDate(),
                request.getEndDate()
        );
        if (availableVehicles.isEmpty()) {
            log.warn("No vehicles available: category={}", request.getVehicleCategory());
            throw new NoVehicleAvailableException(request.getVehicleCategory());
        }

        Vehicle vehicle = availableVehicles.get(0);
        int numberOfDays = ReservationServiceUtil.calculateNumberOfDays(request.getStartDate(), request.getEndDate());
        PricingStrategy strategy = pricingStrategyFactory.getStrategy(request.getVehicleCategory());
        PricingRequest pricingRequest = ReservationServiceUtil.buildPricingRequest(request, numberOfDays);

        double totalAmount = strategy.calculateTotal(pricingRequest);
        List<BreakdownLineDto> breakdown = strategy.calculateBreakdown(pricingRequest);

        Reservation reservation = Reservation.builder()
                .vehicle(vehicle)
                .customerName(request.getCustomerName())
                .customerEmail(request.getCustomerEmail())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .dailyMileage(request.getDailyMileage())
                .yearsLicensed(request.getYearsLicensed())
                .bookingAmount(totalAmount)
                .status(ReservationStatusEnum.ACTIVE)
                .build();

        reservationRepository.save(reservation);
        vehicle.reserve();
        vehicleRepository.save(vehicle);

        log.info("Reservation created: id={}, amount={}",
                reservation.getId(), totalAmount);

        return ReservationServiceUtil.mapToResponseDto(
                reservation, numberOfDays, breakdown);
    }

    /** Updates dates, mileage, and pricing of an active reservation. */
    @Override
    @Transactional
    public ReservationResponseDto modifyReservation(
            Long reservationId,
            ReservationRequestDto request) {

        log.info("Modifying reservation: id={}", reservationId);

        Reservation reservation = reservationRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));

        if (!reservation.isActive()) {
            throw new IllegalStateException(
                    "Only active reservations can be modified");
        }

        ReservationServiceUtil.validateDates(
                request.getStartDate(), request.getEndDate());
        ReservationServiceUtil.validateCategorySpecificFields(request);

        Vehicle vehicle = reservation.getVehicle();

        int numberOfDays = ReservationServiceUtil.calculateNumberOfDays(request.getStartDate(), request.getEndDate());
        PricingStrategy strategy = pricingStrategyFactory.getStrategy(vehicle.getCategory());
        PricingRequest pricingRequest = ReservationServiceUtil.buildPricingRequest(request, numberOfDays);

        double totalAmount = strategy.calculateTotal(pricingRequest);
        List<BreakdownLineDto> breakdown = strategy.calculateBreakdown(pricingRequest);

        reservation.updateDates(
                request.getStartDate(),
                request.getEndDate(),
                request.getDailyMileage(),
                request.getYearsLicensed(),
                totalAmount,
                vehicle,
                request.getCustomerName()
        );
        reservationRepository.save(reservation);

        log.info("Reservation modified: id={}, newAmount={}",
                reservationId, totalAmount);

        return ReservationServiceUtil.mapToResponseDto(
                reservation, numberOfDays, breakdown);
    }

    /** Marks a reservation as cancelled and releases the associated vehicle back to the available pool. */
    @Override
    @Transactional
    public void cancelReservation(Long reservationId) {

        log.info("Cancelling reservation: id={}", reservationId);

        Reservation reservation = reservationRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));

        if (reservation.isCancelled()) {
            throw new IllegalStateException("Reservation is already cancelled");
        }

        reservation.cancel();
        reservationRepository.save(reservation);

        Vehicle vehicle = reservation.getVehicle();
        vehicle.release();
        vehicleRepository.save(vehicle);

        log.info("Reservation cancelled: id={}, vehicle={}",
                reservationId, vehicle.getLicensePlate());
    }

    /** Returns all vehicle categories with computed pricing, sorted by total booking amount ascending. */
    @Override
    public List<VehicleOptionDto> getOptions(
            int numberOfDays,
            double estimatedDailyMileage,
            int yearsLicensed) {

        if (log.isDebugEnabled()) {
            log.debug("Getting options: days={}, mileage={}, years={}",
                    numberOfDays, estimatedDailyMileage, yearsLicensed);
        }

        PricingRequest pricingRequest = ReservationServiceUtil
                .buildPricingRequest(numberOfDays, estimatedDailyMileage, yearsLicensed);

        return Arrays.stream(VehicleCategoryEnum.values())
                .map(category -> buildVehicleOption(
                        category, pricingRequest, numberOfDays, yearsLicensed))
                .sorted((a, b) -> Double.compare(
                        a.getBookingAmount(), b.getBookingAmount()))
                .toList();
    }

    /** Fetches a reservation by ID and recalculates its pricing breakdown from the stored reservation data. */
    @Override
    public ReservationResponseDto getReservation(Long reservationId) {

        log.info("Fetching reservation: id={}", reservationId);

        Reservation reservation = reservationRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));

        int numberOfDays = ReservationServiceUtil.calculateNumberOfDays(reservation.getStartDate(), reservation.getEndDate());

        double mileage = reservation.getDailyMileage()   != null
                ? reservation.getDailyMileage()   : 0.0;
        int years      = reservation.getYearsLicensed() != null
                ? reservation.getYearsLicensed() : 0;

        PricingRequest pricingRequest =
                new PricingRequest(numberOfDays, mileage, years);

        PricingStrategy strategy = pricingStrategyFactory
                .getStrategy(reservation.getVehicle().getCategory());

        List<BreakdownLineDto> breakdown =
                strategy.calculateBreakdown(pricingRequest);

        return ReservationServiceUtil.mapToResponseDto(
                reservation, numberOfDays, breakdown);
    }

    /** Builds a VehicleOptionDto for a given category, including surcharge details for pick-up trucks. */
    private VehicleOptionDto buildVehicleOption(
            VehicleCategoryEnum category,
            PricingRequest pricingRequest,
            int numberOfDays,
            int yearsLicensed) {

        PricingStrategy strategy =
                pricingStrategyFactory.getStrategy(category);

        double total   = strategy.calculateTotal(pricingRequest);
        String summary = strategy.getPricingSummary(pricingRequest);

        boolean surchargeApplied = false;
        int surchargePercent = 0;

        if (category == VehicleCategoryEnum.PICKUP_TRUCK) {
            PickUpTruckPricingStrategy pickupStrategy =
                    (PickUpTruckPricingStrategy) strategy;
            surchargeApplied = pickupStrategy.hasSurcharge(yearsLicensed);
            surchargePercent = surchargeApplied
                    ? pickupStrategy.getSurchargePercentage() : 0;
        }

        return VehicleOptionDto.builder()
                .vehicleCategory(category)
                .noOfDays(numberOfDays)
                .bookingAmount(total)
                .pricingSummary(summary)
                .surchargeApplied(surchargeApplied)
                .surchargePercentage(surchargePercent)
                .build();
    }
}