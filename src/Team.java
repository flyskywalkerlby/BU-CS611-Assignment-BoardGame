/*
* Team
* contain players
* */


import java.util.ArrayList;

public class Team {

    private String name;
    public void set_name(String name) {
        this.name = name;
    }
    public String get_name() {
        return this.name;
    }

    private final Performance performance;
    public Performance get_performance() {
        return this.performance;
    }
    public void update_performance(char res) {
        this.performance.update(res);
    }

    private final ArrayList<Player> players;
    ArrayList<Player> get_players() {
        return this.players;
    }
    int get_num_members() {
        return this.players.size();
    }

    Team() {
        this.players = new ArrayList<>();
        this.player_idx = -1;
        this.performance = new Performance();
    }

    void add_player(String name, String color, Team team) {
        this.players.add(new Player(name, color, team));
    }

    void add_player(Player player) {
        this.players.add(player);
    }

    void set_first(boolean first) {
        for (Player player: this.players) {
            player.set_first_player(first);
        }
    }

    private int player_idx;
    Player next_player() {
        this.player_idx = (this.player_idx + 1) % this.get_num_members();
        Player player = this.players.get(this.player_idx);
        System.out.println(player + " comes on stage !!!");
        return player;
    }
}
