import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.*;
import java.util.stream.Collectors;

public class SudokuGame {
    private static SudokuBoard sb;
    // each internal list represents one row
    private static List<List<BoxSpace>> space;

    // initial sudoku board shown to player
    // 0 are empty and non-fixed spaces
    // must fill with numbers 1 through 9
    private static int[][] initialBoard = {
        {9, 5, 8, 0, 0, 0, 0, 2, 0},
        {0, 0, 0, 2, 5, 6, 0, 4, 0},
        {0, 0, 6, 0, 0, 0, 5, 1, 7},
        {6, 0, 0, 3, 7, 8, 0, 0, 0},
        {7, 8, 4, 0, 0, 0, 9, 3, 2},
        {0, 0, 0, 4, 2, 9, 0, 0, 8},
        {4, 9, 2, 0, 0, 0, 1, 0, 0},
        {0, 6, 0, 5, 8, 1, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 7, 6, 3}
    };

    // complete solution of the initialBoard matrix
    private static final int[][] solution = {
        {9, 5, 8, 7, 1, 4, 3, 2, 6},
        {1, 7, 3, 2, 5, 6, 8, 4, 9},
        {2, 4, 6, 8, 9, 3, 5, 1, 7},
        {6, 2, 9, 3, 7, 8, 4, 5, 1},
        {7, 8, 4, 1, 6, 5, 9, 3, 2},
        {5, 3, 1, 4, 2, 9, 6, 7, 8},
        {4, 9, 2, 6, 3, 7, 1, 8, 5},
        {3, 6, 7, 5, 8, 1, 2, 9, 4},
        {8, 1, 5, 9, 4, 2, 7, 6, 3}
    };

   public static void main(String[] args) {
        // variable which decides if player wants to continue the game
        boolean playing = true;

        // the only necessary scanner object in the code
        Scanner sc = new Scanner(System.in);

        // will continue until player selects exit
        while (playing) {
            // display of options for player
            System.out.print("\n1 - Input Number\n2 - Remove Number\n3 - Visualize Game\n4 - Verify Status\n5 - Clear Inputs\n6 - End Game\n7 - Exit\nOption: ");
            int option = sc.nextInt();
            // startGame creates the board
            if (sb == null) {
                startGame();
            }
            System.out.println();
            switch (option) {
                // will attempt to place the selected number at the specific row x column
                case 1:
                    System.out.print("Row number: ");
                    int row = sc.nextInt() - 1;

                    System.out.print("Column number: ");
                    int column = sc.nextInt() - 1;

                    System.out.print("Choose a number 1-9: ");
                    int num = sc.nextInt();

                    inNumber(row, column, num);

                    break;
                // will attempt to remove the number from specific position, unless fixed
                case 2:
                    System.out.print("Row number: ");
                    int rowRemove = sc.nextInt() - 1;

                    System.out.print("Column number: ");
                    int columnRemove = sc.nextInt() - 1;

                    reNumber(rowRemove, columnRemove);

                    break;
                // will display current state of the game
                case 3:
                    visualizeGame();
                    break;
                // will tell player if there are any errors and if the game is complete or not
                case 4:
                    verifyStatus();
                    break;
                // will remove all player inputs, while mainting the fixed numbers
                case 5:
                    clearInputs();
                    break;
                // will check whether the player has completed the game or not
                case 6:
                    endGame();
                    break;
                // will finalize game 
                case 7:
                    System.out.println("Exiting game...");
                    playing = false;
                    break;
                // will run if player doesn't select a number 1 through 7
                default:
                    System.out.println("Incorrect option! Please select between 1 and 7.");
                    break; 
            }
        }
        // closing user input, good for clean coding
        sc.close();
   }

   // creates all 81 spaces for the board
   public static void startGame() {
        // this will contain the 9 rows
        space = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            // creates a new list to represent the current row
            List<BoxSpace> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                // creates a specific position on the board
                BoxSpace box = new BoxSpace();
                // stores the correct answer in the position
                box.setCorrectNumber(solution[i][j]);

                // if the board doesn't have an empty space
                if (initialBoard[i][j] != 0) {
                    // sets the space to be fixed
                    box.setFixed(true);
                    // will display the number of the specific positon
                    box.setNumber(initialBoard[i][j]);
                } else {
                    // a position editable by the player
                    box.setFixed(false);
                    // empty position
                    box.setNumber(null);
                }
                // adds the specific position to the current row
                row.add(box);
            }
            // adds the complete row to the board
            space.add(row);
        }
        // creates the board, with the space argument 
        sb = new SudokuBoard(space); 
   }

   // will place a number in specified positon
   public static void inNumber(int row, int column, int number) {
        sb.inputNumber(row, column, number);
   }

   // will remove the number from specific positon if not fixed
   public static void reNumber(int row, int column) {
        sb.removeNumber(row, column);
   }

   // displays the state of the board
   public static void visualizeGame() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(sb.getBoxSpace(i, j).toString());
            }
            // after 9 positions, will create a new line
            System.out.println();
        }
        // API stream format 
        // space.stream().map(row -> row.stream().map(BoxSpace::toString).collect(Collectors.joining())).forEach(System.out::println);
   }

   // displays information the the game state
   public static void verifyStatus() {
        // if there is one player input that is incorrect 
        System.out.println("Has errors: " + sb.hasErrors());
        // if every position is filled and correct, then returns true
        System.out.println("Is complete: " + sb.isComplete());

   }

   // removes all non-fixed numbers
   public static void clearInputs() {
        sb.restart();
   }

   // checks whether the player can finish the game (game must be complete)
   public static void endGame() {
        if (sb.isComplete()) {
            System.out.println("Game is complete!");
            return;
        } else {
            // if there are empty or incorrect plays:
            System.out.println("Game is not done!");
        }
   } 
}
