import java.util.ArrayList;
import java.util.Scanner;

class InventoryProduct {

private int id;
private String name;
private double price;
private int quantity;

public InventoryProduct(int id, String name, double price, int quantity) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
}

public int getId() {
    return id;
}

public String getName() {
    return name;
}

public double getPrice() {
    return price;
}

public int getQuantity() {
    return quantity;
}

public void setQuantity(int quantity) {
    this.quantity = quantity;
}

public double getInventoryValue() {
    return price * quantity;
}

public void displayProduct() {
    System.out.printf(
            "%-5d %-20s ₹%-10.2f %-10d ₹%.2f%n",
            id,
            name,
            price,
            quantity,
            getInventoryValue()
    );
}


}

public class InventoryManagementSimulator {


static Scanner scanner = new Scanner(System.in);
static ArrayList<InventoryProduct> products = new ArrayList<>();

static int nextId = 1;

public static void main(String[] args) {

    addSampleProducts();

    int choice;

    do {

        System.out.println("\n==============================================");
        System.out.println("       📦 INVENTORY MANAGEMENT SIMULATOR");
        System.out.println("==============================================");
        System.out.println("1. Add Product");
        System.out.println("2. View All Products");
        System.out.println("3. Search Product");
        System.out.println("4. Update Stock");
        System.out.println("5. Sell Product");
        System.out.println("6. Remove Product");
        System.out.println("7. View Low Stock Products");
        System.out.println("8. View Inventory Value");
        System.out.println("9. Exit");
        System.out.println("==============================================");

        System.out.print("Enter your choice: ");
        choice = scanner.nextInt();

        switch (choice) {

            case 1:
                addProduct();
                break;

            case 2:
                viewProducts();
                break;

            case 3:
                searchProduct();
                break;

            case 4:
                updateStock();
                break;

            case 5:
                sellProduct();
                break;

            case 6:
                removeProduct();
                break;

            case 7:
                viewLowStock();
                break;

            case 8:
                viewInventoryValue();
                break;

            case 9:
                System.out.println("\nThank you for using Inventory Management Simulator!");
                break;

            default:
                System.out.println("\nInvalid choice. Please try again.");
        }

    } while (choice != 9);

    scanner.close();
}

public static void addSampleProducts() {

    products.add(
            new InventoryProduct(
                    nextId++,
                    "Laptop",
                    55000,
                    10
            )
    );

    products.add(
            new InventoryProduct(
                    nextId++,
                    "Keyboard",
                    1500,
                    25
            )
    );

    products.add(
            new InventoryProduct(
                    nextId++,
                    "Mouse",
                    800,
                    30
            )
    );

    products.add(
            new InventoryProduct(
                    nextId++,
                    "Headphones",
                    2500,
                    8
            )
    );
}

public static void addProduct() {

    scanner.nextLine();

    System.out.println("\n---------- ADD PRODUCT ----------");

    System.out.print("Enter product name: ");
    String name = scanner.nextLine();

    if (name.trim().isEmpty()) {
        System.out.println("Product name cannot be empty.");
        return;
    }

    System.out.print("Enter product price: ");
    double price = scanner.nextDouble();

    if (price <= 0) {
        System.out.println("Price must be greater than 0.");
        return;
    }

    System.out.print("Enter product quantity: ");
    int quantity = scanner.nextInt();

    if (quantity < 0) {
        System.out.println("Quantity cannot be negative.");
        return;
    }

    InventoryProduct product =
            new InventoryProduct(
                    nextId++,
                    name,
                    price,
                    quantity
            );

    products.add(product);

    System.out.println("\nProduct added successfully!");
    System.out.println("Product ID: " + product.getId());
}

public static void viewProducts() {

    if (products.isEmpty()) {
        System.out.println("\nInventory is empty.");
        return;
    }

    System.out.println("\n---------------- INVENTORY ----------------");

    System.out.printf(
            "%-5s %-20s %-11s %-10s %-15s%n",
            "ID",
            "Product",
            "Price",
            "Quantity",
            "Inventory Value"
    );

    System.out.println(
            "--------------------------------------------------------------"
    );

    for (InventoryProduct product : products) {
        product.displayProduct();
    }

    System.out.println(
            "--------------------------------------------------------------"
    );
}

public static void searchProduct() {

    System.out.print("\nEnter Product ID: ");
    int id = scanner.nextInt();

    InventoryProduct product = findProduct(id);

    if (product == null) {
        System.out.println("Product not found.");
        return;
    }

    System.out.println("\n---------- PRODUCT DETAILS ----------");

    System.out.println("Product ID     : " + product.getId());
    System.out.println("Product Name   : " + product.getName());
    System.out.printf("Price          : ₹%.2f%n", product.getPrice());
    System.out.println("Quantity       : " + product.getQuantity());
    System.out.printf(
            "Inventory Value: ₹%.2f%n",
            product.getInventoryValue()
    );
}

public static void updateStock() {

    System.out.print("\nEnter Product ID: ");
    int id = scanner.nextInt();

    InventoryProduct product = findProduct(id);

    if (product == null) {
        System.out.println("Product not found.");
        return;
    }

    System.out.println(
            "Current Stock: " + product.getQuantity()
    );

    System.out.print("Enter quantity to add: ");
    int quantity = scanner.nextInt();

    if (quantity <= 0) {
        System.out.println("Quantity must be greater than 0.");
        return;
    }

    int newQuantity =
            product.getQuantity() + quantity;

    product.setQuantity(newQuantity);

    System.out.println("\nStock updated successfully!");
    System.out.println(
            "New Stock: " + product.getQuantity()
    );
}

public static void sellProduct() {

    System.out.print("\nEnter Product ID: ");
    int id = scanner.nextInt();

    InventoryProduct product = findProduct(id);

    if (product == null) {
        System.out.println("Product not found.");
        return;
    }

    if (product.getQuantity() == 0) {
        System.out.println("Product is out of stock.");
        return;
    }

    System.out.println(
            "Available Stock: " + product.getQuantity()
    );

    System.out.print("Enter quantity to sell: ");
    int quantity = scanner.nextInt();

    if (quantity <= 0) {
        System.out.println("Quantity must be greater than 0.");
        return;
    }

    if (quantity > product.getQuantity()) {
        System.out.println(
                "Not enough stock available."
        );
        return;
    }

    int remainingStock =
            product.getQuantity() - quantity;

    product.setQuantity(remainingStock);

    double totalAmount =
            quantity * product.getPrice();

    System.out.println("\nSale completed successfully!");

    System.out.println("Product: " + product.getName());
    System.out.println("Quantity Sold: " + quantity);

    System.out.printf(
            "Total Amount: ₹%.2f%n",
            totalAmount
    );

    System.out.println(
            "Remaining Stock: " + product.getQuantity()
    );
}

public static void removeProduct() {

    System.out.print("\nEnter Product ID: ");
    int id = scanner.nextInt();

    InventoryProduct product = findProduct(id);

    if (product == null) {
        System.out.println("Product not found.");
        return;
    }

    products.remove(product);

    System.out.println(
            "Product removed successfully!"
    );
}

public static void viewLowStock() {

    boolean found = false;

    System.out.println("\n---------- LOW STOCK PRODUCTS ----------");

    System.out.printf(
            "%-5s %-20s %-10s%n",
            "ID",
            "Product",
            "Quantity"
    );

    System.out.println("-----------------------------------------");

    for (InventoryProduct product : products) {

        if (product.getQuantity() <= 10) {

            System.out.printf(
                    "%-5d %-20s %-10d%n",
                    product.getId(),
                    product.getName(),
                    product.getQuantity()
            );

            found = true;
        }
    }

    if (!found) {
        System.out.println("No low-stock products found.");
    }
}

public static void viewInventoryValue() {

    double totalValue = 0;

    int totalProducts = 0;
    int totalQuantity = 0;

    for (InventoryProduct product : products) {

        totalValue += product.getInventoryValue();

        totalProducts++;

        totalQuantity += product.getQuantity();
    }

    System.out.println("\n========== INVENTORY SUMMARY ==========");

    System.out.println(
            "Total Products : " + totalProducts
    );

    System.out.println(
            "Total Quantity : " + totalQuantity
    );

    System.out.printf(
            "Inventory Value: ₹%.2f%n",
            totalValue
    );

    System.out.println(
            "======================================="
    );
}

public static InventoryProduct findProduct(int id) {

    for (InventoryProduct product : products) {

        if (product.getId() == id) {
            return product;
        }
    }

    return null;
}


}
