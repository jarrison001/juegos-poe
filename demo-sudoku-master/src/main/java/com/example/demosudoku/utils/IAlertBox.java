package com.example.demosudoku.utils;

import javafx.scene.control.Alert;

/**
 * Defines the contract for an alert box utility used throughout the Sudoku application.
 * Implementations must handle displaying messages to the user in a consistent way.
 */
public interface IAlertBox {

    /**
     * Displays a standard alert dialog to the user.
     *
     * @param headerText  The text to display in the header area of the dialog.
     * @param contentText The main content message of the dialog.
     * @param alertType   The type of alert to display (e.g., ERROR, INFORMATION, WARNING).
     */
    void showAlert(String headerText, String contentText, Alert.AlertType alertType);

    /**
     * Displays an informational alert dialog.
     *
     * @param message The message to display to the user.
     */
    default void showInfo(String message) {
        showAlert("Información", message, Alert.AlertType.INFORMATION);
    }

    /**
     * Displays an error alert dialog.
     *
     * @param message The error message to display.
     */
    default void showError(String message) {
        showAlert("Error", message, Alert.AlertType.ERROR);
    }

    /**
     * Displays a warning alert dialog.
     *
     * @param message The warning message to display.
     */
    default void showWarning(String message) {
        showAlert("Advertencia", message, Alert.AlertType.WARNING);
    }
}
