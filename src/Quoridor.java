/*
* Game Quoridor
* print board: make most use of color to display walls and legal positions to move
* step: parse input_string to 1 or 2 or 4 integers
* 1 or 2: step_move
* 4: step_place_a_wall
* Other functions are for judgements of different cases
*   move:
*       based on get_legal_moves()
*       may jump
*   place a wall:
*       whether it can form a wall
*       whether new wall and old wall intersect or overlap
*       whether block the way from some player to the end
* */

import java.util.*;

public class Quoridor extends BaseGame {

    static String COLOR_WALL = "RED";
    static String COLOR_LEGAL_POS_TO_MOVE = "CYAN";

    private static final HashMap<Character, Character> user_input_char_to_direction;
    static {
        user_input_char_to_direction = new HashMap<>();
        user_input_char_to_direction.put('w', 'u');
        user_input_char_to_direction.put('s', 'd');
        user_input_char_to_direction.put('a', 'l');
        user_input_char_to_direction.put('d', 'r');
    }
    private final int[] player_win_destinations = {8, 0};
    private final int[][] players_pos = {{0, 4}, {8, 4}};
    private final int[] player_num_walls = {10, 10};
    private int next_player_id = 0;
    public void step_player_id(int player_id) {
        next_player_id = 1 - player_id;
    }

    Quoridor() {
        super(
            3,
            "QRD",
            "Queridor",
            9,
            9,
            0,
            true,
            false
        );
    }

    @Override
    public void init() {
        int[] wh = this.get_board_width_height(default_width, default_height);
        int w = wh[0];
        int h = wh[1];
        this.board.init(wh[0], wh[1]);

        next_player_id = 0;

        players_pos[0][0] = 0;
        players_pos[0][1] = w / 2;
        players_pos[1][0] = h - 1;
        players_pos[1][1] = w / 2;

        player_win_destinations[0] = h - 1;
        player_win_destinations[1] = 0;

        System.out.println("Players' poses: " + Arrays.deepToString(players_pos));
        System.out.println("Players' destinations: " + Arrays.toString(player_win_destinations));

        for (int r=0; r<h; r++) {
            for (int c=0; c<w; c++) {
                this.board.set(r, c, new TilePieceWithWall());
            }
        }
        for (int player_id=0; player_id<2; player_id++) {
            this.board.set_symbol(players_pos[player_id], possible_symbols[player_id]);
        }
    }

    void forecast_state(Player player, char player_symbol, int player_id) {
        print_state_line();
        System.out.printf("<%s> | Your Turn (%d).\n", player.get_name(), this.board.get_n_step() + 1);
        System.out.printf("The symbol you use is %s.\n", Color.get_message_with_color(player_symbol, player.get_color()));
        System.out.printf("You have %d/10 walls to use.\n", player_num_walls[player_id]);
        System.out.printf("Your destination is row %d.\n", player_win_destinations[player_id]);
        print_state_line();
    }

    public boolean _wall_connection(int r, int c) {
        // used for print
        // - print the + before item, so judge l and u

        if (r == 0 || c == 0)
            return false;

        if (_get_wall(r, c, 'l') != null && _get_wall(r, c, 'l') == _get_wall(r - 1, c, 'l'))
            return true;

        if (_get_wall(r, c, 'u') != null && _get_wall(r, c, 'u') == _get_wall(r, c - 1, 'u'))
            return true;

        return false;
    }

