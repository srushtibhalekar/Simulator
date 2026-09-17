import java.util.ArrayList;
import java.util.Scanner;

class Book {

    private int bookId;
    private String title;
    private String author;
    private boolean issued;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.issued = false;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return issued;
    }

    public void issueBook() {
        issued = true;
    }

    public void returnBook() {
        issued = false;
    }

    public void displayBook() {
        System.out.println("--------------------------------");
        System.out.println("Book ID     : " + bookId);
        System.out.println("Title       : " + title);
        System.out.println("Author      : " + author);
        System.out.println("Status      : " + (issued ? "Issued" : "Available"));
    }
}

public class LibraryManagementSimulator {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Book> books = new ArrayList<>();
    static int nextBookId = 1;

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n======================================");
            System.out.println("     LIBRARY MANAGEMENT SIMULATOR");
            System.out.println("======================================");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book by ID");
            System.out.println("4. Search Book by Title");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. Remove Book");
            System.out.println("8. Exit");
            System.out.println("======================================");

            choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addBook();
                    break;

                case 2:
                    viewAllBooks();
                    break;

                case 3:
                    searchBookById();
                    break;

                case 4:
                    searchBookByTitle();
                    break;

                case 5:
                    issueBook();
                    break;

                case 6:
                    returnBook();
                    break;

                case 7:
                    removeBook();
                    break;

                case 8:
                    System.out.println("\nThank you for using Library Management Simulator!");
                    break;

                default:
                    System.out.println("\nInvalid choice! Please enter a number from 1 to 8.");
            }

        } while (choice != 8);

        scanner.close();
    }

    // Add Book
    public static void addBook() {

        System.out.println("\n---------- ADD BOOK ----------");

        System.out.print("Enter book title: ");
        String title = scanner.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("Book title cannot be empty.");
            return;
        }

        System.out.print("Enter author name: ");
        String author = scanner.nextLine().trim();

        if (author.isEmpty()) {
            System.out.println("Author name cannot be empty.");
            return;
        }

        Book book = new Book(nextBookId, title, author);
        books.add(book);

        System.out.println("\nBook added successfully!");
        System.out.println("Book ID: " + nextBookId);

        nextBookId++;
    }

    // View All Books
    public static void viewAllBooks() {

        System.out.println("\n---------- ALL BOOKS ----------");

        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }

        for (Book book : books) {
            book.displayBook();
        }

        System.out.println("--------------------------------");
        System.out.println("Total Books: " + books.size());
    }

    // Search Book by ID
    public static void searchBookById() {

        System.out.println("\n---------- SEARCH BOOK BY ID ----------");

        int id = readInt("Enter Book ID: ");

        Book book = findBookById(id);

        if (book != null) {
            System.out.println("\nBook Found!");
            book.displayBook();
        } else {
            System.out.println("Book with ID " + id + " not found.");
        }
    }

    // Search Book by Title
    public static void searchBookByTitle() {

        System.out.println("\n---------- SEARCH BOOK BY TITLE ----------");

        System.out.print("Enter book title: ");
        String title = scanner.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        boolean found = false;

        for (Book book : books) {

            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                book.displayBook();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No book found with title: " + title);
        }
    }

    // Issue Book
    public static void issueBook() {

        System.out.println("\n---------- ISSUE BOOK ----------");

        int id = readInt("Enter Book ID to issue: ");

        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book with ID " + id + " not found.");
            return;
        }

        if (book.isIssued()) {
            System.out.println("This book is already issued.");
            return;
        }

        book.issueBook();

        System.out.println("Book issued successfully!");
        System.out.println("Book: " + book.getTitle());
    }

    // Return Book
    public static void returnBook() {

        System.out.println("\n---------- RETURN BOOK ----------");

        int id = readInt("Enter Book ID to return: ");

        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book with ID " + id + " not found.");
            return;
        }

        if (!book.isIssued()) {
            System.out.println("This book is already available.");
            return;
        }

        book.returnBook();

        System.out.println("Book returned successfully!");
        System.out.println("Book: " + book.getTitle());
    }

    // Remove Book
    public static void removeBook() {

        System.out.println("\n---------- REMOVE BOOK ----------");

        int id = readInt("Enter Book ID to remove: ");

        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book with ID " + id + " not found.");
            return;
        }

        if (book.isIssued()) {
            System.out.println("Cannot remove an issued book.");
            return;
        }

        books.remove(book);

        System.out.println("Book removed successfully!");
        System.out.println("Removed Book: " + book.getTitle());
    }

    // Find Book by ID
    public static Book findBookById(int id) {

        for (Book book : books) {

            if (book.getBookId() == id) {
                return book;
            }
        }

        return null;
    }

    // Read Integer Safely
    public static int readInt(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
}