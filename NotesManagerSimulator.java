import java.io.*;
import java.util.Scanner;

public class NotesManagerSimulator {

    static final String FILE_NAME = "notes.txt";

    public static void addNote(String note) {

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {

            writer.write(note + "\n");
            System.out.println("✅ Note saved successfully!");

        } catch (IOException e) {

            System.out.println("❌ Error saving note.");
        }
    }

    public static void viewNotes() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("📭 No notes found.");
            return;
        }

        System.out.println("\n===== YOUR NOTES =====");

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            int count = 1;

            while ((line = reader.readLine()) != null) {

                System.out.println(count + ". " + line);
                count++;
            }

        } catch (IOException e) {

            System.out.println("❌ Error reading notes.");
        }
    }

    public static void deleteNotes() {

        File file = new File(FILE_NAME);

        if (file.exists() && file.delete()) {

            System.out.println("🗑️ All notes deleted successfully!");

        } else {

            System.out.println("📭 No notes to delete.");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("================================");
        System.out.println("      📝 NOTES MANAGER");
        System.out.println("================================");

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Delete All Notes");
            System.out.println("4. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter your note: ");
                    String note = sc.nextLine();

                    if (note.trim().isEmpty()) {
                        System.out.println("❌ Note cannot be empty.");
                    } else {
                        addNote(note);
                    }
                    break;

                case 2:
                    viewNotes();
                    break;

                case 3:
                    deleteNotes();
                    break;

                case 4:
                    System.out.println("\n📝 Notes Manager Closed.");
                    sc.close();
                    return;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
}