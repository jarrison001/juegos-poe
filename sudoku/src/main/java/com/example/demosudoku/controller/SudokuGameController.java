package com.example.demosudoku.controller;

import com.example.demosudoku.model.game.Game;
import com.example.demosudoku.model.user.User;
import com.example.demosudoku.utils.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the main Sudoku game view (sudoku-game-view.fxml).
 * Manages board initialization, user data, and UI actions (Hint / Reset).
 */
public class SudokuGameController implements Initializable {

    /** The GridPane element from the FXML file that holds the Sudoku board cells. */
    @FXML
    private GridPane boardGridPane;

    /** Label that displays the current player's nickname. */
    @FXML
    private Label playerNameLbl;

    /** Button for requesting a hint. */
    @FXML
    private Button btnHint;

    /** Button for resetting the game. */
    @FXML
    private Button btnReset;

    /** The current game logic instance. */
    private Game game;

    /** The user currently playing the game. */
    private User user;

    /**
     * Initializes the controller class after the FXML has been loaded.
     * Creates a new game instance and starts it.
     *
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize game logic and display board
        game = new Game(boardGridPane);
        game.startGame();
    }

    /**
     * Sets the current user for this game session.
     * Called from the welcome controller after login.
     *
     * @param user The user object containing player data.
     */
    public void setUser(User user) {
        this.user = user;
        if (playerNameLbl != null && user != null) {
            playerNameLbl.setText("Jugador: " + user.getNickname());
        }
    }

    /**
     * Handles the "Hint" button action.
     * Requests a hint from the game logic (HU-4).
     *
     * @param event The action event triggered by clicking the Hint button.
     */
    @FXML
    private void handleHint(ActionEvent event) {
        try {
            game.requestHint();
        } catch (UnsupportedOperationException e) {
            new AlertBox().showAlert(
                    "Ayuda no disponible",
                    "La funcionalidad de ayuda aún no está implementada.",
                    javafx.scene.control.Alert.AlertType.INFORMATION
            );
        }
    }

    /**
     * Handles the "Reset" button action.
     * Clears the board and starts a new game (HU-5).
     *
     * @param event The action event triggered by clicking the Reset button.
     */
    @FXML
    private void handleReset(ActionEvent event) {
        game.resetBoard();
        game.startGame();
        new AlertBox().showAlert(
                "Reinicio",
                "Se ha iniciado una nueva partida.",
                javafx.scene.control.Alert.AlertType.INFORMATION
        );
    }
}
