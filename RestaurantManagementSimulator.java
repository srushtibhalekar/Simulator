import java.util.ArrayList;
import java.util.Scanner;

class MenuItem {
    private int itemId;
    private String name;
    private double price;
    private String category;

    public MenuItem(int itemId, String name, double price, String category) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void displayItem() {
        System.out.println(
                "ID: " + itemId +
                " | Name: " + name +
                " | Category: " + category +
                " | Price: ₹" + price
        );
    }
}

class Order {
    private int orderId;
    private String customerName;
    private String itemName;
    private int quantity;
    private double totalAmount;
    private String status;

    public Order(int orderId, String customerName, String itemName,
                 int quantity, double totalAmount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = "Placed";
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getItemName() {
        return itemName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayOrder() {
        System.out.println(
                "Order ID: " + orderId +
                " | Customer: " + customerName +
                " | Item: " + itemName +
                " | Quantity: " + quantity +
                " | Total: ₹" + totalAmount +
                " | Status: " + status
        );
    }
}

public class RestaurantManagementSimulator {

    static Scanner scanner = new Scanner(System.in);

    static ArrayList<MenuItem> menuItems = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();

    static int nextItemId = 1;
    static int nextOrderId = 1;

    public static void main(String[] args) {

        addSampleMenu();

        while (true) {
            System.out.println("\n======================================");
            System.out.println("   RESTAURANT MANAGEMENT SIMULATOR");
            System.out.println("======================================");
            System.out.println("1. Add Menu Item");
            System.out.println("2. View Menu");
            System.out.println("3. Search Menu Item");
            System.out.println("4. Update Menu Item");
            System.out.println("5. Remove Menu Item");
            System.out.println("6. Place Order");
            System.out.println("7. View Orders");
            System.out.println("8. Calculate Order Bill");
            System.out.println("9. Update Order Status");
            System.out.println("10. Cancel Order");
            System.out.println("11. Exit");
            System.out.println("======================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addMenuItem();
                    break;

                case 2:
                    viewMenu();
                    break;

                case 3:
                    searchMenuItem();
                    break;

                case 4:
                    updateMenuItem();
                    break;

                case 5:
                    removeMenuItem();
                    break;

                case 6:
                    placeOrder();
                    break;

                case 7:
                    viewOrders();
                    break;

                case 8:
                    calculateOrderBill();
                    break;

                case 9:
                    updateOrderStatus();
                    break;

                case 10:
                    cancelOrder();
                    break;

                case 11:
                    System.out.println("Thank you for using Restaurant Management Simulator!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void addSampleMenu() {
        menuItems.add(new MenuItem(nextItemId++, "Pizza", 250, "Main Course"));
        menuItems.add(new MenuItem(nextItemId++, "Burger", 150, "Fast Food"));
        menuItems.add(new MenuItem(nextItemId++, "Pasta", 200, "Main Course"));
        menuItems.add(new MenuItem(nextItemId++, "Cold Coffee", 100, "Beverage"));
    }

    static void addMenuItem() {

        System.out.println("\n--- Add Menu Item ---");

        String name = readString("Enter item name: ");

        double price = readPositiveDouble("Enter item price: ");

        String category = readString("Enter category: ");

        MenuItem item = new MenuItem(
                nextItemId++,
                name,
                price,
                category
        );

        menuItems.add(item);

        System.out.println("Menu item added successfully.");
        System.out.println("Item ID: " + item.getItemId());
    }

    static void viewMenu() {

        System.out.println("\n--- Restaurant Menu ---");

        if (menuItems.isEmpty()) {
            System.out.println("No menu items available.");
            return;
        }

        for (MenuItem item : menuItems) {
            item.displayItem();
        }
    }

    static void searchMenuItem() {

        System.out.println("\n--- Search Menu Item ---");

        String name = readString("Enter item name to search: ");

        boolean found = false;

        for (MenuItem item : menuItems) {

            if (item.getName().equalsIgnoreCase(name)) {
                item.displayItem();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Menu item not found.");
        }
    }

    static void updateMenuItem() {

        System.out.println("\n--- Update Menu Item ---");

        int id = readInt("Enter item ID: ");

        MenuItem item = findMenuItemById(id);

        if (item == null) {
            System.out.println("Menu item not found.");
            return;
        }

        System.out.println("Current details:");
        item.displayItem();

        String name = readString("Enter new item name: ");
        double price = readPositiveDouble("Enter new price: ");
        String category = readString("Enter new category: ");

        item.setName(name);
        item.setPrice(price);
        item.setCategory(category);

        System.out.println("Menu item updated successfully.");
    }

    static void removeMenuItem() {

        System.out.println("\n--- Remove Menu Item ---");

        int id = readInt("Enter item ID: ");

        MenuItem item = findMenuItemById(id);

        if (item == null) {
            System.out.println("Menu item not found.");
            return;
        }

        menuItems.remove(item);

        System.out.println("Menu item removed successfully.");
    }

    static void placeOrder() {

        System.out.println("\n--- Place Order ---");

        if (menuItems.isEmpty()) {
            System.out.println("No menu items available.");
            return;
        }

        String customerName = readString("Enter customer name: ");

        viewMenu();

        int itemId = readInt("Enter item ID: ");

        MenuItem item = findMenuItemById(itemId);

        if (item == null) {
            System.out.println("Invalid item ID.");
            return;
        }

        int quantity = readPositiveInt("Enter quantity: ");

        double total = item.getPrice() * quantity;

        Order order = new Order(
                nextOrderId++,
                customerName,
                item.getName(),
                quantity,
                total
        );

        orders.add(order);

        System.out.println("\nOrder placed successfully.");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer: " + customerName);
        System.out.println("Item: " + item.getName());
        System.out.println("Quantity: " + quantity);
        System.out.println("Total Amount: ₹" + total);
    }

    static void viewOrders() {

        System.out.println("\n--- All Orders ---");

        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }

        for (Order order : orders) {
            order.displayOrder();
        }
    }

    static void calculateOrderBill() {

        System.out.println("\n--- Calculate Order Bill ---");

        int orderId = readInt("Enter order ID: ");

        Order order = findOrderById(orderId);

        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        double subtotal = order.getTotalAmount();
        double tax = subtotal * 0.05;
        double finalAmount = subtotal + tax;

        System.out.println("\n========== BILL ==========");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer: " + order.getCustomerName());
        System.out.println("Item: " + order.getItemName());
        System.out.println("Subtotal: ₹" + subtotal);
        System.out.println("GST (5%): ₹" + tax);
        System.out.println("--------------------------");
        System.out.println("Final Amount: ₹" + finalAmount);
        System.out.println("==========================");
    }

    static void updateOrderStatus() {

        System.out.println("\n--- Update Order Status ---");

        int orderId = readInt("Enter order ID: ");

        Order order = findOrderById(orderId);

        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        System.out.println("Current Status: " + order.getStatus());

        System.out.println("\nSelect New Status:");
        System.out.println("1. Preparing");
        System.out.println("2. Ready");
        System.out.println("3. Delivered");

        int choice = readInt("Enter choice: ");

        switch (choice) {

            case 1:
                order.setStatus("Preparing");
                break;

            case 2:
                order.setStatus("Ready");
                break;

            case 3:
                order.setStatus("Delivered");
                break;

            default:
                System.out.println("Invalid status choice.");
                return;
        }

        System.out.println("Order status updated successfully.");
    }

    static void cancelOrder() {

        System.out.println("\n--- Cancel Order ---");

        int orderId = readInt("Enter order ID: ");

        Order order = findOrderById(orderId);

        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        if (order.getStatus().equalsIgnoreCase("Delivered")) {
            System.out.println("Delivered orders cannot be cancelled.");
            return;
        }

        order.setStatus("Cancelled");

        System.out.println("Order cancelled successfully.");
    }

    static MenuItem findMenuItemById(int id) {

        for (MenuItem item : menuItems) {

            if (item.getItemId() == id) {
                return item;
            }
        }

        return null;
    }

    static Order findOrderById(int id) {

        for (Order order : orders) {

            if (order.getOrderId() == id) {
                return order;
            }
        }

        return null;
    }

    static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                int value = Integer.parseInt(scanner.nextLine());

                return value;

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
            }
        }
    }

    static int readPositiveInt(String message) {

        while (true) {

            int value = readInt(message);

            if (value > 0) {
                return value;
            }

            System.out.println("Value must be greater than zero.");
        }
    }

    static double readPositiveDouble(String message) {

        while (true) {

            try {

                System.out.print(message);

                double value = Double.parseDouble(scanner.nextLine());

                if (value > 0) {
                    return value;
                }

                System.out.println("Value must be greater than zero.");

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid amount.");
            }
        }
    }

    static String readString(String message) {

        while (true) {

            System.out.print(message);

            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Input cannot be empty.");
        }
    }
}