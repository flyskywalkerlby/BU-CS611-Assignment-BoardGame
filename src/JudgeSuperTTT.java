/*
 * extends BaseJudge
 * Judge for SuperTTT
 * a winning occurs when there are win_len(default=3) continuous pieces, which have same symbols or at most 1 stalemate.
 * */


import java.util.HashMap;

public class JudgeSuperTTT extends BaseJudge{
    public char judge(Board board, int[] pos, int win_len, char player_symbol, char[] possible_logos,
                         Player player, Player opponent, boolean inner) {

        char win_symbol = judge_win_considering_T(board, pos, win_len);
        if (win_symbol != ' ') {
            if (!inner) {

                if (win_symbol == player_symbol) {
                    this.congratulate_winner(player.get_name());
                    this.update_performance(player, opponent, false);
                }
                else {
                    this.congratulate_winner(opponent.get_name());
                    this.update_performance(opponent, player, false);
                }
            }
            return 'w';
        }
        if (judge_stalemate(board)) {
            if (!inner) {
                this.broadcast_stalemate();
                this.update_performance(player, opponent, true);
            }
            return 's';
        }
        return ' ';
    }

    public boolean judge_stalemate(Board board) {
        if (judge_empty(board))
            return true;
        return false;
    }

    // Judge whether there's a winning appearing
    // allow at most 1 stalemate
    public char judge_win_considering_T(Board board, int[] pos, int win_len) {
        // ' ' is included to judge the possible of win, in order to judge the stalemate.

        char symbol = board.get_symbol(pos[0], pos[1]);
        
        boolean over;
        if (symbol == 'T') {
            for (char s : new char[]{'X', 'O'}) {
                over = this._judge_win_or_possible_win(board, pos, win_len, s, 1);
                if (over)
                    return s;
            }
            return ' ';
        }
        else {
            over = _judge_win_or_possible_win(board, pos, win_len, symbol, 0);
            if (over)
                return symbol;
            else
                return ' ';
        }
    }

    // use init_stale_n to consider situation where the new symbol is a stalemate
    private boolean _judge_win_or_possible_win(Board board, int[] pos, int win_len, char symbol, int init_stale_n) {

        int width = board.get_width();
        int height = board.get_height();
        int r = pos[0];
        int c = pos[1];

        HashMap<String, Integer> dir2offset = new HashMap<>();
        int offset;
        char temp;
        int stale_n;

        // right
        offset = 1;
        stale_n = init_stale_n;
        while (c + offset < width) {
            temp = board.get_symbol(r, c + offset);
            if (temp == '?')
                stale_n ++;
            if (temp == symbol && stale_n <= 1) {
                offset += 1;
                if (offset >= win_len)
                    return true;
            } else {
                break;
            }
        }
        dir2offset.put("r", offset - 1);

        // upper right
        offset = 1;
        stale_n = init_stale_n;
        while (r - offset >= 0 && c + offset < width) {
            temp = board.get_symbol(r - offset, c + offset);
            if (temp == '?')
                stale_n ++;
            if (temp == symbol && stale_n <= 1) {
                offset += 1;
                if (offset >= win_len)
                    return true;
            } else {
                break;
            }
        }
        dir2offset.put("ur", offset - 1);

        // upper
        offset = 1;
        stale_n = init_stale_n;
        while (r - offset >= 0) {
            temp = board.get_symbol(r - offset, c);
            if (temp == '?')
                stale_n ++;
            if (temp == symbol && stale_n <= 1) {
                offset += 1;
                if (offset >= win_len)
                    return true;
            } else {
                break;
            }
        }
        dir2offset.put("u", offset - 1);

        // upper left
        offset = 1;
        stale_n = init_stale_n;
        while (r - offset >= 0 && c - offset >= 0) {
            temp = board.get_symbol(r - offset, c - offset);
            if (temp == '?')
                stale_n ++;
            if (temp == symbol && stale_n <= 1) {
                offset += 1;
                if (offset >= win_len)
                    return true;
            } else {
                break;
            }
        }
        dir2offset.put("ul", offset - 1);

        // left
        offset = 1;
        stale_n = init_stale_n;
        while (c - offset >= 0) {
            temp = board.get_symbol(r, c - offset);
            if (temp == '?')
                stale_n ++;
            if (temp == symbol && stale_n <= 1) {
                offset += 1;
                if (offset >= win_len)
                    return true;
            } else {
                break;
            }
        }
        dir2offset.put("l", offset - 1);

        // down left
        offset = 1;
        stale_n = init_stale_n;
        while (r + offset < height && c - offset >= 0) {
            temp = board.get_symbol(r + offset, c - offset);
            if (temp == '?')
                stale_n ++;
            if (temp == symbol && stale_n <= 1) {
                offset += 1;
                if (offset >= win_len)
                    return true;
            } else {
                break;
            }
        }
        dir2offset.put("dl", offset - 1);

        // down
        offset = 1;
        stale_n = init_stale_n;
        while (r + offset < height) {
            temp = board.get_symbol(r + offset, c);
            if (temp == '?')
                stale_n ++;
            if (temp == symbol && stale_n <= 1) {
                offset += 1;
                if (offset >= win_len)
                    return true;
            } else {
                break;
            }
        }
        dir2offset.put("d", offset - 1);

        // down right
        offset = 1;
        stale_n = init_stale_n;
        while (r + offset < height && c + offset < width) {
            temp = board.get_symbol(r + offset, c + offset);
            if (temp == '?')
                stale_n ++;
            if (temp == symbol && stale_n <= 1) {
                offset += 1;
                if (offset >= win_len)
                    return true;
            } else {
                break;
            }
        }
        dir2offset.put("dr", offset - 1);

        // l - r
        if (dir2offset.get("l") + dir2offset.get("r") + 1 >= win_len)
            return true;

        // ur - dl
        if (dir2offset.get("ur") + dir2offset.get("dl") + 1 >= win_len)
            return true;

        // u - d
        if (dir2offset.get("u") + dir2offset.get("d") + 1 >= win_len)
            return true;

        // ul - dr
        if (dir2offset.get("ul") + dir2offset.get("dr") + 1 >= win_len)
            return true;

        return false;
    }
}
