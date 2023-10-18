/*
* Color
* used for colorful print
* is the instance of Piece and Player
* All static methods
* */

public class Color {
    // Color escape codes
    public static final String RESET = "\u001B[0m";
//    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    private static final String[] colors_pool = {"RED", "GREEN", "YELLOW", "BLUE", "PURPLE", "CYAN", "WHITE"};

    public static void printColorsPool() {
//        System.out.println("Colors for you: " + Arrays.toString(colors_pool));
        System.out.print("Colors for you: ");
        for (String color : colors_pool) {
            print_color(color);
            System.out.print(" ");
        }
        System.out.println(RESET); // 重置颜色至默认颜色
    }

    public static void print_color(String color) {
        String color_code = get_color_code(color);
        System.out.print(color_code + color + RESET);
    }

    public static String get_color_from_index(int idx) {
        return colors_pool[idx];
    }

    public static void select_color() {
        printColorsPool();
        System.out.println("You can choose by entering either uppercase or lowercase, the full name or the initial capital letter.");
    }
    public static void color_print(String message, String color) {
        System.out.print(get_message_with_color(message, color));
    }

    public static void color_print(char message, String color) {
        System.out.print(get_message_with_color(message, color));
    }

    // Used for define the toString of Piece
    public static String get_message_with_color(String message, String color) {
        String color_code = get_color_code(color.toUpperCase());
        if (color_code == null) {
            throw new IllegalArgumentException("Unsupported color");
        }
        return color_code + message + RESET;
    }

    public static String get_message_with_color(char message, String color) {
        String color_code = get_color_code(color.toUpperCase());
        if (color_code == null) {
            throw new IllegalArgumentException("Unsupported color");
        }
        return color_code + message + RESET;
    }

    public static String get_color_name(String color) {
        color = color.toUpperCase();
        switch (color) {
            // case "BLACK":
            // case "B":
            //     return "black";
            case "RED":
            case "R":
                return "red";
            case "GREEN":
            case "G":
                return "green";
            case "YELLOW":
            case "Y":
                return "yellow";
            case "BLUE":
            case "B":
                return "blue";
            case "PURPLE":
            case "P":
                return "purple";
            case "CYAN":
            case "C":
                return "cyan";
            case "WHITE":
            case "W":
                return "white";
            default:
                return "WRONG";
        }
    }


    private static String get_color_code(String color) {
        color = color.toUpperCase();
        switch (color) {
//            case "BLACK":
//            case "B":
//                return BLACK;
            case "RED":
            case "R":
                return RED;
            case "GREEN":
            case "G":
                return GREEN;
            case "YELLOW":
            case "Y":
                return YELLOW;
            case "BLUE":
            case "B":
                return BLUE;
            case "PURPLE":
            case "P":
                return PURPLE;
            case "CYAN":
            case "C":
                return CYAN;
            case "WHITE":
            case "W":
                return WHITE;
            case "DEFAULT":
                return RESET;
            default:
                return null;
        }
    }

}


