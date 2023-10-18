/*
* based on a scanner
* input int, char, string
* if find "exit" or "quit" (including Uppercase), system will exit
* */

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InputScanner {

    private final Scanner scanner;

    InputScanner() {
        this.scanner = new Scanner(System.in);
    }

    public int input_int() {
        while (true) {
            String input = scanner.nextLine();
            check_quit(input);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("[WARNING] Please enter a valid number!");
            }
        }
    }
    
    public int[] input_n_none_negative_integers(int n) {
        String input_content;
        int[] integers;

        while (true) {

            input_content = this.input_string();

            integers = parse_n_none_negative_integers(input_content, n);
            if (integers != null)
                return integers;
            else
                System.out.printf("[WARNING] Please input %d integers!!!\n", n);
        }
    }

    public int[] parse_n_none_negative_integers(String input_content, int n) {
        int count = 0;
        int[] integers = new int[n];
        if (input_content.contains("-")) {
            System.out.println("***********************\n" +
                    "[Attention] We do not support negative number !!!\n" +
                    "***********************");
        }

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input_content);

        // Number Input

        while (matcher.find()) {
            if (count == n) {
                count = n + 1;
                break;
            }
            int number = Integer.parseInt(matcher.group());
            integers[count] = number;
            count ++;
        }

        if (count == n) {
            return integers;
        } else {
            return null;
        }
    }

    public char input_char() {
        String temp = this.input_string();
        check_quit(temp);
        while (true) {
            if (Objects.equals(temp, "")) {
                System.out.println("[WARNING] Input Required!!!");
                temp = this.input_string();
                continue;
            }
            return temp.charAt(0);
        }
    }

    public String input_string() {
        String input = this.scanner.nextLine();
        check_quit(input);
        return input;
    }

    public static void check_quit(String input) {
        if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit"))
            System.exit(0);
    }
}
