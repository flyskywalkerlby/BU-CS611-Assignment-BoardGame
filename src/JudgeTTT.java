/*
 * extends BaseJudge
 * Judge for TTT
 * Considering stalemate
 * announce stalemate in advance
 * */


public class JudgeTTT extends BaseJudge{

    // true: over; false: going on
    public char judge(Board board, int[] pos, int win_len, char logo, char[] possible_logos,
                         Player player, Player opponent, boolean inner) {
        if (judge_win(board, pos, win_len)) {
            if (!inner) {
                this.congratulate_winner(player.get_name());
                this.update_performance(player, opponent, false);
            }
            return 'w';
        }
        if (judge_stalemate(board, win_len, possible_logos)) {
            if (!inner) {
                this.broadcast_stalemate();
                this.update_performance(player, opponent, true);
            }
            return 's';
        }
        return ' ';
    }

    public boolean judge_stalemate(Board board, int win_len, char[] possible_logos) {

        if (judge_empty(board))
            return true;

        int width = board.get_width();
        int height = board.get_height();

        boolean one_pos_possible_win;
        int[] pos = new int[2];
        for (int r=0; r<height; r++) {
            for(int c=0; c<width; c++) {
                if (board.get_symbol(r, c) == ' ') {
                    pos[0] = r;
                    pos[1] = c;
                    for (char logo : possible_logos) {
//                        System.out.printf("(%d, %d) | %c\n", r, c, logo);
                        one_pos_possible_win = this.judge_win_or_possible_win(board, pos, win_len, true, logo);
                        if (one_pos_possible_win)
                            return false;
                    }
                }
            }
        }
        return true;
    }
}
