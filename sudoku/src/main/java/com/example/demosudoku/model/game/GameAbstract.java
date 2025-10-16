package com.example.demosudoku.model.game;

import com.example.demosudoku.model.board.Board;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class that provides the core attributes and structure
 * shared by all Sudoku game implementations.
 * <p>
 * Defines the link between the UI (GridPane) and the game logic (Board).
 */
public abstract class GameAbstract implements IGame {

    /** The JavaFX grid where the Sudoku board is rendered. */
    protected final GridPane boardGridpane;

    /** The Sudoku board model that holds logic and data. */
    protected Board board;

    /** List of TextFields representing interactive cells on the board. */
    protected final List<TextField> numberFields;

    /**
     * Constructs a GameAbstract instance and initializes
     * the Sudoku board and the UI components list.
     *
     * @param boardGridpane The GridPane that will contain the Sudoku cells.
     */
    public GameAbstract(GridPane boardGridpane) {
        this.boardGridpane = boardGridpane;
        this.board = new Board();
        this.numberFields = new ArrayList<>();
    }

    /**
     * Starts or restarts the game.
     * Concrete implementations must define how the board is created
     * and how the UI elements are attached.
     */
    @Override
    public abstract void startGame();

    /**
     * Clears all cells visually and logically, resetting the Sudoku board.
     */
    public void resetBoard() {
        if (boardGridpane != null) {
            boardGridpane.getChildren().clear();
        }
        if (board != null) {
            board.clearBoard();
        }
        numberFields.clear();
    }

    /**
     * Returns the underlying Sudoku board model.
     *
     * @return The current {@link Board} instance.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns all text fields that represent Sudoku cells.
     *
     * @return A list of {@link TextField} elements.
     */
    public List<TextField> getNumberFields() {
        return numberFields;
    }
}
