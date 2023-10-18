/*
* connect board and piece
* contains tile_object
* */
public class Tile {
    TileObject tile_object;

    Tile(TileObject tile_object) {
        this.tile_object = tile_object;
    }

    public void init() {
        this.tile_object.init();
    }

    TileObject get_tile_object() {
        return tile_object;
    }

    public void init_symbol() {
        tile_object.init_symbol();
    }

    public void set_symbol(char symbol) {
        tile_object.set_symbol(symbol);
    }

    public void set_color(String color) {
        tile_object.set_color(color);
    }

    char get_symbol() {
        return tile_object.get_piece().get();
    }

    char get_default_symbol() {
        return tile_object.get_piece().get_default_symbol();
    }

    void setTileObject(TileObject tile_object) {
        this.tile_object = tile_object;
    }

    @Override
    public String toString() {
        return tile_object.toString();
    }
}