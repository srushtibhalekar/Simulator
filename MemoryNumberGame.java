
import java.util.Random;
import java.util.Scanner;

public class MemoryNumberGame {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int score = 0;

        System.out.println("==============================");
        System.out.println("     🧠 MEMORY NUMBER GAME");
        System.out.println("==============================");

        System.out.println("\nRemember the number!");

        for (int round = 1; round <= 5; round++) {

            int number = random.nextInt(90) + 10;

            System.out.println("\nRound " + round);
            System.out.println("Remember: " + number);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            System.out.println("\n" + "\n".repeat(10));

            System.out.print("Enter the number you remember: ");
            int answer = sc.nextInt();

            if (answer == number) {
                System.out.println("✅ Correct!");
                score++;
            } else {
                System.out.println("❌ Wrong!");
                System.out.println("The number was: " + number);
            }
        }

        System.out.println("\n==============================");
        System.out.println("          GAME OVER");
        System.out.println("==============================");

        System.out.println("Your Score: " + score + "/5");

        if (score == 5) {
            System.out.println("🏆 Perfect Memory!");
        } else if (score >= 3) {
            System.out.println("👏 Good Memory!");
        } else {
            System.out.println("💪 Keep Practicing!");
        }

        sc.close();
    }
}

