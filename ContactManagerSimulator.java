import java.util.ArrayList;
import java.util.Scanner;

class Contact {

    String name;
    String phone;

    Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}

public class ContactManagerSimulator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Contact> contacts = new ArrayList<>();

        System.out.println("================================");
        System.out.println("     📱 CONTACT MANAGER");
        System.out.println("================================");

        while (true) {

            System.out.println("\n===== CONTACT MENU =====");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Search Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter phone number: ");
                    String phone = sc.nextLine();

                    contacts.add(new Contact(name, phone));

                    System.out.println("✅ Contact added successfully!");
                    break;

                case 2:

                    if (contacts.isEmpty()) {
                        System.out.println("📭 No contacts available.");
                    } else {

                        System.out.println("\n📱 Contact List:");

                        for (int i = 0; i < contacts.size(); i++) {
                            Contact c = contacts.get(i);

                            System.out.println(
                                    (i + 1) + ". " +
                                    c.name + " - " + c.phone
                            );
                        }
                    }
                    break;

                case 3:

                    System.out.print("Enter name to search: ");
                    String search = sc.nextLine();

                    boolean found = false;

                    for (Contact c : contacts) {

                        if (c.name.equalsIgnoreCase(search)) {

                            System.out.println("\n✅ Contact Found");
                            System.out.println("Name: " + c.name);
                            System.out.println("Phone: " + c.phone);

                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("❌ Contact not found.");
                    }

                    break;

                case 4:

                    System.out.print("Enter name to delete: ");
                    String deleteName = sc.nextLine();

                    boolean deleted = false;

                    for (int i = 0; i < contacts.size(); i++) {

                        if (contacts.get(i).name.equalsIgnoreCase(deleteName)) {

                            contacts.remove(i);

                            System.out.println("🗑️ Contact deleted.");
                            deleted = true;
                            break;
                        }
                    }

                    if (!deleted) {
                        System.out.println("❌ Contact not found.");
                    }

                    break;

                case 5:

                    System.out.println("\n📱 Contact Manager Closed.");
                    sc.close();
                    return;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
}