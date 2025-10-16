package com.example.demosudoku.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the welcome stage (window) of the Sudoku application.
 * This class follows the Singleton pattern to ensure only one instance exists.
 */
public class SudokuWelcomeStage extends Stage {

    /**
     * Private constructor to enforce the Singleton pattern.
     * Loads the FXML view, sets up the scene, and configures the stage properties.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    private SudokuWelcomeStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/demosudoku/sudoku-welcome-view.fxml")
        );
        Parent root = loader.load();

        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Sudoku - Bienvenida");
        setResizable(false);

        // Carga un ícono (opcional)
        Image icon = new Image(String.valueOf(
                getClass().getResource("/com/example/demosudoku/favicon.png")
        ));
        getIcons().add(icon);

        show();
    }

    /**
     * Inner static class to hold the singleton instance (lazy initialization).
     */
    private static class Holder {
        private static SudokuWelcomeStage INSTANCE;
    }

    /**
     * Provides global access to the singleton SudokuWelcomeStage instance.
     * Creates the instance if it doesn't exist yet.
     *
     * @return The single instance of SudokuWelcomeStage.
     * @throws IOException if the FXML file cannot be loaded during the first creation.
     */
    public static SudokuWelcomeStage getInstance() throws IOException {
        if (Holder.INSTANCE == null) {
            Holder.INSTANCE = new SudokuWelcomeStage();
        }
        return Holder.INSTANCE;
    }

    /**
     * Closes the stage and deletes the singleton instance,
     * allowing it to be recreated later.
     */
    public static void deleteInstance() {
        if (Holder.INSTANCE != null) {
            Holder.INSTANCE.close();
            Holder.INSTANCE = null;
        }
    }
}
