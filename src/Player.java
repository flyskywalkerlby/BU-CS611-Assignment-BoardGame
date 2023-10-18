/*
* Player
* first_player for OC
* name team color
* */

public class Player {

    private final Team team;
    public Team get_team() {
        return this.team;
    }
    private final String name;
    public String get_name() {
        return this.name;
    }
    private final String color;
    public String get_color() {
        return this.color;
    }

    private final Performance performance;
    public Performance get_performance() {
        return this.performance;
    }
    public void update_performance(char res) {
        this.performance.update(res);
    }

    private boolean first_player;
    public void set_first_player(boolean first) {
        this.first_player = first;
    }
    public boolean check_first_player() {
        return this.first_player;
    }

    Player(String name, String color, Team team) {
        this.name = name;
        this.color = color;
        this.team = team;
        this.performance = new Performance();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
