import java.util.ArrayList;
import java.util.Scanner;

class Vehicle {

    int id;
    String brand;
    double pricePerDay;
    boolean rented;

    Vehicle(int id, String brand, double pricePerDay) {
        this.id = id;
        this.brand = brand;
        this.pricePerDay = pricePerDay;
        this.rented = false;
    }

    void display() {
        System.out.println(
                "ID: " + id +
                " | Brand: " + brand +
                " | Price/Day: ₹" + pricePerDay +
                " | Status: " + (rented ? "Rented" : "Available")
        );
    }
}

class Car extends Vehicle {

    Car(int id, String brand, double pricePerDay) {
        super(id, brand, pricePerDay);
    }

    @Override
    void display() {
        System.out.println(
                "🚗 Car | ID: " + id +
                " | Brand: " + brand +
                " | ₹" + pricePerDay + "/day" +
                " | Status: " + (rented ? "Rented" : "Available")
        );
    }
}

class Bike extends Vehicle {

    Bike(int id, String brand, double pricePerDay) {
        super(id, brand, pricePerDay);
    }

    @Override
    void display() {
        System.out.println(
                "🏍️ Bike | ID: " + id +
                " | Brand: " + brand +
                " | ₹" + pricePerDay + "/day" +
                " | Status: " + (rented ? "Rented" : "Available")
        );
    }
}

public class VehicleRentalSimulator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Vehicle> vehicles = new ArrayList<>();

        vehicles.add(new Car(101, "Honda", 1500));
        vehicles.add(new Car(102, "Toyota", 1800));
        vehicles.add(new Bike(201, "Yamaha", 700));
        vehicles.add(new Bike(202, "Honda", 600));

        System.out.println("================================");
        System.out.println("    🚗 VEHICLE RENTAL SYSTEM");
        System.out.println("================================");

        while (true) {

            System.out.println("\n===== RENTAL MENU =====");
            System.out.println("1. View Vehicles");
            System.out.println("2. Rent Vehicle");
            System.out.println("3. Return Vehicle");
            System.out.println("4. View Rental Status");
            System.out.println("5. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.println("\n===== VEHICLES =====");

                    for (Vehicle vehicle : vehicles) {
                        vehicle.display();
                    }

                    break;

                case 2:

                    System.out.print("Enter vehicle ID: ");
                    int rentId = sc.nextInt();

                    Vehicle rentVehicle = null;

                    for (Vehicle vehicle : vehicles) {

                        if (vehicle.id == rentId) {
                            rentVehicle = vehicle;
                            break;
                        }
                    }

                    if (rentVehicle == null) {

                        System.out.println("❌ Vehicle not found.");

                    } else if (rentVehicle.rented) {

                        System.out.println("❌ Vehicle is already rented.");

                    } else {

                        System.out.print("Enter number of days: ");
                        int days = sc.nextInt();

                        if (days <= 0) {

                            System.out.println("❌ Days must be greater than 0.");

                        } else {

                            double totalCost =
                                    rentVehicle.pricePerDay * days;

                            rentVehicle.rented = true;

                            System.out.println("\n✅ Vehicle rented successfully!");
                            System.out.println("Vehicle: " + rentVehicle.brand);
                            System.out.println("Days: " + days);
                            System.out.println("Total Cost: ₹" + totalCost);
                        }
                    }

                    break;

                case 3:

                    System.out.print("Enter vehicle ID: ");
                    int returnId = sc.nextInt();

                    Vehicle returnVehicle = null;

                    for (Vehicle vehicle : vehicles) {

                        if (vehicle.id == returnId) {
                            returnVehicle = vehicle;
                            break;
                        }
                    }

                    if (returnVehicle == null) {

                        System.out.println("❌ Vehicle not found.");

                    } else if (!returnVehicle.rented) {

                        System.out.println("❌ Vehicle is not currently rented.");

                    } else {

                        returnVehicle.rented = false;

                        System.out.println(
                                "✅ " + returnVehicle.brand +
                                " returned successfully!"
                        );
                    }

                    break;

                case 4:

                    System.out.println("\n===== RENTAL STATUS =====");

                    for (Vehicle vehicle : vehicles) {

                        System.out.println(
                                "ID: " + vehicle.id +
                                " | " + vehicle.brand +
                                " | " +
                                (vehicle.rented
                                        ? "🔴 Rented"
                                        : "🟢 Available")
                        );
                    }

                    break;

                case 5:

                    System.out.println("\n================================");
                    System.out.println("     🚗 RENTAL SYSTEM CLOSED");
                    System.out.println("================================");

                    sc.close();
                    return;

                default:

                    System.out.println("❌ Invalid choice!");
            }
        }
    }
}