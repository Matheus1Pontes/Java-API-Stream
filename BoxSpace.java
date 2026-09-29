public class BoxSpace {
    // fixed spot on the board
    private boolean fixed;
    // the number that the player will be placing
    private Integer number;
    // the actual number that is correct for the blank spot
    private int correctNumber;

    // getter and setter methods
    
    public boolean getFixed() {
        return fixed;
    }

    public Integer getNumber() {
        return number;
    }

    public int getCorrectNumber() {
        return correctNumber;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setCorrectNumber(int correctNumber) {
        this.correctNumber = correctNumber;
    }

    // helper function to print the elements of the SudokuBoard
    public String toString() {
        // if player did not place a number yet, then it will print a "." for that spot as a placeholder
        if (getNumber() == null) {
            return " .  ";
        } else {
            // otherwise it will print the number the player placed
            return "{" + getNumber() + "} ";
        }
    }
}
