package com.example.demosudoku.model.game;

/**
 * Defines the contract for a Sudoku game class.
 * Implementations must provide methods to start, reset, and assist gameplay.
 */
public interface IGame {

    /**
     * Initializes and starts the Sudoku game logic.
     * Responsible for preparing the board and rendering it in the UI.
     */
    void startGame();

    /**
     * Resets the current game, clearing both the board model and the UI.
     * Optional for simple implementations.
     */
    default void resetGame() {
        // Optional: implemented in concrete class if needed
    }

    /**
     * Provides a hint or suggestion to the player (HU-4).
     * Optional method for help features.
     */
    default void requestHint() {
        // Optional: implemented in concrete class if needed
    }

    /**
     * Validates the current Sudoku board state.
     * @return true if the board is valid according to Sudoku rules.
     */
    default boolean validateBoard() {
        return true;
    }

    /**
     * Checks if the game is complete (no empty cells and all valid).
     * @return true if the Sudoku is solved correctly.
     */
    default boolean isGameComplete() {
        return false;
    }
}
