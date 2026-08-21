import java.util.Random;

public class DiceSimulator {

    public static void main(String[] args) {

        Random random = new Random();

        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;

        int total = dice1 + dice2;

        System.out.println("🎲 Dice 1: " + dice1);
        System.out.println("🎲 Dice 2: " + dice2);
        System.out.println("Total: " + total);
    }
}