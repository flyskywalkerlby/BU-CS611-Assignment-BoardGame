/*
* base judge extends different judges for different games
* */


import java.util.HashMap;

public abstract class BaseJudge {

    public void update_performance(Player winner, Player loser, boolean if_stalemate) {
        if (if_stalemate) {
            winner.update_performance('s');
            winner.get_team().update_performance('s');
            loser.update_performance('s');
            loser.get_team().update_performance('s');
        }
        else {
            winner.get_team().update_performance('w');
            winner.update_performance('w');
            loser.get_team().update_performance('l');
            loser.update_performance('l');
        }
    }

    public void congratulate_winner(String name) {
        System.out.printf("Congratulation %s! You win!!!\n", name);
    }

    public void broadcast_stalemate() {
        System.out.println("Stalemate.");
    }

    public abstract char judge(Board board, int[] pos, int win_len,
                               char logo, char[] possible_logos, Player player, Player opponent, boolean inner);

    public boolean judge_win(Board board, int[] pos, int win_len) {
        return this.judge_win_or_possible_win(board, pos, win_len, false, ' ');
    }

    // judge whether this board is empty by the step and w and h
    public boolean judge_empty(Board board) {
        int width = board.get_width();
        int height = board.get_height();

        int n_step = board.get_n_step();
        if (n_step == width * height) {
            System.out.println("[STATE] No empty space on board.");
            return true;
        }
        return false;
    }

    // Judge whether there's a winning appearing
    // or Judge whether there can be a winning in the future
    // controlled by possible
    public boolean judge_win_or_possible_win(Board board, int[] pos, int win_len, boolean possible, char symbol) {
        // ' ' is included to judge the possible of win, in order to judge the stalemate.

        int width = board.get_width();
        int height = board.get_height();
        int r = pos[0];
        int c = pos[1];

        if (!possible)
            symbol = board.get_symbol(r, c);

        HashMap<String, Integer> dir2offset = new HashMap<>();
        int offset;
        char temp;

        // right
        offset = 1;
        while (c + offset < width) {
            temp = board.get_symbol(r, c + offset);
            if (temp == symbol || (temp == ' ' && possible)) {
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
        while (r - offset >= 0 && c + offset < width) {
            temp = board.get_symbol(r - offset, c + offset);
            if (temp == symbol || (temp == ' ' && possible)) {
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
        while (r - offset >= 0) {
            temp = board.get_symbol(r - offset, c);
            if (temp == symbol || (temp == ' ' && possible)) {
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
        while (r - offset >= 0 && c - offset >= 0) {
            temp = board.get_symbol(r - offset, c - offset);
            if (temp == symbol || (temp == ' ' && possible)) {
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
        while (c - offset >= 0) {
            temp = board.get_symbol(r, c - offset);
            if (temp == symbol || (temp == ' ' && possible)) {
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
        while (r + offset < height && c - offset >= 0) {
            temp = board.get_symbol(r + offset, c - offset);
            if (temp == symbol || (temp == ' ' && possible)) {
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
        while (r + offset < height) {
            temp = board.get_symbol(r + offset, c);
            if (temp == symbol || (temp == ' ' && possible)) {
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
        while (r + offset < height && c + offset < width) {
            temp = board.get_symbol(r + offset, c + offset);
            if (temp == symbol || (temp == ' ' && possible)) {
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
