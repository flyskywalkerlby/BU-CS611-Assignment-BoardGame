/*
* Base game extends other games
* constructor by game configs
* some basic game input and output
* */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseGame {

    static String COLOR_INDEX = "YELLOW";
    static String COLOR_STATE = "BLUE";
    static String COLOR_INSTR = "GREEN";

    protected final JudgeFactoryInterface judge_factory;
    protected final BaseJudge judge;
    protected final InputScanner input_scanner;
    protected Board board;
    public Board get_board() {
        return this.board;
    }

    protected final int game_id;
    protected final String game_key;
    protected final String game_name;
    protected int default_width;
    protected int default_height;
    protected final int win_len;
    protected final boolean extendable;
    protected final boolean can_choose_symbol;

    static char[] possible_symbols = {'X', 'O'};

    protected int symbol_idx = -1;

    // players use different symbols in turn
    public char step_symbol_idx() {
        this.symbol_idx = (this.symbol_idx + 1) % possible_symbols.length;
        return possible_symbols[this.symbol_idx];
    }

    BaseGame(int game_id, String game_key, String game_name,
             int default_width, int default_height, int win_len,
             boolean extendable, boolean can_choose_symbol) {

        this.input_scanner = new InputScanner();
        this.board = new Board();
        this.judge_factory = new JudgeFactory();

        this.judge = this.judge_factory.createJudge(game_key);

        this.game_id = game_id;
        this.game_key = game_key;
        this.game_name = game_name;
        this.default_width = default_width;
        this.default_height = default_height;
        this.win_len = win_len;
        this.extendable = extendable;
        this.can_choose_symbol = can_choose_symbol;
    }

    public String add_double_slash_line(String messages) {
        return "==================== " + messages + " ====================";
    }

    public void print_state_line() {
        System.out.println(Color.get_message_with_color(add_double_slash_line("STATE"), COLOR_STATE));
    }

    public void print_instr_line() {
        System.out.println(Color.get_message_with_color(add_double_slash_line("Instr"), COLOR_INSTR));
    }

    public void print_prefix(String messages) {
        System.out.printf("[%s] ", messages);
    }

    public void print_warning(String messages) {
        print_prefix("WARNING");
        System.out.println(messages);
    }


    @Override
    public String toString() {
        return "BaseGame{" +
                "game_id=" + game_id +
                ", game_key='" + game_key + '\'' +
                ", game_name='" + game_name + '\'' +
                ", default_width=" + default_width +
                ", default_height=" + default_height +
                ", win_len=" + win_len +
                ", extendable=" + extendable +
                ", can_choose_symbol=" + can_choose_symbol +
                '}';
    }

    public int[] get_board_width_height(int w, int h) {
        print_instr_line();
        System.out.printf("The default size of the board is [%d×%d]\n", w, h);
        if (this.extendable) {
            System.out.println("Do you want to set the board size?\nType 'y' if you want to.");
            print_instr_line();
            String choice = this.input_scanner.input_string();
            if (choice.contains("y")) {
                System.out.println("Please input width and height");
                System.out.print("Width: ");
                w = this.input_scanner.input_int();
                System.out.print("Height: ");
                h = this.input_scanner.input_int();
            }
        }
        else
            print_instr_line();
        return new int[]{w, h};
    }

    public abstract void init();

    // print board
    public void print_board() {
        this.board.print_board();
    }

    // get pos input, allow one or two ints, allow different input styles
    public int[] get_input_pos() {
        System.out.println("Please input 1 or 2 integers (index or position).");
        String input_content;
        while (true) {
            input_content = this.input_scanner.input_string();

            if (input_content.contains("-")) {
                System.out.println("***********************\n" +
                        "[Attention] We do not support negative number !!!\n" +
                        "***********************");
            }

            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(input_content);

            // Number Input

            int count = 0;
            int[] pos = new int[2];

            while (matcher.find()) {
                if (count == 2) {
                    count = 3;
                    break;
                }
                int number = Integer.parseInt(matcher.group());
                pos[count] = number;
                count ++;
            }

            if (count == 1 || count == 2) {
                if (count == 1) {
                    pos[1] = pos[0] % this.board.get_width();
                    pos[0] = pos[0] / this.board.get_width();
                }
                if(board.valid_pos(pos)) {
                    System.out.printf("You placed at (%d, %d)\n", pos[0], pos[1]);
                    return pos;
                }
            } else {
                System.out.println(("[WARNING] Please input 1 or 2 integers (index or position)!!!"));
            }
        }
    }

    // user choose symbol
    public char get_input_symbol() {
        char symbol;
        if (this.can_choose_symbol) {
            while (true) {
                System.out.println("Please choose either X(x) or O(o)");
                symbol = Character.toUpperCase(this.input_scanner.input_char());
                if (symbol == 'X' || symbol == 'O')
                    break;
                else
                    System.out.println("Please choose either X or O !!");
            }
        }
        else
            symbol = step_symbol_idx();
        return symbol;
    }

    void forecast_state(Player player, char player_symbol) {
        print_state_line();
        System.out.printf("<%s> | Your Turn (%d).\n", player.get_name(), this.board.get_n_step() + 1);
        System.out.printf("The symbol you use is %s.\n", Color.get_message_with_color(player_symbol, player.get_color()));
        print_state_line();
    }

    // ============================= turn control
    public abstract boolean step(Player player, Player opponent);
}
