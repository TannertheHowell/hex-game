import java.lang.reflect.Array;
import java.util.ArrayList;

public class HexGame {

    private Color[] grid;
    private int size;
    private DisjointSet redPlayer;
    private DisjointSet bluePlayer;

    private final int TOP_EDGE;
    private final int BOTTOM_EDGE;
    private final int LEFT_EDGE;
    private final int RIGHT_EDGE;

    // Setting up possible colors
    public enum Color {
        None,
        Red,
        Blue
    }

    // Constructor and setting up red/blue's disjoint sets
    public HexGame(int sze){
        this.size = sze;
        grid = new Color[size * size + 1];
        for(int i = 0; i < size * size; i++){
            grid[i] = Color.None;
        }
        // Extra edge elements
        TOP_EDGE = size * size + 1;
        BOTTOM_EDGE = size * size + 2;
        LEFT_EDGE = size * size + 3;
        RIGHT_EDGE = size * size + 4;
        redPlayer = new DisjointSet(size * size + 4);
        bluePlayer = new DisjointSet(size * size + 4);
    }


    // Position is an int from 1 to 121
    // When displayNeighbors is true, the hex positions of neighboring cells are shown
    // Return true if blue has won, otherwise false for not winning or if the space is full already
    public boolean playBlue(int position, boolean displayNeighbors){
        // Store all the neighbors around position
        ArrayList<Integer> neighbors = getNeighborsBlue(position);

        // Check for printing the neighbors
        if(displayNeighbors){
            System.out.println("Blue Cell " + position + " has neighbors: " + neighbors);
        }

        // Check if the position is already full first
        if(isOccupied(position)){
            return false;
        }

        // If it's not empty, turn it blue
        grid[position-1] = Color.Blue;

        for (Integer neighbor : neighbors){
            if(neighbor > size * size || grid[neighbor - 1] == Color.Blue){
                bluePlayer.union(position - 1, neighbor - 1);
            }
        }
        return bluePlayer.find(LEFT_EDGE - 1) == bluePlayer.find(RIGHT_EDGE - 1);
    }

    // Same logic as playBlue, but switched for red winning
    public boolean playRed(int position, boolean displayNeighbors){
        // Store all the neighbors around position
        ArrayList<Integer> neighbors = getNeighborsRed(position);

        // Check for printing the neighbors
        if(displayNeighbors){
            System.out.println("Red Cell " + position + " has neighbors: " + neighbors);
        }

        // Check if the position is already full first
        if(isOccupied(position)){
            return false;
        }

        // If it's not empty, turn it red
        grid[position-1] = Color.Red;

        for (Integer neighbor : neighbors){
            if(neighbor > size * size || grid[neighbor-1] == Color.Red){
                redPlayer.union(position - 1, neighbor - 1);
            }
        }
        return redPlayer.find(TOP_EDGE - 1) == redPlayer.find(BOTTOM_EDGE - 1);
    }

    public Color[] getGrid(){
        return grid;
    }

    public int getSize(){
        return size;
    }

    private boolean isOccupied(int position){
        return grid[position-1] != Color.None;
    }

    public Color getColorAtPosition(int row, int col){
        int position = row * size + col;
        return grid[position];
    }

    private ArrayList<Integer> getNeighborsRed(int position){
        int gridIndex = position - 1;
        int gridIndexRow = gridIndex / size;
        int gridIndexCol = gridIndex % size;

        // Store all the neighbors around position
        ArrayList<Integer> neighbors = new ArrayList<>();

        // 2D array for the relative rows and columns compared to the given position
        int [][] hexCoords = {{ -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }};

        // Looping over all the possible neighbors around the position
        for (int[] coords : hexCoords){
            int neighborRow = gridIndexRow + coords[0];
            int neighborCol = gridIndexCol + coords[1];

            // Before adding the new position, make sure it's within the board's size limits
            if(neighborRow >= 0 && neighborRow < size && neighborCol >= 0 && neighborCol < size){
                neighbors.add(neighborRow * size + neighborCol + 1);
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
        int gridIndex = position - 1;
        int gridIndexRow = gridIndex / size;
        int gridIndexCol = gridIndex % size;

        // Store all the neighbors around position
        ArrayList<Integer> neighbors = new ArrayList<>();

        // 2D array for the relative rows and columns compared to the given position
        int [][] hexCoords = {{ -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }};

        // Looping over all the possible neighbors around the position
        for (int[] coords : hexCoords){
            int neighborRow = gridIndexRow + coords[0];
            int neighborCol = gridIndexCol + coords[1];

            // Before adding the new position, make sure it's within the board's size limits
            if(neighborRow >= 0 && neighborRow < size && neighborCol >= 0 && neighborCol < size){
                neighbors.add(neighborRow * size + neighborCol + 1);
            }
        }
        // Check if the position is on the left edge
        if(gridIndex % size == 0){
            neighbors.add(LEFT_EDGE);
        }

        // Check if the position is on the bottom edge
        if(gridIndex % size == size - 1){
            neighbors.add(RIGHT_EDGE);
        }
        return neighbors;
    }
}
