import java.util.*;

public class Wall{

    private static final HashMap<Character, Character> inverse_directions = new HashMap<>();
    static {
        inverse_directions.put('u', 'd');
        inverse_directions.put('d', 'u');
        inverse_directions.put('l', 'r');
        inverse_directions.put('r', 'l');
    }

    private final char vertical_or_horizon;

    // Hashmap
    // Key: u d l r, four directions
    // ArrayList<Integer>: 0, 2
    private final HashMap<Character, ArrayList<Integer>> directions_to_tile_indexes;

    Wall(HashMap<Integer, Character> tile_indexes_to_directions) {
        directions_to_tile_indexes = new HashMap<>();
        for (char direction: new char[]{'u', 'd', 'l', 'r'}) {
            directions_to_tile_indexes.put(direction, new ArrayList<>());
        }
        int index;
        char direction;
        for (Map.Entry<Integer, Character> entry : tile_indexes_to_directions.entrySet()) {
            index = entry.getKey();
            direction = entry.getValue();
            directions_to_tile_indexes.get(inverse_directions.get(direction)).add(index);
        }
        this.vertical_or_horizon = judge_vertical_or_horizontal();
    }

    public char judge_vertical_or_horizontal() {
        if (directions_to_tile_indexes.get('u').isEmpty())
            // u has no tile means vertical
            return 'v';
        else
            return 'h';
    }

    public static char judge_vertical_or_horizontal(HashMap<Integer, Character> index2direction) {
        return judge_vertical_or_horizontal(index2direction.values());
    }

    public static char judge_vertical_or_horizontal(Collection<Character> directions) {
        if (directions.contains('l') || directions.contains('r'))
            // contains l or r means vertical
            return 'v';
        else
            return 'h';
    }

    public char get_vertical_or_horizon() {
        return this.vertical_or_horizon;
    }

    public ArrayList<Integer> get_tile_index(Character direction) {
        return directions_to_tile_indexes.get(direction);
    }

    public static HashMap<Integer, Character> check_valid_indexes(int[] four_tile_indexes, int width, int wall_len) {
        HashMap<Integer, Character> resultMap = new HashMap<>();

        Set<Integer> sideIndexes1 = new HashSet<>(Arrays.asList(four_tile_indexes[0], four_tile_indexes[1]));
        Set<Integer> sideIndexes2 = new HashSet<>(Arrays.asList(four_tile_indexes[2], four_tile_indexes[3]));
        if (sideIndexes1.containsAll(sideIndexes2)) {
            System.out.println("[WARNING] Tiles on both sides are same.");
            return null;
        }

        String firstWallOrientation = get_wall_orientation_by_index(four_tile_indexes[0], four_tile_indexes[1], width, wall_len);
        if ("none".equals(firstWallOrientation)) {
            System.out.println("[WARNING] The line connecting tile0 and tile1 is not horizontal or vertical.");
            return null;
        }

        String secondWallOrientation = get_wall_orientation_by_index(four_tile_indexes[2], four_tile_indexes[3], width, wall_len);
        if (!firstWallOrientation.equals(secondWallOrientation)) {
            System.out.println("[WARNING] Tiles on both sides are not parallel.");
            return null;
        }

        Set<Integer> firstWallSet = new HashSet<>();
        Set<Integer> secondWallSet = new HashSet<>();
        if ("vertical".equals(firstWallOrientation)) {
            firstWallSet.add(four_tile_indexes[0] / width);
            firstWallSet.add(four_tile_indexes[1] / width);
            secondWallSet.add(four_tile_indexes[2] / width);
            secondWallSet.add(four_tile_indexes[3] / width);

            if (!firstWallSet.equals(secondWallSet)) {
                System.out.println("[WARNING] Parallel but not a rectangle!");
                return null;
            }

            char side1 = (four_tile_indexes[0] % width < four_tile_indexes[2] % width) ? 'r' : 'l';
            char side2 = inverse_directions.get(side1);

            resultMap.put(four_tile_indexes[0], side1);
            resultMap.put(four_tile_indexes[1], side1);
            resultMap.put(four_tile_indexes[2], side2);
            resultMap.put(four_tile_indexes[3], side2);
        } else {
            // horizontal
            firstWallSet.add(four_tile_indexes[0] % width);
            firstWallSet.add(four_tile_indexes[1] % width);
            secondWallSet.add(four_tile_indexes[2] % width);
            secondWallSet.add(four_tile_indexes[3] % width);

            if (!firstWallSet.equals(secondWallSet)) {
                System.out.println("[WARNING] Parallel but not a rectangle!");
                return null;
            }

            char side1 = (four_tile_indexes[0] / width < four_tile_indexes[2] / width) ? 'd' : 'u';
            char side2 = inverse_directions.get(side1);

            resultMap.put(four_tile_indexes[0], side1);
            resultMap.put(four_tile_indexes[1], side1);
            resultMap.put(four_tile_indexes[2], side2);
            resultMap.put(four_tile_indexes[3], side2);
        }

        return resultMap;
    }

