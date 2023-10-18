/*
* Performance records
* */

public class Performance {
    private int win_count;
    private int lose_count;
    private int stalemate_count;

    Performance() {
        this.win_count = 0;
        this.lose_count = 0;
        this.stalemate_count = 0;
    }

    public void update(char res) {
        if (res == 'w')
            this.win_count ++;
        else if (res == 'l')
            this.lose_count ++;
        else if (res == 's')
            this.stalemate_count ++;
        else
            System.out.println("Invalid result, only accept ['w', 'l', 's'].");
    }

    @Override
    public String toString() {
        return "Performance: {" +
                "win: " + win_count +
                ", lose: " + lose_count +
                ", tie: " + stalemate_count +
                '}';
    }
}
