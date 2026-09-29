import java.util.List;
import java.util.stream.*;

public class SudokuBoard {
    // 2D List of type BoxSpace
    private List<List<BoxSpace>> space;

    public SudokuBoard(List<List<BoxSpace>> space) {
        this.space = space;
    }

    // will return value in specific row by column
    public BoxSpace getBoxSpace(int row, int column) {
        return space.get(row).get(column);
    }

    // if the number is valid, then return true, otherwise wrong input or trying to input in a fixed spot
    public boolean inputNumber(int row, int column, int number) {
        if (validNumber(row, column, number)) {
            space.get(row).get(column).setNumber(number);
            return true;
        } else {
            System.out.println("Invalid input! That number is already on the same row or column!");
            return false;
        }
    }

    // if the spot is fixed, you can't remove the number, otherwise it will remove the number that the user put prior
    public boolean removeNumber(int row, int column) {
        if (!(space.get(row).get(column).getFixed())) {
            space.get(row).get(column).setNumber(null);
            return true;
        } else {
            System.out.println("Number is fixed!");
            return false;
        }
    }

    // number in the row can't be null and has to be the same as the input
    public boolean numberExistsRow(int row, int number) {   
        return space.get(row).stream()
                .anyMatch(board -> board.getNumber() != null && board.getNumber() == number);
    }

    // number in the column can't be null and has to be the same as the input
    public boolean numberExistsColumn(int column, int number) {
        return space.stream()
                .map(row -> row.get(column))
                .anyMatch(board -> board.getNumber() != null && board.getNumber() == number);
    }

    // helper function to see if that number is valid for the 9 box group 
    public boolean numberExistsSpace(int row, int column, int number) {
        // this is checking the first 3x3 grid on the board
        int initialRow = (row / 3) * 3;
        int initialColumn = (column / 3) * 3;
        // iterating over rows
        return space.subList(initialRow, initialRow + 3)
                .stream()
                // iterating over columns
                .flatMap(board -> board.subList(initialColumn, initialColumn + 3)
                        .stream())
                // checking for valid number
                .anyMatch(board -> board.getNumber() != null && board.getNumber() == number);
    }

    // helper function to check if that input number will be valid for the specific spot
    public boolean validNumber(int row, int column, int number) {
        // number must be from 1 to 9
        return (number >= 1 && number <= 9
                // must be empty row
                && !numberExistsRow(row, number)
                // must be empty column
                && !numberExistsColumn(column, number)
                // must be valid for the grid
                && !numberExistsSpace(row, column, number)
                // finally, it must not be fixed,a preset number
                && !getBoxSpace(row, column).getFixed());
    
    }

    // check all numbers in the List, if any spot is null or isn't the correct answer, then it has an error
    public boolean hasErrors() {
        // existence quantifier, any x
        return space.stream()
                // for each item in the List
                .flatMap(List::stream)
                // if *ANY* spot is not null and the incorrect option, then it has an error
                .anyMatch(board -> board.getNumber() != null
                        && board.getNumber() != board.getCorrectNumber());
    }

    // to be complete, the List can't be null at any point and can't be different from the correct answer either
    public boolean isComplete() {
        // universal quantifier, all x
        return space.stream()
                // for each item in the List
                .flatMap(List::stream)
                // if *EVERY* spot is not null and the correct option, then it is complete
                .allMatch(board -> board.getNumber() != null
                        && board.getNumber() != board.getCorrectNumber());
    }

    // this will check to see if the spot is fixed, otherwise, will set all spots to null 
    public void restart() {
        space.stream()
                // for each item in the List
                .flatMap(List::stream)
                // if it isn't a fixed spot
                .filter(board -> !board.getFixed())
                // set that spot to null
                .forEach(board -> board.setNumber(null));
    }

}