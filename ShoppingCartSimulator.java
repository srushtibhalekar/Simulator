import java.util.ArrayList;
import java.util.Scanner;

class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public void displayProduct() {
        System.out.printf("%-5d %-20s ₹%.2f%n", id, name, price);
    }
}

class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    public void displayCartItem() {
        System.out.printf(
                "%-5d %-20s %-10d ₹%.2f%n",
                product.getId(),
                product.getName(),
                quantity,
                getTotalPrice()
        );
    }
}

public class ShoppingCartSimulator {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Product> products = new ArrayList<>();
    static ArrayList<CartItem> cart = new ArrayList<>();

    public static void main(String[] args) {

        addProducts();

        int choice;

        do {
            System.out.println("\n========================================");
            System.out.println("       🛒 SHOPPING CART SIMULATOR");
            System.out.println("========================================");
            System.out.println("1. View Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Remove Product from Cart");
            System.out.println("5. View Total");
            System.out.println("6. Clear Cart");
            System.out.println("7. Exit");
            System.out.println("========================================");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    viewProducts();
                    break;

                case 2:
                    addToCart();
                    break;

                case 3:
                    viewCart();
                    break;

                case 4:
                    removeFromCart();
                    break;

                case 5:
                    viewTotal();
                    break;

                case 6:
                    clearCart();
                    break;

                case 7:
                    System.out.println("\nThank you for using Shopping Cart Simulator!");
                    break;

                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }

        } while (choice != 7);

        scanner.close();
    }

    public static void addProducts() {

        products.add(new Product(1, "Laptop", 55000));
        products.add(new Product(2, "Headphones", 2500));
        products.add(new Product(3, "Keyboard", 1500));
        products.add(new Product(4, "Mouse", 800));
        products.add(new Product(5, "Smartphone", 25000));
        products.add(new Product(6, "Power Bank", 1200));
        products.add(new Product(7, "USB Cable", 400));
        products.add(new Product(8, "Smart Watch", 3500));
    }

    public static void viewProducts() {

        System.out.println("\n--------------- PRODUCTS ---------------");
        System.out.printf("%-5s %-20s %-10s%n", "ID", "Product", "Price");
        System.out.println("-----------------------------------------");

        for (Product product : products) {
            product.displayProduct();
        }
    }

    public static void addToCart() {

        viewProducts();

        System.out.print("\nEnter Product ID: ");
        int id = scanner.nextInt();

        Product selectedProduct = findProduct(id);

        if (selectedProduct == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        CartItem existingItem = findCartItem(id);

        if (existingItem != null) {
            existingItem.increaseQuantity(quantity);
            System.out.println("Product quantity updated in cart.");
        } else {
            cart.add(new CartItem(selectedProduct, quantity));
            System.out.println("Product added to cart successfully.");
        }
    }

    public static void viewCart() {

        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty.");
            return;
        }

        System.out.println("\n---------------- YOUR CART ----------------");
        System.out.printf(
                "%-5s %-20s %-10s %-10s%n",
                "ID", "Product", "Quantity", "Total"
        );
        System.out.println("-------------------------------------------");

        for (CartItem item : cart) {
            item.displayCartItem();
        }

        System.out.println("-------------------------------------------");
        System.out.printf("Cart Total: ₹%.2f%n", calculateTotal());
    }

    public static void removeFromCart() {

        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty.");
            return;
        }

        viewCart();

        System.out.print("\nEnter Product ID to remove: ");
        int id = scanner.nextInt();

        CartItem item = findCartItem(id);

        if (item != null) {
            cart.remove(item);
            System.out.println("Product removed from cart successfully.");
        } else {
            System.out.println("Product not found in cart.");
        }
    }

    public static void viewTotal() {

        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty.");
            return;
        }

        double total = calculateTotal();

        System.out.println("\n========== BILL SUMMARY ==========");
        System.out.printf("Total Items: %d%n", calculateTotalItems());
        System.out.printf("Total Amount: ₹%.2f%n", total);
        System.out.println("==================================");
    }

    public static void clearCart() {

        if (cart.isEmpty()) {
            System.out.println("\nYour cart is already empty.");
            return;
        }

        cart.clear();

        System.out.println("\nCart cleared successfully.");
    }

    public static double calculateTotal() {

        double total = 0;

        for (CartItem item : cart) {
            total += item.getTotalPrice();
        }

        return total;
    }

    public static int calculateTotalItems() {

        int totalItems = 0;

        for (CartItem item : cart) {
            totalItems += item.getQuantity();
        }

        return totalItems;
    }

    public static Product findProduct(int id) {

        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }

        return null;
    }

    public static CartItem findCartItem(int id) {

        for (CartItem item : cart) {
            if (item.getProduct().getId() == id) {
                return item;
            }
        }

        return null;
    }
}