package com.example.demosudoku.model.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Represents a 6x6 Sudoku board divided into 2x3 blocks.
 * Provides methods to generate, validate, and manage Sudoku logic.
 * <p>
 * Each cell may contain a number between 1 and 6 (0 means empty).
 * The board ensures that numbers do not repeat in rows, columns, or 2x3 blocks.
 * <p>
 * Java JDK 17.
 */
public class Board implements IBoard {

    private static final int SIZE = 6;
    private static final int BLOCK_ROWS = 2;
    private static final int BLOCK_COLS = 3;

    private final List<List<Integer>> board;
    private final Random random = new Random();

    /**
     * Initializes a new Sudoku board and generates an initial valid configuration.
     */
    public Board() {
        board = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            List<Integer> row = new ArrayList<>(Collections.nCopies(SIZE, 0));
            board.add(row);
        }
        // Fill each 2x3 block with one number randomly
        fillBlocks(0);
    }

    // ------------------------------------------------------------------------
    // -------------------- Board Generation Logic ----------------------------
    // ------------------------------------------------------------------------

    @Override
    public boolean fillBlocks(int blockIndex) {
        int totalBlocks = (SIZE / BLOCK_ROWS) * (SIZE / BLOCK_COLS);
        if (blockIndex == totalBlocks) return true;

        int blockRow = blockIndex / (SIZE / BLOCK_COLS);
        int blockCol = blockIndex % (SIZE / BLOCK_COLS);
        int startRow = blockRow * BLOCK_ROWS;
        int startCol = blockCol * BLOCK_COLS;

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) numbers.add(i);
        Collections.shuffle(numbers, random);

        for (int i = startRow; i < startRow + BLOCK_ROWS; i++) {
            for (int j = startCol; j < startCol + BLOCK_COLS; j++) {
                for (Integer number : numbers) {
                    if (isValidMove(i, j, number)) {
                        board.get(i).set(j, number);
                        if (fillBlocks(blockIndex + 1)) return true;
                        board.get(i).set(j, 0); // backtrack
                    }
                }
            }
        }
        return false;
    }

    // ------------------------------------------------------------------------
    // -------------------- Validation Logic ----------------------------------
    // ------------------------------------------------------------------------

    /**
     * Checks whether placing a value in (row, col) follows Sudoku rules.
     *
     * @param row   row index
     * @param col   column index
     * @param value value to check
     * @return true if valid, false otherwise
     */
    @Override
    public boolean isValid(int row, int col, int value) {
        return isValidMove(row, col, value);
    }

    /**
     * Validates a move according to Sudoku rules (no repetition in row, column, block).
     */
    public boolean isValidMove(int row, int col, int value) {
        if (value < 1 || value > SIZE) return false;

        // Row check
        for (int j = 0; j < SIZE; j++) {
            if (j != col && board.get(row).get(j) == value) return false;
        }
        // Column check
        for (int i = 0; i < SIZE; i++) {
            if (i != row && board.get(i).get(col) == value) return false;
        }
        // Block check
        int blockRow = (row / BLOCK_ROWS) * BLOCK_ROWS;
        int blockCol = (col / BLOCK_COLS) * BLOCK_COLS;
        for (int i = blockRow; i < blockRow + BLOCK_ROWS; i++) {
            for (int j = blockCol; j < blockCol + BLOCK_COLS; j++) {
                if (!(i == row && j == col) && board.get(i).get(j) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Validates the entire Sudoku board.
     *
     * @return true if the board is currently valid.
     */
    public boolean isBoardValid() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                int v = board.get(r).get(c);
                if (v != 0 && !isValidMove(r, c, v)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if the board is fully filled and valid.
     */
    public boolean isComplete() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                int v = board.get(r).get(c);
                if (v == 0 || !isValidMove(r, c, v)) return false;
            }
        }
        return true;
    }

    // ------------------------------------------------------------------------
    // -------------------- Gameplay Helpers ----------------------------------
    // ------------------------------------------------------------------------

    /**
     * Gets a list of possible valid numbers that can be placed in the given cell.
     *
     * @param row row index
     * @param col column index
     * @return list of valid candidates
     */
    public List<Integer> getValidCandidates(int row, int col) {
        List<Integer> candidates = new ArrayList<>();
        if (board.get(row).get(col) != 0) return candidates;
        for (int v = 1; v <= SIZE; v++) {
            if (isValidMove(row, col, v)) candidates.add(v);
        }
        return candidates;
    }

    /**
     * Updates the value in a cell (used for player input).
     *
     * @param row   row index
     * @param col   column index
     * @param value value to place (0 clears the cell)
     */
    public void setCellValue(int row, int col, int value) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            board.get(row).set(col, value);
        }
    }

    /**
     * Returns the value in the specified cell.
     *
     * @param row row index
     * @param col column index
     * @return integer value (0 if empty)
     */
    public int getCellValue(int row, int col) {
        return board.get(row).get(col);
    }

    /**
     * Returns the internal 6x6 board representation.
     */
    public List<List<Integer>> getBoard() {
        return board;
    }

    /**
     * Clears the entire board (sets all cells to 0).
     */
    public void clearBoard() {
        for (List<Integer> row : board) {
            Collections.fill(row, 0);
        }
    }
}
