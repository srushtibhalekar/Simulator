import java.util.ArrayList;
import java.util.Scanner;

abstract class Room {

    private int roomNumber;
    private String roomType;
    private double pricePerNight;
    private boolean booked;

    public Room(int roomNumber, String roomType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.booked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isBooked() {
        return booked;
    }

    public void bookRoom() {
        booked = true;
    }

    public void cancelBooking() {
        booked = false;
    }

    public abstract void displayRoom();
}

class StandardRoom extends Room {

    public StandardRoom(int roomNumber) {
        super(roomNumber, "Standard", 1500);
    }

    @Override
    public void displayRoom() {
        System.out.println("--------------------------------");
        System.out.println("Room Number    : " + getRoomNumber());
        System.out.println("Room Type      : " + getRoomType());
        System.out.println("Price/Night    : ₹" + getPricePerNight());
        System.out.println("Status         : " +
                (isBooked() ? "Booked" : "Available"));
    }
}

class DeluxeRoom extends Room {

    public DeluxeRoom(int roomNumber) {
        super(roomNumber, "Deluxe", 2500);
    }

    @Override
    public void displayRoom() {
        System.out.println("--------------------------------");
        System.out.println("Room Number    : " + getRoomNumber());
        System.out.println("Room Type      : " + getRoomType());
        System.out.println("Price/Night    : ₹" + getPricePerNight());
        System.out.println("Status         : " +
                (isBooked() ? "Booked" : "Available"));
    }
}

class Booking {

    private int bookingId;
    private String customerName;
    private String phoneNumber;
    private int roomNumber;
    private int nights;

    public Booking(int bookingId, String customerName,
                   String phoneNumber, int roomNumber, int nights) {

        this.bookingId = bookingId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.roomNumber = roomNumber;
        this.nights = nights;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getNights() {
        return nights;
    }

    public void displayBooking(double pricePerNight) {

        double totalAmount = pricePerNight * nights;

        System.out.println("--------------------------------");
        System.out.println("Booking ID     : " + bookingId);
        System.out.println("Customer Name  : " + customerName);
        System.out.println("Phone Number   : " + phoneNumber);
        System.out.println("Room Number    : " + roomNumber);
        System.out.println("Nights         : " + nights);
        System.out.println("Price/Night    : ₹" + pricePerNight);
        System.out.println("Total Amount   : ₹" + totalAmount);
    }
}

public class HotelReservationSimulator {

    static Scanner scanner = new Scanner(System.in);

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();

    static int nextBookingId = 1;

    public static void main(String[] args) {

        addDefaultRooms();

        int choice;

        do {

            System.out.println("\n==========================================");
            System.out.println("       HOTEL RESERVATION SIMULATOR");
            System.out.println("==========================================");
            System.out.println("1. View All Rooms");
            System.out.println("2. View Available Rooms");
            System.out.println("3. Add Room");
            System.out.println("4. Book Room");
            System.out.println("5. View All Bookings");
            System.out.println("6. Search Booking");
            System.out.println("7. Cancel Booking");
            System.out.println("8. Check-in");
            System.out.println("9. Check-out");
            System.out.println("10. Exit");
            System.out.println("==========================================");

            choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    viewAllRooms();
                    break;

                case 2:
                    viewAvailableRooms();
                    break;

                case 3:
                    addRoom();
                    break;

                case 4:
                    bookRoom();
                    break;

                case 5:
                    viewAllBookings();
                    break;

                case 6:
                    searchBooking();
                    break;

                case 7:
                    cancelBooking();
                    break;

                case 8:
                    checkIn();
                    break;

                case 9:
                    checkOut();
                    break;

                case 10:
                    System.out.println("\nThank you for using Hotel Reservation Simulator!");
                    break;

                default:
                    System.out.println("\nInvalid choice! Please enter 1 to 10.");
            }

        } while (choice != 10);

        scanner.close();
    }

    // Add default rooms
    public static void addDefaultRooms() {

        rooms.add(new StandardRoom(101));
        rooms.add(new StandardRoom(102));
        rooms.add(new StandardRoom(103));

        rooms.add(new DeluxeRoom(201));
        rooms.add(new DeluxeRoom(202));
        rooms.add(new DeluxeRoom(203));
    }

    // View all rooms
    public static void viewAllRooms() {

        System.out.println("\n---------- ALL ROOMS ----------");

        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }

        for (Room room : rooms) {
            room.displayRoom();
        }

        System.out.println("--------------------------------");
        System.out.println("Total Rooms: " + rooms.size());
    }

