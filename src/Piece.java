/*
* Piece
* symbol and color
* toString is Override for colorful_print
* */

public class Piece {

    private static final char default_symbol = ' ';
    private char symbol;
    private String color;

    // constructor
    public Piece() {
        init();
    }

    public void init() {
        this.symbol = default_symbol;
        this.color = "DEFAULT";
    }
    public char get_default_symbol() {
        return default_symbol;
    }

    // set
    public void set(char symbol) {
        this.symbol = symbol;
    }

    // get
    public char get() {
        return this.symbol;
    }

    public void set_color(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return Color.get_message_with_color(this.symbol, this.color);
    }
}
