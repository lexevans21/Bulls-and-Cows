package bullscows;

import java.util.Scanner;
import java.util.Random;

public class Main {

    char[] secretCode;

    void run() {
        int[] setUpValues = getPlayerInputForSecretCode();
        boolean gameIsPlayable = true;

        if (setUpValues == null) {
            gameIsPlayable = false;
        } else {
            secretCode = generateSecretCode(setUpValues);
        }

        if (secretCode == null) {
            gameIsPlayable = false;
        } else {
            System.out.println("Let's begin!");
        }
        if (!gameIsPlayable) {
            System.out.println("Error, nerd.");
        } else {
            boolean gameOver = playGame();

            if (gameOver) {
                System.out.println("Congratulations! The secret code was: " + String.valueOf(secretCode));
            }
        }
    }

    boolean playGame() {
        int[] grade;
        int turnNumber = 1;
        do {
            System.out.printf("Turn %d: ", turnNumber);
            grade = grader(getPlayerGuess());
            turnNumber++;
            if (grade == null) {
                return false;
            }
        } while (grade[0] < secretCode.length);

        return true;
    }


    // returns an int array of size 2, with the number of bulls and cows.
    int[] grader(char[] guess) {

        if (guess == null) {
            return null;
        }

        int[] grade = {0, 0};

        //Calculate grade.
        for (int i = 0; i < secretCode.length; i++) {
            if (guess[i] == secretCode[i]) {
                grade[0]++;
            } else {
                for (int j = i; j < secretCode.length; j++) {
                    if (guess[i] == secretCode[j]) {
                        grade[1]++;
                        break;
                    }
                }
            }
        }

        //Print grade.
        System.out.print("Grade: ");
        if (grade[0] == 0 && grade[1] == 0) {
            System.out.print("None.\n");
        } else if (grade[1] == 0) {
            System.out.print(grade[0] + " bull(s).\n");
        } else if (grade[0] == 0) {
            System.out.print(grade[1] + " cow(s).\n");
        } else {
            System.out.print(grade[0] + " bull(s) and " + grade[1] + " cow(s).\n");
        }

        return grade;
    }

    char[] getPlayerGuess() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        char[] playerGuess = input.toCharArray();

        if (playerGuess.length != secretCode.length) {
            System.out.println("Error, boss! You gotta guess " + secretCode.length + " characters!");
            return null;
        }
        return playerGuess;

    }

    int[] getPlayerInputForSecretCode() {
        Scanner scanner = new Scanner(System.in);
        int length;
        int numOfPossibleCharacters;
        //Ask for secret code length
        System.out.print("Please enter the secret code's length: ");
        try {
             length = scanner.nextInt();
        } catch (Exception e) {
             length = -1;
        }

        //Ask for number of possible characters
        System.out.print("Now, input the number of possible symbols in the code: ");
        try {
             numOfPossibleCharacters = scanner.nextInt();
        } catch (Exception e) {
             numOfPossibleCharacters = -1;
        }
        return new int[]{length, numOfPossibleCharacters};
    }

    char[] generateSecretCode(int[] playerValues) {

        Random random = new Random();


        //Length can't be bigger than 10. Number of possible characters can't be bigger than 36, but must be >= length.
        if (playerValues[0] > 10 || playerValues[0] < 1) {
            System.out.println("Error, yo.");
            return null;
        }
        if (playerValues[1] > 36 || playerValues[1] < 1) {
            System.out.println("Error, my dude.");
            return null;
        }
        if (playerValues[1] < playerValues[0]) {
            System.out.println("Error, friend.");
            return null;
        }

        //Create an array of all possibleCharacters
        char[] possibleChars = new char[playerValues[1]];
        for (int i = 0; i < possibleChars.length; i++) {
            if (i < 10) {
                possibleChars[i] = (char) (i + 48);
            } else {
                possibleChars[i] = (char) (i + 87);
            }
        }

        StringBuilder sb = new StringBuilder();
        //Generate secret code, no repeat chars allowed.
        do {
            char randomChar = possibleChars[random.nextInt(possibleChars.length)];


            //No repeats allowed.
            if (sb.indexOf(Character.toString(randomChar)) != -1) {
                continue;
            }

            if (sb.length() < playerValues[0]) {
                sb.append(randomChar);
            }


        } while (sb.length() < playerValues[0]);

        //Create a StringBuilder to display information about the secret (A "*" for each character in the secret, as well as
        // the possible characters.) Example: "The secret is prepared: ***** (0-9, a-f)."
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < sb.length(); i++) {
            stars.append("*");
        }
        stars.append(" (0-");
        if (playerValues[1] <= 10) {
            stars.append(String.valueOf(possibleChars[possibleChars.length - 1]));
            stars.append(").");
        } else {
            stars.append("9, a-").append(String.valueOf(possibleChars[possibleChars.length - 1]));
        }


        System.out.println("The secret is prepared: " + stars);

        return sb.toString().toCharArray();

    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

}
