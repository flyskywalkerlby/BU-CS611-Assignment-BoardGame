/*
* board object
* contains Tile[][], w, h
* connect user operation with tile, piece, etc
* */

public class Board {

    static String COLOR_INDEX = "YELLOW";
    private int width;
    public int get_width() {
        return this.width;
    }
    private int height;
    public int get_height() {
        return this.height;
    }

    private int n_step;
    public int get_n_step() {
        return this.n_step;
    }
    public void step_n_step() {
        this.n_step ++;
    }

    private Tile[][] tiles;

    public void set(int r, int c, TileObject tile_object) {
        this.tiles[r][c] = new Tile(tile_object);
    }

    public Tile get(int r, int c) {
        return this.tiles[r][c];
    }
    public Tile get(int[] pos) {
        return this.get(pos[0], pos[1]);
    }
    public Tile get(int index) {
        return this.get(this.index2pos(index));
    }
    public void set_symbol(int[] pos, char symbol) {
        this.set_symbol(pos[0], pos[1], symbol);
    }
    public void set_symbol(int r, int c, char symbol) {
        this.tiles[r][c].set_symbol(symbol);
        this.n_step ++;
    }

    public void set_color(int[] pos, String color) {
        this.set_color(pos[0], pos[1], color);
    }
    public void set_color(int r, int c, String color) {
        this.tiles[r][c].set_color(color);
    }
    public char get_symbol(int r, int c) {
        return this.tiles[r][c].get_symbol();
    }
    public String get_bound() {
        int max_index = height * width - 1;
        char letter;
        if (max_index < 0 || max_index >= 26)
            letter = '*';
        else
            letter = (char)('A' + max_index);

        return String.format("Out of range of {Size=[%d, %d], MaxIndex=%d, MaxLetter=%s}",
                height, width, max_index, letter);
    }

    public int[] index2pos(int index) {
        return new int[] {index / width, index % width};
    }

    public int pos2index(int r, int c) {
        return r * width + c;
    }

    public int pos2index(int[] pos) {
        return pos2index(pos[0], pos[1]);
    }

    Board () {}

    void init(int w, int h) {
        this.width = w;
        this.height = h;
        this.n_step = 0;
        this.tiles = new Tile[h][w];
    }

    // Either out of range or not empty are is not a valid pos
    public boolean valid_pos(int[] pos) {
        if (pos[0] < 0 || pos[0] >= this.height || pos[1] < 0 || pos[1] >= this.width) {
            System.out.printf("[WARNING] Got position (%d, %d), out of the tiles size [%d, %d] or the max index %d !!!\n",
                    pos[0], pos[1], this.width, this.height, this.width * this.height - 1);
            return false;
        }
        if (!this.empty_pos(pos)) {
            System.out.println("[WARNING] You must bet in an empty position !!!");
            return false;
        }
        return true;
    }

    public boolean valid_pos(int pos_r, int pos_c) {
        if (pos_r < 0 || pos_r >= this.height || pos_c < 0 || pos_c >= this.width) {
            System.out.printf("[WARNING] Got position (%d, %d), out of the tiles size [%d, %d] or the max index %d !!!\n",
                    pos_r, pos_c, this.width, this.height, this.width * this.height - 1);
            return false;
        }
        if (!this.empty_pos(pos_r, pos_c)) {
            System.out.println("[WARNING] You must bet in an empty position !!!");
            return false;
        }
        return true;
    }

    // check whether the content in the pos is the same as the default value
    public boolean empty_pos(int[] pos) {
        return empty_pos(pos[0], pos[1]);
    }

    public boolean empty_pos(int r, int c) {
        if (this.tiles[r][c].get_symbol() == this.tiles[r][c].get_default_symbol()) {
            return true;
        }
        else {
            return false;
        }
    }

    // frame
    public void _print_tiles_split_row(int w) {
        for (int c=0; c<w; c++) {
            System.out.print("+---");
        }
        System.out.println('+');
    }

    // control by the Tile toString()
    public void print_board() {
        int index;
        String colored_index;
        String indexString;
        for (int r=0; r<this.height; r++) {
            this._print_tiles_split_row(this.width);
            for (int c=0; c<this.width; c++) {

                if (empty_pos(r, c) && this.width * this.height <= 1000) {
                    index = r * this.width + c;
                    colored_index = Color.get_message_with_color(String.valueOf(index), COLOR_INDEX);
                    indexString = Integer.toString(index);
                    switch(indexString.length()) {
                        case 1:
                            System.out.print("| " + colored_index + " ");
                            break;
                        case 2:
                            System.out.print("| " + colored_index);
                            break;
                        case 3:
                            System.out.print("|" + colored_index);
                            break;
//                        default:
//                            System.err.print("Number has more than three digits.");
                    }
                }
                else
                    System.out.print("| " + this.tiles[r][c] + ' ');
            }
            // r index
            System.out.println('|' + String.valueOf(r));
        }
        this._print_tiles_split_row(this.width);

        // c index
        for (int c=0; c<this.width; c++)
            System.out.print("  " + c + " ");
        System.out.println();
    }
}
