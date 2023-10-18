/*
* win_len is used as the destination row in QRD
* Implement it since we need to update performance here, can be optimized
* */

public class JudgeQRD extends BaseJudge{

    @Override
    public char judge(Board board, int[] pos, int win_len, char logo, char[] possible_logos,
                      Player player, Player opponent, boolean inner) {

        // win_len means the row player needs to reach to win
        if (pos[0] == win_len) {
            if (!inner) {
                this.congratulate_winner(player.get_name());
                this.update_performance(player, opponent, false);
            }
            return 'w';
        }
        return ' ';
    }
}
