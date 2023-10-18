/*
* Pipeline engine
* game factory used to select game
* team -> player -> choose color -> round loop
* */
public class Engine {

    private final GameFactory game_factory;
//    private final JudgeFactory judge_factory;
    private final InputScanner input_scanner;

    static int team_num = 2;
    private final Team[] teams;

    public Engine() {

        this.game_factory = new GameFactory();
//        this.judge_factory = new JudgeFactory();
        this.input_scanner = new InputScanner();

        this.teams = new Team[team_num];
    }

    public void run() {

//        Color color_selector = new Color();
//        color_selector.printColorsPool();
//        System.exit(0);

        int num_team_members;
        String temp_string;
        String team_name;
        String player_name;
        for (int i=0; i<team_num; i++) {
            this.teams[i] = new Team();
            System.out.printf("Welcome Team[%d] !!!\n", i);
            // team name
            temp_string = this.enter_name("team");
            if (temp_string.isEmpty())
                team_name = String.format("Team[%d]", i);
            else
                team_name = temp_string;
            this.teams[i].set_name(team_name);
            // team members
            System.out.println("How many team members?");
            while (true) {
                num_team_members = this.input_scanner.input_int();
                if (num_team_members < 1)
                    System.out.println("[WARNING] At least 1 member !!!");
                else
                    break;
            }
            for (int j=0; j<num_team_members; j++) {
                temp_string = enter_name("nick");
                if (temp_string.isEmpty())
                    player_name = String.format("Player[%d]", j);
                else
                    player_name = temp_string;
                player_name = String.format("%s-%s", team_name, player_name);
                temp_string = select_color(i);
                this.teams[i].add_player(player_name, temp_string, this.teams[i]);
            }
            System.out.println("Hello " + teams[i].get_players() + "!!!");
        }

        int first_team_id = 0;
        Player player1, player2;

        int game_id;
        String game_key;
        BaseGame game;
//        BaseJudge judge;
        while (true) {
            // many rounds
            if (want_to_exit())
                break;
            game_id = this.select_game();
            game_key = this.game_factory.get_game_key(game_id);
            game = this.game_factory.createGame(game_key);
//            judge = this.judge_factory.createJudge(game_key);

            game.init();
            game.print_board();

            this.teams[first_team_id].set_first(true);
            this.teams[1 - first_team_id].set_first(false);

            player1 = this.teams[first_team_id].next_player();
            player2 = this.teams[1 - first_team_id].next_player();

            while (true) {
                // many turns
                if (game.step(player1, player2))
                    break;

                if (game.step(player2, player1))
                    break;
            }

            this.print_performance();

            first_team_id = 1 - first_team_id;
        }
    }

    // user choose to exit every round
    // besides, enter quit or exit at any time can quit
    boolean want_to_exit() {
        System.out.println(
                "Press 'e' if you want to exit." +
                " (or Press any other key to start a new game.)\n" +
                "And you can type <exit> or <quit> (case-insensitive) to exit at any time."
        );
        String temp = this.input_scanner.input_string();
        if (temp.isEmpty())
            return false;
        else {
            if (temp.charAt(0) == 'e')
                return true;
        }
        return false;
    }

    String enter_name(String prefix) {
        System.out.printf("Please enter your %s name (or leave empty to use the default name).\n", prefix);
        return this.input_scanner.input_string();
    }

    int select_game() {

        System.out.print("Games for you! Enter the game_id please.\n");
        for (String game_key : this.game_factory.get_game_keys()) {
            System.out.println(this.game_factory.createGame(game_key));
        }

        int game_id;
        while (true) {
            game_id = this.input_scanner.input_int();

            if (this.game_factory.get_games_pool().containsKey(game_id)) {
                System.out.printf(
                        "Welcome to %s!\n-------------------------%n\n",
                        this.game_factory.get_game_name(game_id)
                );
                break;
            }
            else {
                System.out.println("[WARNING] Please type a valid game id!");
            }
        }

        return game_id;
    }

    String select_color(int idx) {
        Color.select_color();
        String default_color = Color.get_color_from_index(idx);
        System.out.printf("Please choose your color (or leave empty to use the default color %s).\n", default_color);
        String color;
        while (true) {
            color = this.input_scanner.input_string();
            if (color.isEmpty()) {
                color = default_color;
                break;
            }
            else {
                color = Color.get_color_name(color);
                if (!color.equals("WRONG"))
                    break;
            }
            System.out.println("[WARNING] Please enter a valid color or leave it empty.");
        }
        System.out.print("Your color is ");
        Color.print_color(color);
        System.out.println();
        return color;
    }

    void print_performance() {
        System.out.println("\n===================================================");
        for (int i=0; i<team_num; i++) {
            System.out.printf("Team[%d] <%s>\n%s\n", i, this.teams[i].get_name(), this.teams[i].get_performance());
            System.out.println("-------");
            for (Player player: this.teams[i].get_players()) {
                System.out.printf("     <%s>\n      %s\n", player.get_name(), player.get_performance());
                System.out.println("-------");
            }
            System.out.println("===================================================\n");
        }
    }
}
