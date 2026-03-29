package com.ticketbookingsystem.factory;

import com.ticketbookingsystem.client.MovieTicketBookingClient;
import com.ticketbookingsystem.controller.*;
import com.ticketbookingsystem.manager.*;
import com.ticketbookingsystem.repository.*;
import com.ticketbookingsystem.scheduler.*;
import com.ticketbookingsystem.service.*;
import com.ticketbookingsystem.strategy.pricing.*;
import com.ticketbookingsystem.strategy.refund.*;
import com.ticketbookingsystem.proxy.*;

public class SystemFactory {
    private final CityRepository cityRepository = new CityRepositoryImpl();
    private final MovieRepository movieRepository = new MovieRepositoryImpl();
    private final TheatreRepository theatreRepository = new TheatreRepositoryImpl();
    private final ShowRepository showRepository = new ShowRepositoryImpl();
    private final BookingRepository bookingRepository = new BookingRepositoryImpl();
    private final PaymentRepository paymentRepository = new PaymentRepositoryImpl();

    private final BookingExpiryScheduler scheduler = new BookingExpiryScheduler();
    private final SeatLockManager seatLockManager = new SeatLockManager(showRepository);
    
    private final PricingStrategy pricingStrategy;
    private final RefundPolicy refundPolicy;
    private final PaymentService paymentService;
    private final BookingManager bookingManager;
    private final SearchService searchService;
    private final BookingService bookingService;
    private final AdminService adminService;

    private final AdminController adminController;
    private final BookingController bookingController;
    private final SearchController searchController;

    private final PaymentGateway paymentGateway = new PaymentGateway();
    private final PaymentProxy paymentProxy = new PaymentProxy(paymentGateway);

    private SystemFactory(String pricing, String refund) {
        switch (pricing) {
            case "dynamic":
                this.pricingStrategy = new DynamicPricingStrategy();
                break;
            case "fixed":
                this.pricingStrategy = new DefaultPricingStrategy();
                break;
            default:
                this.pricingStrategy = new DynamicPricingStrategy();
        }

        switch (refund) {
            case "time_based":
                this.refundPolicy = new TimeBasedRefundPolicy();
                break;
            case "strict":
                this.refundPolicy = new FullRefundPolicy();
                break;
            default:
                this.refundPolicy = new TimeBasedRefundPolicy();
        }

        this.paymentService = new PaymentService(paymentRepository, paymentProxy);
        
        this.bookingManager = new BookingManager(
                bookingRepository, showRepository, seatLockManager, pricingStrategy, 
                refundPolicy, paymentService, scheduler
        );

        this.searchService = new SearchService(cityRepository, theatreRepository);
        this.bookingService = new BookingService(bookingManager);
        this.adminService = new AdminService(cityRepository, theatreRepository, movieRepository, showRepository);

        this.adminController = new AdminController(adminService);
        this.bookingController = new BookingController(bookingService);
        this.searchController = new SearchController(searchService);
    }

    public static MovieTicketBookingClient createClient(String pricing, String refund) {
        SystemFactory factory = new SystemFactory(pricing, refund);
        return new MovieTicketBookingClient(factory.adminController, factory.searchController, factory.bookingController);
    }
}