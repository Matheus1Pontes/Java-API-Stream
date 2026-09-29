# Java-API-Stream

A console-based sudoku game written in Java. It uses the Java Stream API for board checks and operations.

## Features

- Display a 9×9 Sudoku board.
- Enter or remove numbers in editable cells.
- Check whether entered numbers match the puzzle’s solution.
- Clear player-entered numbers while keeping the preset clues.
- Check the board’s status.

## Requirements

- Java 8 or higher.

## Run the game

Open a terminal in the project folder and run:

javac BoxSpace.java SudokuBoard.java SudokuGame.java
java SudokuGame

## How to play

Choose an option from the menu. When entering a row or column, use a number from **1 to 9**. 
The game checks whether a number of conflicts with another number in its row, column, or 3×3 box. 
Preset numbers cannot be changed or removed.

Choose **EXIT** to close the game. Choose **CLEAR INPUTS** to remove your entries and keep the preset clues.

## Project files

- `SudokuGame.java` — starts the game, displays the menu, and handles player input
- `SudokuBoard.java` — stores and checks the board
- `BoxSpace.java` — represents one cell on the board