    public void print_board() {

        ArrayList<int[]> next_player_valid_pos_to_move = get_legal_moves(next_player_id, true); 
        
        int index;
        String colored_index;
        String indexString;
        String colored_vertical_wall = Color.get_message_with_color('|', COLOR_WALL);
        String colored_horizontal_wall = Color.get_message_with_color("---", COLOR_WALL);
        String colored_wall_connection = Color.get_message_with_color('+', COLOR_WALL);
        int w = board.get_width();
        int h = board.get_height();
        boolean color_ok = w * h <= 1000;
        for (int r=0; r<h; r++) {
            // +---+
            for (int c=0; c<w; c++) {
                if (_wall_connection(r, c))
                    System.out.print(colored_wall_connection);
                else
                    System.out.print('+');
                if (_has_a_wall(r, c, 'u'))
                    System.out.print(colored_horizontal_wall);
                else
                    System.out.print("---");
            }
            System.out.println('+');
            // pieces
            for (int c=0; c<w; c++) {
                // |
                if (_has_a_wall(r, c, 'l')) {
                    System.out.print(colored_vertical_wall);
                }

                else
                    System.out.print('|');
                //
                if (board.empty_pos(r, c) && color_ok) {
                    index = board.pos2index(r, c);
                    if (legal_move(r, c, next_player_valid_pos_to_move))
                        colored_index = Color.get_message_with_color(String.valueOf(index), COLOR_LEGAL_POS_TO_MOVE);
                    else 
                        colored_index = Color.get_message_with_color(String.valueOf(index), COLOR_INDEX);
                    indexString = Integer.toString(index);
                    switch(indexString.length()) {
                        case 1:
                            System.out.print(" " + colored_index + " ");
                            break;
                        case 2:
                            System.out.print(" " + colored_index);
                            break;
                        case 3:
                            System.out.print(colored_index);
                            break;
                    }
                }
                else
                    System.out.print(" " + board.get(r, c) + ' ');
            }
            // r index
            System.out.println('|' + String.valueOf(r));
        }
        board._print_tiles_split_row(w);

        // c index
        for (int c=0; c<w; c++)
            System.out.print("  " + c + " ");
        System.out.println();
    }

    public Wall _get_wall(int index, char direction) {
        return _get_wall(board.index2pos(index), direction);
    }

    public Wall _get_wall(int[] pos, char direction) {
        return _get_wall(pos[0], pos[1], direction);
    }
    public Wall _get_wall(int r, int c, char direction) {
        return ((TilePieceWithWall)board.get(r, c).tile_object).get_wall(direction);
    }

