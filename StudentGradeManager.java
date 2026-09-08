import java.util.HashMap;
import java.util.Scanner;

class Student {

    int rollNo;
    String name;
    double marks;

    Student(int rollNo, String name, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
    }

    void display() {
        System.out.println(
                "Roll No: " + rollNo +
                " | Name: " + name +
                " | Marks: " + marks
        );
    }
}

public class StudentGradeManager {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        HashMap<Integer, Student> students = new HashMap<>();

        System.out.println("================================");
        System.out.println("     🎓 STUDENT GRADE MANAGER");
        System.out.println("================================");

        while (true) {

            System.out.println("\n===== STUDENT MENU =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Calculate Average");
            System.out.println("5. Find Topper");
            System.out.println("6. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter roll number: ");
                    int rollNo = sc.nextInt();
                    sc.nextLine();

                    if (students.containsKey(rollNo)) {
                        System.out.println("❌ Roll number already exists!");
                        break;
                    }

                    System.out.print("Enter student name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter marks: ");
                    double marks = sc.nextDouble();

                    if (marks < 0 || marks > 100) {
                        System.out.println("❌ Marks must be between 0 and 100.");
                        break;
                    }

                    Student student = new Student(rollNo, name, marks);

                    students.put(rollNo, student);

                    System.out.println("✅ Student added successfully!");

                    break;

                case 2:

                    if (students.isEmpty()) {
                        System.out.println("📭 No students available.");
                    } else {

                        System.out.println("\n===== STUDENT LIST =====");

                        for (Student s : students.values()) {
                            s.display();
                        }
                    }

                    break;

                case 3:

                    System.out.print("Enter roll number to search: ");
                    int searchRoll = sc.nextInt();

                    Student foundStudent = students.get(searchRoll);

                    if (foundStudent != null) {

                        System.out.println("\n✅ Student Found");
                        foundStudent.display();

                    } else {
                        System.out.println("❌ Student not found.");
                    }

                    break;

                case 4:

                    if (students.isEmpty()) {
                        System.out.println("📭 No marks available.");
                        break;
                    }

                    double total = 0;

                    for (Student s : students.values()) {
                        total += s.marks;
                    }

                    double average = total / students.size();

                    System.out.printf(
                            "\n📊 Average Marks: %.2f%n",
                            average
                    );

                    break;

                case 5:

                    if (students.isEmpty()) {
                        System.out.println("📭 No students available.");
                        break;
                    }

                    Student topper = null;

                    for (Student s : students.values()) {

                        if (topper == null || s.marks > topper.marks) {
                            topper = s;
                        }
                    }

                    System.out.println("\n🏆 TOPPER");
                    topper.display();

                    break;

                case 6:

                    System.out.println("\n================================");
                    System.out.println("    🎓 GRADE MANAGER CLOSED");
                    System.out.println("================================");

                    sc.close();
                    return;

                default:

                    System.out.println("❌ Invalid choice!");
            }
        }
    }
}