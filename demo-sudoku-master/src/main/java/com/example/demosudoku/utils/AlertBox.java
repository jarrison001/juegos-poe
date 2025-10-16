package com.example.demosudoku.utils;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

/**
 * Utility class for creating and displaying standardized JavaFX alerts
 * throughout the Sudoku application.
 */
public class AlertBox implements IAlertBox {

    /**
     * Displays a customizable JavaFX alert.
     *
     * @param headerText The header/title text for the alert.
     * @param contentText The main message content to display.
     * @param alertType The type of alert (INFORMATION, ERROR, WARNING, etc.).
     */
    @Override
    public void showAlert(String headerText, String contentText, AlertType alertType) {
        // Ensure the alert runs on the JavaFX Application Thread
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType, contentText, ButtonType.OK);
            alert.setTitle("Sudoku");
            alert.setHeaderText(headerText);
            alert.initStyle(StageStyle.UTILITY); // cleaner window style
            alert.showAndWait();
        });
    }

    /**
     * Shows a confirmation alert and returns true if the user clicks OK.
     *
     * @param question The confirmation message.
     * @return true if confirmed, false otherwise.
     */
    public boolean showConfirmation(String question) {
        final boolean[] result = {false};
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.CONFIRMATION, question, ButtonType.OK, ButtonType.CANCEL);
            alert.setTitle("Confirmar acción");
            alert.setHeaderText("Por favor, confirma tu decisión");
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait().ifPresent(button -> {
                result[0] = (button == ButtonType.OK);
            });
        });
        return result[0];
    }

    /**
     * Convenience method for showing error alerts.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        showAlert("Error", message, AlertType.ERROR);
    }

    /**
     * Convenience method for showing information alerts.
     *
     * @param message The info message to display.
     */
    public void showInfo(String message) {
        showAlert("Información", message, AlertType.INFORMATION);
    }
}