    // View available rooms
    public static void viewAvailableRooms() {

        System.out.println("\n---------- AVAILABLE ROOMS ----------");

        boolean found = false;

        for (Room room : rooms) {

            if (!room.isBooked()) {
                room.displayRoom();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms are currently available.");
        }
    }

    // Add new room
    public static void addRoom() {

        System.out.println("\n---------- ADD ROOM ----------");

        int roomNumber = readInt("Enter room number: ");

        if (findRoom(roomNumber) != null) {
            System.out.println("Room number already exists.");
            return;
        }

        System.out.println("\nSelect Room Type:");
        System.out.println("1. Standard - ₹1500/night");
        System.out.println("2. Deluxe   - ₹2500/night");

        int type = readInt("Enter room type: ");

        if (type == 1) {

            rooms.add(new StandardRoom(roomNumber));

        } else if (type == 2) {

            rooms.add(new DeluxeRoom(roomNumber));

        } else {

            System.out.println("Invalid room type.");
            return;
        }

        System.out.println("Room added successfully!");
    }

    // Book room
    public static void bookRoom() {

        System.out.println("\n---------- BOOK ROOM ----------");

        int roomNumber = readInt("Enter room number: ");

        Room room = findRoom(roomNumber);

        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        if (room.isBooked()) {
            System.out.println("Room is already booked.");
            return;
        }

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine().trim();

        if (customerName.isEmpty()) {
            System.out.println("Customer name cannot be empty.");
            return;
        }

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine().trim();

        if (!phoneNumber.matches("\\d{10}")) {
            System.out.println("Phone number must contain exactly 10 digits.");
            return;
        }

        int nights = readInt("Enter number of nights: ");

        if (nights <= 0) {
            System.out.println("Number of nights must be greater than 0.");
            return;
        }

        Booking booking = new Booking(
                nextBookingId,
                customerName,
                phoneNumber,
                roomNumber,
                nights
        );

        bookings.add(booking);
        room.bookRoom();

        double totalAmount = room.getPricePerNight() * nights;

        System.out.println("\nRoom booked successfully!");
        System.out.println("Booking ID : " + nextBookingId);
        System.out.println("Room       : " + roomNumber);
        System.out.println("Total Cost : ₹" + totalAmount);

        nextBookingId++;
    }

    // View all bookings
    public static void viewAllBookings() {

        System.out.println("\n---------- ALL BOOKINGS ----------");

        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Booking booking : bookings) {

            Room room = findRoom(booking.getRoomNumber());

            if (room != null) {
                booking.displayBooking(room.getPricePerNight());
            }
        }

        System.out.println("----------------------------------");
        System.out.println("Total Bookings: " + bookings.size());
    }

    // Search booking
    public static void searchBooking() {

        System.out.println("\n---------- SEARCH BOOKING ----------");

        int bookingId = readInt("Enter Booking ID: ");

        Booking booking = findBooking(bookingId);

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        Room room = findRoom(booking.getRoomNumber());

        System.out.println("\nBooking Found!");

        if (room != null) {
            booking.displayBooking(room.getPricePerNight());
        }
    }

    // Cancel booking
    public static void cancelBooking() {

        System.out.println("\n---------- CANCEL BOOKING ----------");

        int bookingId = readInt("Enter Booking ID: ");

        Booking booking = findBooking(bookingId);

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        Room room = findRoom(booking.getRoomNumber());

        if (room != null) {
            room.cancelBooking();
        }

        bookings.remove(booking);

        System.out.println("Booking cancelled successfully.");
        System.out.println("Booking ID: " + bookingId);
    }

    // Check-in
    public static void checkIn() {

        System.out.println("\n---------- CHECK-IN ----------");

        int bookingId = readInt("Enter Booking ID: ");

        Booking booking = findBooking(bookingId);

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        System.out.println("Customer " + booking.getCustomerName()
                + " checked in successfully.");

        System.out.println("Room Number: " + booking.getRoomNumber());
    }

    // Check-out
    public static void checkOut() {

        System.out.println("\n---------- CHECK-OUT ----------");

        int bookingId = readInt("Enter Booking ID: ");

        Booking booking = findBooking(bookingId);

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        Room room = findRoom(booking.getRoomNumber());

        if (room != null) {

            double totalAmount =
                    room.getPricePerNight() * booking.getNights();

            System.out.println("Customer: " + booking.getCustomerName());
            System.out.println("Room: " + booking.getRoomNumber());
            System.out.println("Total Bill: ₹" + totalAmount);

            room.cancelBooking();
        }

        bookings.remove(booking);

        System.out.println("Check-out completed successfully.");
    }

    // Find room
    public static Room findRoom(int roomNumber) {

        for (Room room : rooms) {

            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }

        return null;
    }

    // Find booking
    public static Booking findBooking(int bookingId) {

        for (Booking booking : bookings) {

            if (booking.getBookingId() == bookingId) {
                return booking;
            }
        }

        return null;
    }

    // Safe integer input
    public static int readInt(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine();

            try {

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input! Please enter a valid number."
                );
            }
        }
    }
}