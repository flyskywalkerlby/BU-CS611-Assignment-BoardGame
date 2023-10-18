/*
* extends BaseJudge
* Judge for OC
* Different processing when the board has no empty space
* based on whether a player is first_player
* */


public class JudgeOC extends BaseJudge{

    public char judge(Board board, int[] pos, int win_len, char logo, char[] possible_logos,
                         Player player, Player opponent, boolean inner) {

        Player winner, loser;

        // order win
        if (judge_win(board, pos, win_len)) {
            if (player.check_first_player()) {
                winner = player;
                loser = opponent;
            }
            else {
                if (!opponent.check_first_player())
                    System.err.println("Impossible chaos logo!");
                System.out.println("Help opponent win???");
                winner = opponent;
                loser = player;
            }
            System.out.printf("%s is the order player\n", winner.get_name());
        }
        // chaos win
        else if (judge_empty(board)) {
            if (player.check_first_player()) {
                winner = opponent;
                loser = player;
            }
            else {
                winner = player;
                loser = opponent;
            }
            System.out.printf("Game over!!! Since no empty space.\n %s is the chaos player\n", winner);
        }
        else
            return ' ';

        if (!inner) {
            this.congratulate_winner(winner.get_name());
            this.update_performance(winner, loser, false);
        }

        return 'w';
    }
}
