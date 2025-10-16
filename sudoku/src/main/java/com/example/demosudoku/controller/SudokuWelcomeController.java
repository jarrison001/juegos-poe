package com.example.demosudoku.controller;

import com.example.demosudoku.model.user.User;
import com.example.demosudoku.utils.AlertBox;
import com.example.demosudoku.view.SudokuGameStage;
import com.example.demosudoku.view.SudokuWelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller for the welcome screen (sudoku-welcome-view.fxml).
 * Handles user interactions for starting a new Sudoku game.
 */
public class SudokuWelcomeController {

    @FXML
    private TextField nicknameTxt;

    /**
     * Handles the "Play" button action.
     * Validates the nickname, creates a user, and transitions to the game view.
     *
     * @param event the action event triggered by clicking the button.
     * @throws IOException if the SudokuGameStage cannot be loaded.
     */
    @FXML
    void handlePlay(ActionEvent event) throws IOException {
        String nickname = nicknameTxt.getText().trim();

        // 🔹 Validación mejorada: evita espacios y nombres vacíos
        if (nickname.isEmpty() || nickname.length() < 3) {
            new AlertBox().showAlert("Error", "Por favor ingresa un nombre de al menos 3 caracteres.", Alert.AlertType.ERROR);
            return;
        }

        // 🔹 Crea el usuario y lo pasa al controlador del juego
        User user = new User(nickname);
        SudokuGameStage gameStage = SudokuGameStage.getInstance();
        gameStage.getController().setUser(user);


    }

    /**
     * Optional: Handles the Enter key event on the nickname field
     * to start the game without clicking the button.
     */
    @FXML
    private void onEnterPressed(ActionEvent event) throws IOException {
        handlePlay(event);
    }
}
