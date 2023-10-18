/*
 * SuperTTT extends BaseGame
 * change init
 * change print
 * change step
 * like recursion
 * */


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SuperTTT extends BaseGame {

    SuperTTT() {
        super(
                2,
                "SuperTTT",
                "Super Tic Tac Toe",
                3,
                3,
                3,
                true,
                false
        );
    }

    @Override
    public void init() {
        System.out.println("Setting the super board...");
        int[] wh = this.get_board_width_height(default_width, default_height);
        int w = wh[0];
        int h = wh[1];
        this.board.init(wh[0], wh[1]);

        for (int r=0; r<h; r++) {
            for (int c=0; c<w; c++) {
                this.board.set(r, c, new TileTTT());
                System.out.printf("Setting the {pos = (%d, %d), index = %d board...\n",
                        r, c, r * this.board.get_width() + c);
                this.board.get(r, c).init();
            }
        }
    }

    private TTT get_inner_TTT(int r, int c) {
        return (
            (TileTTT)this.board.get(r, c).get_tile_object()
        ).getTTT();
    }

    private Board get_inner_board(int r, int c) {
        return this.get_inner_TTT(r, c).get_board();
    }

    // better get_input_pos
    // allow letter input
    private int[] position_super_board() {
        String input_content;
        while (true) {
            System.out.println("\nPlease choose the SuperBoard (letter or index or position).");
            input_content = this.input_scanner.input_string();

            if (input_content.isEmpty())
                continue;

            if (input_content.contains("-")) {
                System.out.println("***********************\n" +
                        "[Attention] We do not support negative number !!!\n" +
                        "***********************");
            }

            int[] pos = new int[2];

            if (Character.isLetter(input_content.charAt(0))) {
                char ch = Character.toUpperCase(input_content.charAt(0));
                pos[1] = (ch - 'A') % this.board.get_width();
                pos[0] = (ch - 'A') / this.board.get_width();
            }

            else {
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(input_content);

                // Number Input

                int count = 0;

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
                } else {
                    continue;
                }
            }

            if(board.valid_pos(pos)) {
                System.out.printf("You placed at (%d, %d)\n", pos[0], pos[1]);
                return pos;
            }
        }
    }

    // choose symbol -> choose inner board -> inner board.step() -> step according to the inner res -> then super step
    @Override
    public boolean step(Player player, Player opponent) {
        char player_symbol = this.get_input_symbol();

        this.forecast_state(player, player_symbol);
        int[] pos_super_board = position_super_board();

        TTT goal_ttt = get_inner_TTT(pos_super_board[0], pos_super_board[1]);
        goal_ttt.print_board();

        char inner_res = goal_ttt.step_return_res(player, opponent, player_symbol);

//        System.out.println("Inner res" + inner_res);

        if (inner_res == 'w') {
            // goal_board over
            this.board.set_symbol(pos_super_board[0], pos_super_board[1], player_symbol);
        }
        else if (inner_res == 's') {
            // stalemate
            this.board.set_symbol(pos_super_board[0], pos_super_board[1], 'T');
        }

        this.print_board();

        if (inner_res == ' ')
            // no update
            return false;
        else {
            char res = this.judge.judge(this.board, pos_super_board, win_len,
                    player_symbol, possible_symbols, player, opponent, false);

            if (res == ' ')
                return false;
            else
                return true;
        }
    }

    public void print_super_board(int w, int h) {
        int index;
        char pos_letter;
        for (int r=0; r<h; r++) {
            this.board._print_tiles_split_row(w);
            for (int c=0; c<w; c++) {
                index = w * r + c;
                if (index < 26 && this.board.empty_pos(r, c)) {
                    pos_letter = (char)('A' + index);
                    System.out.print("| " + Color.get_message_with_color(pos_letter, "WHITE") + ' ');
                }
                else
                    System.out.print("| " + this.board.get(r, c) + ' ');
            }
            // r index
            System.out.println('|' + String.valueOf(r));
        }
        this.board._print_tiles_split_row(w);

        // c index
        for (int c=0; c<w; c++)
            System.out.print("  " + c + " ");
        System.out.println();
    }

    public void print_board() {
        int w = this.board.get_width();
        int h = this.board.get_height();
        System.out.println("\n=====================\nThe Super TTT Board");
        this.print_super_board(w, h);
        System.out.println("=====================");
        Board inner_board;
        for (int r=0; r<h; r++) {
            for (int c=0; c<w; c++) {
                System.out.printf("\n%c  | (%d, %d) Board:\n",
                        (char)('A' + r * this.board.get_width() + c), r, c);
                inner_board = this.get_inner_board(r, c);
                inner_board.print_board();
            }
        }
    }
}
