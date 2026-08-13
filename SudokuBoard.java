import java.util.List;
import java.util.stream.*;

public class SudokuBoard {
    // 2D List of type BoxSpace
    private List<List<BoxSpace>> space;

    public SudokuBoard(List<List<BoxSpace>> space) {
        this.space = space;
    }

    // will return value in specific row x column
    public BoxSpace getBoxSpace(int row, int column) {
        return space.get(row).get(column);
    }

    // if the number is valid, then return true. Otherwise wrong input or trying to input in a fixed spot
    public boolean inputNumber(int row, int column, int number) {
        if (validNumber(row, column, number)) {
            space.get(row).get(column).setNumber(number);
            return true;
        } else {
            System.out.println("Invalid input! That number is already on the same row or column!");
            return false;
        }
    }
    // if spot is fixed, you can't remove, otherwise, it will remove the number that the user put prior
    public boolean removeNumber(int row, int column) {
        if (!space.get(row).get(column).getFixed()) {
            space.get(row).get(column).setNumber(null);
            return true;
        } else {
            System.out.println("Number is fixed!");
            return false;
        }
    }

    // number in the row can't be null and has to be the same as the input
    public boolean numberExistsRow(int row, int number) {   
        return space.get(row).stream().anyMatch(b -> b.getNumber() != null && b.getNumber() == number);
    }

    // // number in the column can't be null and has to be the same as the input
    public boolean numberExistsColumn(int column, int number) {
        return space.stream().map(r -> r.get(column)).anyMatch(b -> b.getNumber() != null && b.getNumber() == number);
    }

    // helper function to see if that number is valid for the 9 box group 
    public boolean numberExistsSpace(int row, int column, int number) {
        int initialRow = (row / 3) * 3;
        int initialColumn = (column / 3) * 3;
        return space.subList(initialRow, initialRow + 3).stream().flatMap(b -> b.subList(initialColumn, initialColumn + 3).stream()).anyMatch(b -> b.getNumber() != null && b.getNumber() == number);
    }

    // helper function to check if that input number will be valid for the specific spot
    public boolean validNumber(int row, int column, int number) {
        return (number >= 1 && number <= 9 && !numberExistsRow(row, number) && !numberExistsColumn(column, number) && !numberExistsSpace(row, column, number) && !getBoxSpace(row, column).getFixed());
    
    }

    // check all numbers in the List, if any spot is null or isn't the correct answer, then it is an error
    public boolean hasErrors() {
        // existencial quantifier, any x
        return space.stream().flatMap(List::stream).anyMatch(b -> b.getNumber() != null && b.getNumber() != b.getCorrectNumber());
    }

    // to be complete, the List can't be null at any point and can't be different from the correct answer either
    public boolean isComplete() {
        // universal quantifier, all x
        return space.stream().flatMap(List::stream).allMatch(b -> b.getNumber() != null && b.getNumber() != b.getCorrectNumber());
    }

    // this will check to see if the spot is fixed, otherwise, will set all spots to null 
    public void restart() {
        space.stream().flatMap(List::stream).filter(b -> !b.getFixed()).forEach(b -> b.setNumber(null));
    }
}