    public static String get_wall_orientation_by_index(int index1, int index2, int width, int wall_len) {
        if (Math.abs(index1 - index2) == wall_len - 1) {
            return "horizontal";
        } else if (Math.abs(index1 - index2) == width * (wall_len - 1)) {
            return "vertical";
        }
        return "none";
    }

    public static HashMap<Integer, Character> check_valid_indexes(int[][] four_tile_poses, int wall_len) {
        HashMap<Integer, Character> resultMap = new HashMap<>();

        String firstWallOrientation = get_wall_orientation_by_pos(four_tile_poses[0], four_tile_poses[1], wall_len);
        if ("none".equals(firstWallOrientation)) {
            System.out.println("[WARNING] The line connecting tile0 and tile1 is not horizontal or vertical.");
            return null;
        }

        String secondWallOrientation = get_wall_orientation_by_pos(four_tile_poses[2], four_tile_poses[3], wall_len);
        if (!firstWallOrientation.equals(secondWallOrientation)) {
            System.out.println("[WARNING] Tiles on both sides are not parallel.");
            return null;
        }

        Set<Integer> firstWallSet = new HashSet<>();
        Set<Integer> secondWallSet = new HashSet<>();
        if ("vertical".equals(firstWallOrientation)) {

            firstWallSet.add(four_tile_poses[0][0]);
            firstWallSet.add(four_tile_poses[1][0]);
            secondWallSet.add(four_tile_poses[2][0]);
            secondWallSet.add(four_tile_poses[3][0]);

            if (!firstWallSet.equals(secondWallSet)) {
                System.out.println("[WARNING] Parallel but not a rectangle!");
                return null;
            }

            // Determine sides for the first wall
            char side1 = (four_tile_poses[0][1] < four_tile_poses[1][1]) ? 'l' : 'r';
            char side2 = (four_tile_poses[0][1] < four_tile_poses[1][1]) ? 'r' : 'l';

            resultMap.put(four_tile_poses[0][0], side1);
            resultMap.put(four_tile_poses[1][0], side1);
            resultMap.put(four_tile_poses[2][0], side2);
            resultMap.put(four_tile_poses[3][0], side2);
        } else {
            firstWallSet.add(four_tile_poses[0][1]);
            firstWallSet.add(four_tile_poses[1][1]);
            secondWallSet.add(four_tile_poses[2][1]);
            secondWallSet.add(four_tile_poses[3][1]);

            if (!firstWallSet.equals(secondWallSet)) {
                System.out.println("[WARNING] Parallel but not a rectangle!");
                return null;
            }

            // Determine sides for the horizontal wall
            char side1 = (four_tile_poses[0][0] < four_tile_poses[2][0]) ? 'd' : 'u';
            char side2 = inverse_directions.get(side1);

            resultMap.put(four_tile_poses[0][1], side1);
            resultMap.put(four_tile_poses[1][1], side1);
            resultMap.put(four_tile_poses[2][1], side2);
            resultMap.put(four_tile_poses[3][1], side2);
        }

        return resultMap;

    }
    public static String get_wall_orientation_by_pos(int[] point1, int[] point2, int wall_len) {
        if (point1[0] == point2[0] && Math.abs(point1[1] - point2[1]) == wall_len - 1) {
            return "horizontal";
        } else if (point1[1] == point2[1] && Math.abs(point1[0] - point2[0]) == wall_len - 1) {
            return "vertical";
        }
        return "none";
    }
}
