import java.io.File;
import java.util.Scanner;

public class Assignment6Driver {
    public static void main(String[] args) {

//        testGame();
        playGame("moves1.txt");
        System.out.println();
        playGame("moves2.txt");
    }

    private static void playGame(String filename) {
        File file = new File(filename);
        try (Scanner input = new Scanner(file)) {
            boolean isBlueTurn = true;
            HexGame game = new HexGame(11);
            while(input.hasNext()){

                int position = Integer.parseInt(input.next());

                if(isBlueTurn){
                    boolean winCondition = game.playBlue(position, false);
                    if (winCondition){
                        System.out.println("Blue wins with move at position " + position +"!!");
                        printGrid(game);
                        break;
                    }
                }
                else{
                    boolean winCondition = game.playRed(position, false);
                    if (winCondition){
                        System.out.println("Red wins with move at position " + position +"!!");
                        printGrid(game);
                        break;
                    }
                }
                isBlueTurn = !isBlueTurn;
            }
        } catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the moves file: " + ex);
        }
    }


    // TODO: You can use this to compare with the output show in the assignment while working on your code
    private static void testGame() {
        HexGame game = new HexGame(11);

        System.out.println("--- red ---");
        game.playRed(1, true);
        game.playRed(11, true);
        game.playRed(122 - 12, true);
        game.playRed(122 - 11, true);
        game.playRed(122 - 10, true);
        game.playRed(121, true);
        game.playRed(61, true);

        System.out.println("--- blue ---");
        game.playBlue(1, true);
        game.playBlue(2, true);
        game.playBlue(11, true);
        game.playBlue(12, true);
        game.playBlue(121, true);
        game.playBlue(122 - 11, true);
        game.playBlue(62, true);

        printGrid(game);
    }

    private static void printGrid(HexGame game) {

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_BLUE = "\u001B[34m";


        for (int row = 0; row < game.getSize(); row++){

            // Dealing the indented spacing for the grid
            for(int i = 0; i < row; i++) {
                System.out.print(" ");
            }

            for(int col = 0; col < game.getSize(); col++){
                HexGame.Color currentColor = game.getColorAtPosition(row, col);
                if(currentColor == HexGame.Color.Blue){
                    System.out.print(ANSI_BLUE + "B " + ANSI_RESET);
                }
                else if(currentColor == HexGame.Color.Red){
                    System.out.print(ANSI_RED + "R " + ANSI_RESET);
                }
                else{
                    System.out.print("0 ");
                }

            }
            System.out.print("\n");

        }
    }

}
