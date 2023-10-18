/*
* Game selector factory
* games_pool
* */

import java.util.HashMap;

public interface GameFactoryInterface {
    BaseGame createGame(int game_id);

    BaseGame createGame(String game_key);
}

class GameFactory implements GameFactoryInterface {

    private final HashMap<Integer, String> games_pool;
    private final HashMap<String, String> key2fullname;

    GameFactory() {
        games_pool = new HashMap<>();
        games_pool.put(0, "TTT");
        games_pool.put(1, "OC");
        games_pool.put(2, "SuperTTT");
        games_pool.put(3, "QRD");

        key2fullname = new HashMap<>();
        key2fullname.put("TTT", "Tic Tac Toe");
        key2fullname.put("OC", "Order and Chaos");
        key2fullname.put("SuperTTT", "Super Tic Tac Toe");
        key2fullname.put("QRD", "Quoridor");
    }

    public HashMap<Integer, String> get_games_pool() {
        return games_pool;
    }

    public Iterable<Integer> get_game_ids() {
        return games_pool.keySet();
    }

    public Iterable<String> get_game_keys() {
        return games_pool.values();
    }

    public String get_game_key(int game_id) {
        return games_pool.get(game_id);
    }

    public String get_game_name(int game_id) {
        return this.get_game_name(games_pool.get(game_id));
    }

    public String get_game_name(String game_key) {
        return key2fullname.get(game_key);
    }

    @Override
    public BaseGame createGame(int game_id) {
        return createGame(this.get_game_key(game_id));
    }

    @Override
    public BaseGame createGame(String game_key) {
        switch (game_key) {
            case "TTT":
                return new TTT();
            case "OC":
                return new OC();
            case "SuperTTT":
                return new SuperTTT();
            case "QRD":
                return new Quoridor();
            default:
                throw new IllegalArgumentException("Unsupported file type");
        }
    }


//    @Override
//    public BaseGame createGame(String game_key) {
//        return switch (game_key) {
//            case "TTT" -> new TTT();
//            case "OC" -> new OC();
//            case "SuperTTT" -> new SuperTTT();
//            default -> throw new IllegalArgumentException("Unsupported file type");
//        };
//    }
}
