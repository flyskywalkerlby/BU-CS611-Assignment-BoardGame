/*
 * TTT extends BaseGame
 * */


public class TTT extends BaseGame{

    TTT() {
        super(
            0,
            "TTT",
            "Tic Tac Toe",
            3,
            3,
            3,
            true,
            false
        );
    }

    public void init() {
        int[] wh = this.get_board_width_height(default_width, default_height);
        int w = wh[0];
        int h = wh[1];
        this.board.init(wh[0], wh[1]);

        for (int r=0; r<h; r++) {
            for (int c=0; c<w; c++) {
                this.board.set(r, c, new TilePiece());
            }
        }
    }

    // choose symbol ->
    public boolean step(Player player, Player opponent) {
        char player_symbol = this.get_input_symbol();
        char res = step_return_res(player, opponent, player_symbol);
        if (res == ' ')
            return false;
        else
            return true;
    }

    // -> get_pos -> set symbol and color -> print board -> judge
    public char step_return_res(Player player, Player opponent, char player_symbol) {
        int[] pos = this.get_input_pos();
        this.forecast_state(player, player_symbol);
        this.board.set_symbol(pos[0], pos[1], player_symbol);
        this.board.set_color(pos[0], pos[1], player.get_color());
        this.print_board();
        return this.judge.judge(this.board, pos, win_len,
                player_symbol, possible_symbols, player, opponent, false);
    }
}
