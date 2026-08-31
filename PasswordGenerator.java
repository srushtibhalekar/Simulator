
import java.util.Random;
import java.util.Scanner;

public class PasswordGenerator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        String characters =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "0123456789" +
                "!@#$%^&*";

        System.out.println("==============================");
        System.out.println("    PASSWORD GENERATOR");
        System.out.println("==============================");

        System.out.print("Enter password length: ");
        int length = sc.nextInt();

        if (length < 4) {
            System.out.println("❌ Password length must be at least 4.");
            sc.close();
            return;
        }

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        System.out.println("\n🔐 Generated Password:");
        System.out.println(password);

        sc.close();
    }
}

