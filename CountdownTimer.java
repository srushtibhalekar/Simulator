
import java.util.Scanner;

public class CountdownTimer {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("==============================");
        System.out.println("     COUNTDOWN TIMER");
        System.out.println("==============================");

        System.out.print("Enter countdown seconds: ");
        int seconds = sc.nextInt();

        if (seconds <= 0) {
            System.out.println("❌ Enter a positive number.");
            sc.close();
            return;
        }

        System.out.println("\n⏳ Countdown started...");

        for (int i = seconds; i > 0; i--) {

            System.out.println("Time remaining: " + i + " seconds");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Timer interrupted.");
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("\n⏰ TIME'S UP!");

        sc.close();
    }
}

