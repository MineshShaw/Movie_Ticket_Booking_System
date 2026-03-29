# Movie Ticket Booking System (LLD)

An enterprise-grade low-level design implementation of a movie ticket booking system. The system supports high-concurrency seat locking, automated booking expiry, and pluggable pricing and payment strategies using Java.

---

## 🚀 How it Works

The system follows a modular and decoupled flow to ensure scalability and thread safety:

### 1. Configuration (The Blueprint):

Using the SystemFactory, the client initializes the system with configurable strategies such as pricing models and refund policies.

### 2. Search & Discovery:

The SearchService enables filtering of movies based on city, theatre, or other attributes. The Repository layer abstracts data access, keeping business logic independent of storage.

### 3. Seat Locking & Allocation:

When a user selects seats, the SeatLockManager attempts to lock them using state validation and synchronized operations to prevent race conditions.

### 4. Concurrency Control:

Seat-level locking ensures that no two users can reserve the same seat simultaneously, even under high contention scenarios.

### 5. Automated Expiry:

The BookingExpiryScheduler starts a background task for each booking. If payment is not completed within the configured time window, seats are released automatically.

### 6. Payment & Confirmation:

The PaymentProxy integrates with external payment systems. Upon successful payment, the PaymentStatusObserver updates the booking state and cancels any scheduled expiry.

---

📊 Low Level Design (UML)
![Ticket Booking System](./Movie_Ticket_Booking_System.drawio.svg)

---

## 📁 File Structure
```
src/main/java/com/ticketbookingsystem
├── Main.java                                 # Entry point & concurrency simulation
├── client
│   └── MovieTicketBookingClient.java         # Facade for interacting with the system
├── controller
│   ├── AdminController.java                  # APIs for managing movies, shows, theatres
│   ├── BookingController.java                # Booking-related user APIs
│   └── SearchController.java                 # Search endpoints for discovery
├── factory
│   └── SystemFactory.java                    # System assembler with pluggable strategies
├── manager
│   ├── BookingManager.java                   # Booking lifecycle orchestration
│   └── SeatLockManager.java                  # Thread-safe seat locking logic
├── model
│   ├── Booking.java                          # Booking aggregate
│   ├── City.java                             # City representation
│   ├── Movie.java                            # Movie metadata
│   ├── Payment.java                          # Payment transaction entity
│   ├── PriceComponent.java                   # Pricing breakdown component
│   ├── Refund.java                           # Refund entity
│   ├── Screen.java                           # Screen within a theatre
│   ├── Seat.java                             # Seat entity with type and state
│   ├── Show.java                             # Movie show (time + screen)
│   ├── Theatre.java                          # Theatre with multiple screens
│   ├── User.java                             # User entity
│   └── enums
│       ├── BookingState.java                 # CREATED, LOCKED, CONFIRMED, etc.
│       ├── PaymentState.java                 # INITIATED, SUCCESS, FAILED
│       ├── SeatState.java                    # AVAILABLE, LOCKED, BOOKED
│       ├── SeatType.java                     # REGULAR, VIP, etc.
│       └── UserRole.java                     # ADMIN, CUSTOMER
├── observer
│   └── PaymentStatusObserver.java            # Listens to payment events
├── proxy
│   ├── PaymentGateway.java                   # External payment interface
│   └── PaymentProxy.java                     # Proxy for secure payment handling
├── repository                                # In-memory persistence layer
│   ├── BookingRepository.java
│   ├── BookingRepositoryImpl.java
│   ├── CityRepository.java
│   ├── CityRepositoryImpl.java
│   ├── MovieRepository.java
│   ├── MovieRepositoryImpl.java
│   ├── PaymentRepository.java
│   ├── PaymentRepositoryImpl.java
│   ├── ShowRepository.java
│   ├── ShowRepositoryImpl.java
│   ├── TheatreRepository.java
│   └── TheatreRepositoryImpl.java           
├── scheduler
│   └── BookingExpiryScheduler.java          # Handles booking timeout tasks
├── service
│   ├── AdminService.java                    # Admin operations orchestration
│   ├── BookingService.java                  # Core booking logic
│   ├── PaymentService.java                  # Payment orchestration
│   └── SearchService.java                   # Search functionality
└── strategy
    ├── pricing
    │   ├── PricingStrategy.java             # Pricing abstraction
    │   ├── DefaultPricingStrategy.java      # Base pricing logic
    │   └── DynamicPricingStrategy.java      # Demand-based pricing
    └── refund
        ├── RefundPolicy.java                # Refund abstraction
        ├── FullRefundPolicy.java            # Full refund logic
        └── TimeBasedRefundPolicy.java       # Time-dependent refunds
```

---

## 🛠️ Compilation & Execution

### 1. Compile
```
javac -d out --source-path src/main/java src/main/java/com/ticketbookingsystem/Main.java
```

### 2. Run
```
java -cp out com.ticketbookingsystem.Main
```
> Note:
> The simulation uses concurrent booking requests (via CountDownLatch) to test seat contention and thread safety.

---

## ✨ Key Features

### 1. Thread Safety:
Implemented using synchronized blocks and concurrent utilities to ensure safe seat allocation under high load.

### 2. Strategy Pattern:
Supports interchangeable pricing and refund strategies without modifying core logic.

### 3. Proxy Pattern:
Encapsulates external payment integrations, allowing validation, logging, and security layers.

### 4. Observer Pattern:
Decouples payment and booking modules via event-driven state updates.

---