    public boolean _has_a_wall(int index, char direction) {
        return _has_a_wall(board.index2pos(index), direction);
    }
    public boolean _has_a_wall(int[] pos, char direction) {
        return _has_a_wall(pos[0], pos[1], direction);
    }
    public boolean _has_a_wall(int r, int c, char direction) {
        return ((TilePieceWithWall)board.get(r, c).tile_object).has_a_wall(direction);
    }
    public boolean has_a_path() {
        int p1_r = players_pos[0][0], p1_c = players_pos[0][1];
        int p2_r = players_pos[1][0], p2_c = players_pos[1][1];
        Set<Tile> visited1 = new HashSet<>();
        Set<Tile> visited2 = new HashSet<>();
        if(A_star_search_qrd(p1_r,p1_c,board.get_height()-1,visited1) && A_star_search_qrd(p2_r,p2_c,0,visited2)){
            return true;
        }
        return false;
    }
    private boolean A_star_search_qrd(int cur_r, int cur_c, int end_r, Set<Tile> visited) {
        if (end_r == cur_r) {
            return true;
        }
        visited.add(board.get(cur_r, cur_c));

        int up_dir = end_r>cur_r?1:-1;
        // Heuristically access up, down, left, right neighbor for player that want to go up, and vice versa
        int[][] possible_neighbors = {{cur_r+up_dir, cur_c}, {cur_r, cur_c-1}, {cur_r, cur_c+1}, {cur_r-up_dir, cur_c}};
        char[] dirctions = end_r>cur_r? new char[]{'d','l','r','u'}: new char[]{'u','l','r','d'};
        for (int i=0; i<4; i++) {
            int[] n = possible_neighbors[i];
            if(this.valid_pos(n[0], n[1])){
                if(!_has_a_wall(cur_r,cur_c,dirctions[i])) {
                    if (!visited.contains(board.get(n[0], n[1])) && A_star_search_qrd(n[0], n[1], end_r, visited)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean valid_index(int index) {
        if (0 <= index && index < this.board.get_height() * this.board.get_width())
            return true;
        else {
            System.out.printf("Index %d out of the range [0, %d).\n",
                    index, this.board.get_height() * this.board.get_width());
            return false;
        }
    }

    public boolean valid_pos(int pos_r, int pos_c) {
        return pos_r >= 0 && pos_r < this.board.get_height() && pos_c >= 0 && pos_c < this.board.get_width();
    }

    public boolean intersection_or_overlap(HashMap<Integer, Character> index2direction) {

        char new_wall_v_or_h = Wall.judge_vertical_or_horizontal(index2direction);

        // Convert keySet to ArrayList
        ArrayList<Integer> sorted_indexes = new ArrayList<>(index2direction.keySet());
        // Sort the ArrayList
        Collections.sort(sorted_indexes);
        Wall temp_wall;

        if (new_wall_v_or_h == 'v') {
            // vertical
            // judge 0, 2, r for overlap
            // judge 0, 1, d same wall for intersection
            if (_has_a_wall(sorted_indexes.get(0), 'r') || _has_a_wall(sorted_indexes.get(2), 'r')) {
                print_warning("Walls Overlap.");
                return true;
            }
            temp_wall = _get_wall(sorted_indexes.get(0), 'd');
            if (temp_wall != null && temp_wall == _get_wall(sorted_indexes.get(1), 'd')) {
                print_warning("Wall Intersection");
                return true;
            }
        }
        else {
            // 'h'
            // horizontal
            // 0, 1, d for overlap
            // 0, 2, r same wall for intersection
            if (_has_a_wall(sorted_indexes.get(0), 'd') || _has_a_wall(sorted_indexes.get(1), 'd')) {
                print_warning("Walls Overlap.");
                return true;
            }
            temp_wall = _get_wall(sorted_indexes.get(0), 'r');
            if (temp_wall != null && temp_wall == _get_wall(sorted_indexes.get(2), 'r')) {
                print_warning("Wall Intersection");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean step(Player player, Player opponent) {

        char player_symbol = this.get_input_symbol();
        String user_input_string;
        int[] integers;

        int player_id;
        if (player.check_first_player())
            player_id = 0;
        else
            player_id = 1;

        this.forecast_state(player, player_symbol, player_id);

        while (true) {
//            System.out.println("Please enter [w (up), s (down), a (left), d (right)] to move or 'o' to obstruct.");
            print_instr_line();
            System.out.println(
                    "Please enter 1 integer (index) or 2 integers (position) to move,\n" +
                    "Or 4 integers (tile indexes as a wall). The first(last) two form the first(second) wall."
            );
            print_instr_line();

            user_input_string = input_scanner.input_string();

            // empty continue
            if (user_input_string.isEmpty()) {
                continue;
            }

            // use a wall
            integers = input_scanner.parse_n_none_negative_integers(user_input_string, 4);
            if (integers != null) {
                // can regret when have no walls or make a player unable to reach
                if (this.step_put_a_wall(integers, player_id))
                    break;
            }

            if (this.step_move(player_id, player_symbol, player.get_color(), user_input_string))
                break;

            System.out.println("[WARNING] Please enter a valid operation!");
        }
        
        step_player_id(player_id);
        print_board();

        // very easy and may not need to create a new Judge class
        // Just needs to update the performance
        // can be optimized
        if (judge.judge(
                board, players_pos[player_id], player_win_destinations[player_id],
                player_symbol, possible_symbols, // useless
                player, opponent, false) == 'w')
            return true;
        return false;
    }

    // ============================== Move ==============================

    public boolean legal_move(int r, int c, ArrayList<int[]> legal_moves) {
        return legal_move(new int[]{r, c}, legal_moves);
    }

    public boolean legal_move(int[] move, ArrayList<int[]> legal_moves) {
        for (int[] legal_move: legal_moves) {
            if (Arrays.equals(move, legal_move)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<int[]> get_legal_moves(int player_id, boolean jump)
    {
        // Input: player index and jump(Boolean: jump over available or not) Output: list of positions (r,c)
        int cur_r = players_pos[player_id][0];
        int cur_c = players_pos[player_id][1];

        int[][] possible_neighbors = {{cur_r+1, cur_c}, {cur_r, cur_c-1}, {cur_r, cur_c+1}, {cur_r-1, cur_c}};
        char[] directions = {'d','l','r','u'};

        ArrayList<int[]> possible_moves = new ArrayList<int[]>();
        for (int i=0; i<4; i++) {
            int[] n = possible_neighbors[i];
            if(this.valid_pos(n[0], n[1])){
                if(!_has_a_wall(cur_r,cur_c,directions[i])) {
                    if(board.empty_pos(n) ){
                        possible_moves.add(n);
                    }
                    else if(jump){// The other player is at that position, and jump over is available
                        ArrayList<int[]> step_over_moves = get_legal_moves(1-player_id, false);
                        possible_moves.addAll(step_over_moves);
                    }
                }
            }
        }
        return possible_moves;
    }

    public boolean step_move(int player_id, char player_symbol, String player_color, String user_input_string) {
        int[] integers;
        int[] pos = new int[2];
        integers = input_scanner.parse_n_none_negative_integers(user_input_string, 2);
        if (integers != null) {
            // get a pos
            pos = integers;
        }
        else{
            integers = input_scanner.parse_n_none_negative_integers(user_input_string, 1);
            if (integers != null) {
                // get a index
                pos[0] = integers[0] / board.get_width();
                pos[1] = integers[0] % board.get_width();
            }
            else {
                return false;
            }
        }
        if (legal_move(pos, get_legal_moves(player_id, true))) {
            System.out.printf("You move to (%d, %d).\n", pos[0], pos[1]);

            board.get(players_pos[player_id]).init_symbol();
            board.set_symbol(pos, player_symbol);
            board.set_color(pos, player_color);
            players_pos[player_id] = pos;

            return true;
        }
        else {
            System.out.println("[WARNING] Illegal move!");
            return false;
        }
    }

    // ============================== Wall ==============================
    public void set_wall_or_remove_wall(HashMap<Integer, Character> index2wall_direction, Wall wall) {
        char direction;
        int index;
        TilePieceWithWall tile_piece_with_wall;
        for (Map.Entry<Integer, Character> entry : index2wall_direction.entrySet()) {
            index = entry.getKey();
            tile_piece_with_wall = (TilePieceWithWall) this.board.get(index).get_tile_object();
            direction = entry.getValue();
            tile_piece_with_wall.set_wall(direction, wall);
        }
    }

    public boolean step_put_a_wall(int[] four_tile_indexes, int player_id) {

        if (player_num_walls[player_id] == 0) {
            System.out.println("You have no walls and can only move");
            return false;
        }

        boolean if_valid;
        HashMap<Integer, Character> index2wall_direction;

        /* 1. get 4 valid indexes */
        if_valid = true;
        for (int index : four_tile_indexes) {
            if (!valid_index(index)) {
                if_valid = false;
                break;
            }
        }
        if (!if_valid)
            return false;

        /* 2. judge whether indexes can form a wall */
        index2wall_direction = Wall.check_valid_indexes(four_tile_indexes, this.board.get_width(), 2);
        if (index2wall_direction == null)
            return false;

        /* 3. judge whether intersection or overlap */
        if (intersection_or_overlap(index2wall_direction)) {
            return false;
        }

        // First add wall, then judge if accessible
        Wall wall = new Wall(index2wall_direction);
        set_wall_or_remove_wall(index2wall_direction, wall);

        /* 4. check if the wall will lead to either player unable to reach the destination */
        if (!has_a_path()) {
            // return false give a chance for player to regret rather than keep continuing
//                _has_a_wall(new int[2], ' '); //pos
//                _has_a_wall(1, 2, ' ');
//                _has_a_wall(3, ' ');
            print_warning("You can't block one player's every path to win!");

            // Not accessible, so remove the wall
            set_wall_or_remove_wall(index2wall_direction, null);
            return false;
        }
        else {
            // valid
            // minus the num_walls
            player_num_walls[player_id] --;
            return true;
        }
    }
}
