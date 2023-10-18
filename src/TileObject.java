/*
* TileObject
* (Change from interface to base class: since always need piece and relative functions, more convenient)
* can be either Piece
* or Piece with a sub game like TTT
* or PieceWithWall: add a wall object in it to record the relationship between the tile and the surrounding walls
* */


import java.util.HashMap;

public abstract class TileObject {

    protected Piece piece;

    TileObject() {
        this.piece = new Piece();
    }

    public abstract void init();

    public Piece get_piece() {
        return this.piece;
    }

    public void init_symbol() {
        this.piece.init();
    }

    public void set_symbol(char symbol) {
        this.piece.set(symbol);
    }

    public void set_color(String color) {
        this.piece.set_color(color);
    }

    public String toString() {
        return this.piece.toString();
    }
}


// 定义Piece类实现GameObject接口
class TilePiece extends TileObject {

    TilePiece() {
        super();
    }

    @Override
    public void init() {
        this.piece.init();
    }
}


// 如果有其他类型的对象，例如Obstacle，也实现GameObject接口
class TileTTT extends TileObject {

    private final TTT ttt;

    TileTTT() {
        super();
        this.ttt = new TTT();
    }

    public void init() {
        this.piece.init();
        this.ttt.init();
    }

    public TTT getTTT() {
        return this.ttt;
    }
}


class TilePieceWithWall extends TileObject {

    private final HashMap<Character, Wall> direction_to_wall;

    TilePieceWithWall() {
        super();
        this.direction_to_wall = new HashMap<>();
    }

    @Override
    public void init() {
        this.piece.init();
//        for (Character direction: direction_to_wall.keySet())
//            direction_to_wall.put(direction, null);
        direction_to_wall.replaceAll((d, v) -> null);
    }

    public boolean has_a_wall(Character direction) {
        return direction_to_wall.get(direction) != null;
    }

    public void set_wall(Character direction, Wall wall) {
        direction_to_wall.put(direction, wall);
    }

    public Wall get_wall(Character direction) {
        return direction_to_wall.get(direction);
    }

}


