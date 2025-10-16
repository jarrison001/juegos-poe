package com.example.demosudoku;

import com.example.demosudoku.utils.AlertBox;
import com.example.demosudoku.view.SudokuWelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class for the Sudoku application.
 * This class serves as the entry point for launching the JavaFX application.
 */
public class Main extends Application {

    /**
     * Launches the JavaFX application.
     *
     * @param args command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and displays the welcome screen of the Sudoku game.
     * <p>
     * If the FXML file cannot be loaded, an alert message is shown to the user.
     *
     * @param primaryStage the primary stage provided by JavaFX (not used here, since we manage custom stages).
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Launch the initial welcome screen
            SudokuWelcomeStage.getInstance();
        } catch (IOException e) {
            // Show a friendly error alert instead of a raw stack trace
            new AlertBox().showError("No se pudo cargar la pantalla de bienvenida.\n" +
                    "Detalles del error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Called when the application is closed.
     * Ensures that all resources are released cleanly.
     */
    @Override
    public void stop() {
        System.out.println("Aplicación Sudoku finalizada correctamente.");
    }
}
