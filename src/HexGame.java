import java.util.ArrayList;

public class HexGame {

    // Position is an int from 1 to 121
    // When displayNeighbors is true, the hex positions of neighboring cells are shown
    // Return true if blue has won, otherwise false for not winning or if the space is full already
    public boolean playBlue(int position, boolean displayNeighbors){
        return false;
    }

    // Same logic as playBlue, but switched for red winning
    public boolean playRed(int position, boolean displayNeighbors){
        return false;
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
        return this.size;
    }

    private boolean isOccupied(int position){
        return false;
    }

    private ArrayList<Integer> getNeighborsRed(int position){
        return null;
    }

    private ArrayList<Integer> getNeighborsBlue(int position){
        return null;
    }
}
