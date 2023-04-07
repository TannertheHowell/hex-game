import java.lang.reflect.Array;
import java.util.ArrayList;

public class HexGame {

    private Color[] grid;
    private int size;
    private DisjointSet redPlayer;
    private DisjointSet bluePlayer;

    // Extra edge elements
    private final int TOP_EDGE = size + 1;
    private final int BOTTOM_EDGE = size + 2;
    private final int LEFT_EDGE = size + 3;
    private final int RIGHT_EDGE = size + 4;

    // Constructor and setting up red/blue's disjoint sets
    public HexGame(int size){
        this.size = size;
        grid = new Color[size * size + 1];
        for(int i = 1; i <= size * size; i++){
            grid[i] = Color.None;
        }
        redPlayer = new DisjointSet(size * size + 4);
        bluePlayer = new DisjointSet(size * size + 4);
    }


    // Position is an int from 1 to 121
    // When displayNeighbors is true, the hex positions of neighboring cells are shown
    // Return true if blue has won, otherwise false for not winning or if the space is full already
    public boolean playBlue(int position, boolean displayNeighbors){
        // Check if the position is already full first
        if(isOccupied(position)){
            return false;
        }

        // If it's not empty, turn it blue
        grid[position] = Color.Blue;

        // Store all the neighbors around position
        ArrayList<Integer> neighbors = getNeighborsBlue(position);

        // Check for printing the neighbors
        if(displayNeighbors){
            System.out.println("Blue's neighbors: " + neighbors);
        }
        for (Integer neighbor : neighbors){
            // See if we need to union different sets
            if(grid[neighbor] == Color.Blue){
                bluePlayer.union(position, neighbor);
            }
        }
        return bluePlayer.find(LEFT_EDGE) == bluePlayer.find(RIGHT_EDGE);
    }

    // Same logic as playBlue, but switched for red winning
    public boolean playRed(int position, boolean displayNeighbors){
        // Check if the position is already full first
        if(isOccupied(position)){
            return false;
        }

        // If it's not empty, turn it red
        grid[position] = Color.Red;

        // Store all the neighbors around position
        ArrayList<Integer> neighbors = getNeighborsRed(position);

        // Check for printing the neighbors
        if(displayNeighbors){
            System.out.println("Red's neighbors: " + neighbors);
        }
        for (Integer neighbor : neighbors){
            // See if we need to union different sets
            if(grid[neighbor] == Color.Red){
                redPlayer.union(position, neighbor);
            }
        }
        return redPlayer.find(TOP_EDGE) == redPlayer.find(BOTTOM_EDGE);
    }

    private enum Color {
        None,
        Red,
        Blue
    }

    public Color[] getGrid(){
        return grid;
    }

    public int getSize(){
        return size;
    }

    private boolean isOccupied(int position){
        return grid[position] != Color.None;
    }

    private ArrayList<Integer> getNeighborsRed(int position){
        // Store all the neighbors around position
        ArrayList<Integer> neighbors = new ArrayList<>();

        // 2D array for the relative rows and columns compared to the given position
        int [][] hexCoords = {{ -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, 1 }, { 1, -1 }};

        // Looping over all the possible neighbors around the position
        for (int[] coords : hexCoords){
            int updatedRow = (position - 1) / (size + coords[0]);
            int updatedCol = (position - 1) % (size + coords[1]);

            // Before adding the new position, make sure it's within the board's size limits
            if(updatedRow >= 0 && updatedRow < size && updatedCol >= 0 && updatedCol < size){
                neighbors.add(updatedRow * size + updatedCol + 1);
            }
        }
        // Check if the position is on the top edge
        if((position - 1) / size == 0){
            neighbors.add(TOP_EDGE);
        }

        // Check if the position is on the bottom edge
        if((position - 1) / size == size - 1){
            neighbors.add(BOTTOM_EDGE);
        }
        return neighbors;
    }

    private ArrayList<Integer> getNeighborsBlue(int position){
        // Store all the neighbors around position
        ArrayList<Integer> neighbors = new ArrayList<>();

        // 2D array for the relative rows and columns compared to the given position
        int [][] hexCoords = {{ -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, 1 }, { 1, -1 }};

        // Looping over all the possible neighbors around the position
        for (int[] coords : hexCoords){
            int updatedRow = (position - 1) / (size + coords[0]);
            int updatedCol = (position - 1) % (size + coords[1]);

            // Before adding the new position, make sure it's within the board's size limits
            if(updatedRow >= 0 && updatedRow < size && updatedCol >= 0 && updatedCol < size){
                neighbors.add(updatedRow * size + updatedCol + 1);
            }
        }
        // Check if the position is on the left edge
        if((position - 1) % size == 0){
            neighbors.add(LEFT_EDGE);
        }

        // Check if the position is on the bottom edge
        if((position - 1) % size == size - 1){
            neighbors.add(RIGHT_EDGE);
        }
        return neighbors;
    }
}
