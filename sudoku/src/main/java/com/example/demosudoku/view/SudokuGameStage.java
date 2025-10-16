package com.example.demosudoku.view;

import com.example.demosudoku.controller.SudokuGameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the main stage (window) of the Sudoku game.
 * This class follows the Singleton pattern to ensure only one game window exists.
 */
public class SudokuGameStage extends Stage {

    private final SudokuGameController controller;

    /**
     * Private constructor to enforce the singleton pattern.
     * Loads the FXML view, sets up the scene, and configures the stage properties.
     *
     * @throws IOException if the FXML file cannot be loaded.
     */
    private SudokuGameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/demosudoku/sudoku-game-view.fxml")
        );
        Parent root = loader.load();
        controller = loader.getController();

        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Sudoku");
        setResizable(false);

        // Optional: agrega un icono si está disponible
        Image icon = new Image(String.valueOf(
                getClass().getResource("/com/example/demosudoku/favicon.png")
        ));
        getIcons().add(icon);

        show();
    }

    /**
     * Returns the controller associated with this stage's view.
     *
     * @return The SudokuGameController instance.
     */
    public SudokuGameController getController() {
        return controller;
    }

    /**
     * Inner static class to hold the singleton instance (lazy initialization).
     */
    private static class Holder {
        private static SudokuGameStage INSTANCE;
    }

    /**
     * Provides global access to the singleton SudokuGameStage instance.
     * Creates the instance if it doesn't exist yet.
     *
     * @return The single instance of SudokuGameStage.
     * @throws IOException if the FXML file cannot be loaded during the first creation.
     */
    public static SudokuGameStage getInstance() throws IOException {
        if (Holder.INSTANCE == null) {
            Holder.INSTANCE = new SudokuGameStage();
        }
        return Holder.INSTANCE;
    }

    /**
     * Closes the stage and deletes the current instance.
     * This allows reopening the window cleanly in a future session.
     */
    public static void deleteInstance() {
        if (Holder.INSTANCE != null) {
            Holder.INSTANCE.close();
            Holder.INSTANCE = null;
        }
    }
}
