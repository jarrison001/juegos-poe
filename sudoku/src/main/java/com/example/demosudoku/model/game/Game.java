package com.example.demosudoku.model.game;

import com.example.demosudoku.model.board.Board;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.util.Random;

/**
 * Represents the concrete implementation of the Sudoku game logic.
 * Handles board setup, player input validation, and in-game assistance.
 */
public class Game extends GameAbstract {

    private final Random random = new Random();

    public Game(GridPane boardGridpane) {
        super(boardGridpane);
    }

    @Override
    public void startGame() {
        boardGridpane.getChildren().clear(); // limpia tablero antes de iniciar

        // Crear un nuevo tablero lógico
        board = new Board();

        // Asegurarnos de partir de un tablero vacío (todos ceros)
        clearBoardModel();

        // Generar 2 números válidos por bloque 2x3 (total 12 números)
        generateInitialNumbers();

        // Dibujar tablero con bordes personalizados según bloque
        for (int i = 0; i < board.getBoard().size(); i++) {
            for (int j = 0; j < board.getBoard().get(i).size(); j++) {
                int number = board.getBoard().get(i).get(j);
                TextField textField = new TextField();

                textField.setAlignment(Pos.CENTER);
                textField.setPrefSize(60, 60);

                // 🔹 Determinar bloque (2x3)
                int blockRow = i / 2;  // 0..2
                int blockCol = j / 3;  // 0..1
                int blockIndex = blockRow * 2 + blockCol + 1; // numeración 1..6

                // 🔹 Colores base alternos por bloque
                boolean bloquePar = (blockRow + blockCol) % 2 == 0;
                String colorBase = bloquePar ? "#f2faff" : "#dff3ff";

                // 🔹 Bordes básicos
                String borderColor = "#888888";
                int top = (i % 2 == 0) ? 3 : 1;
                int left = (j % 3 == 0) ? 3 : 1;
                int bottom = (i == 5) ? 3 : 1;
                int right = (j == 5) ? 3 : 1;

                // 🔹 Aplicar borde más fuerte a los bloques 1, 4 y 5
                boolean bloqueFuerte = (blockIndex == 1 || blockIndex == 4 || blockIndex == 5);
                if (bloqueFuerte) {
                    borderColor = "#444444"; // más oscuro
                    top = Math.max(top, 4);
                    left = Math.max(left, 4);
                    bottom = Math.max(bottom, 4);
                    right = Math.max(right, 4);
                }

                // 🔹 Estilo de borde
                String borderStyle = String.format(
                        "-fx-border-color: %s; -fx-border-width: %d %d %d %d;",
                        borderColor, top, right, bottom, left
                );

                // 🔹 Aplicar estilo general
                textField.setStyle(
                        "-fx-font-size: 16px; -fx-text-fill: #142850; " +
                                "-fx-background-color: " + colorBase + "; " + borderStyle
                );

                // 🔹 Si es número base → resaltarlo
                if (number != 0) {
                    textField.setText(String.valueOf(number));
                    textField.setEditable(false);
                    textField.setStyle(
                            "-fx-background-color: #bfe9ff; -fx-font-weight: bold; -fx-font-size: 16px; " +
                                    "-fx-text-fill: #142850; " + borderStyle
                    );
                }

                handleNumberField(textField, i, j);
                boardGridpane.add(textField, j, i);
            }
        }
    }

    /**
     * Limpia el modelo del tablero poniéndolo todo a 0.
     * Esto evita duplicar números si el constructor de Board ya prellenó algo.
     */
    private void clearBoardModel() {
        for (int i = 0; i < board.getBoard().size(); i++) {
            for (int j = 0; j < board.getBoard().get(i).size(); j++) {
                board.getBoard().get(i).set(j, 0);
            }
        }
    }

    /**
     * Genera 2 números válidos en cada bloque 2x3 del tablero (6 bloques → 12 números en total).
     */
    private void generateInitialNumbers() {
        for (int blockRow = 0; blockRow < 3; blockRow++) {      // 3 bloques de alto
            for (int blockCol = 0; blockCol < 2; blockCol++) {  // 2 bloques de ancho
                int placed = 0;
                int attempts = 0;
                while (placed < 2 && attempts < 100) {
                    int row = blockRow * 2 + random.nextInt(2);
                    int col = blockCol * 3 + random.nextInt(3);
                    int num = random.nextInt(6) + 1;
                    attempts++;

                    if (board.getBoard().get(row).get(col) == 0 && board.isValid(row, col, num)) {
                        board.getBoard().get(row).set(col, num);
                        placed++;
                    }
                }
            }
        }
    }

    private void handleNumberField(TextField txt, int row, int col) {
        txt.setOnKeyReleased(event -> {
            String input = txt.getText().trim();

            if (input.isEmpty()) {
                board.getBoard().get(row).set(col, 0);
                txt.setStyle("-fx-background-color: white; -fx-font-size: 16px;");
                return;
            }

            if (!input.matches("[1-6]")) {
                txt.clear();
                board.getBoard().get(row).set(col, 0);
                txt.setStyle("-fx-background-color: white; -fx-font-size: 16px;");
                return;
            }

            int number = Integer.parseInt(input);

            if (board.isValid(row, col, number)) {
                board.getBoard().get(row).set(col, number);
                txt.setStyle("-fx-background-color: #b3ffb3; -fx-border-color: #00cc00; -fx-border-width: 1;");
            } else {
                board.getBoard().get(row).set(col, 0);
                txt.setStyle("-fx-background-color: #ffcccc; -fx-border-color: red; -fx-border-width: 2;");
                txt.clear();
            }
        });
    }

    @Override
    public void requestHint() {
        for (int i = 0; i < board.getBoard().size(); i++) {
            for (int j = 0; j < board.getBoard().get(i).size(); j++) {
                int value = board.getBoard().get(i).get(j);
                if (value == 0) {
                    for (int candidate = 1; candidate <= 6; candidate++) {
                        if (board.isValid(i, j, candidate)) {
                            board.getBoard().get(i).set(j, candidate);

                            for (Node node : boardGridpane.getChildren()) {
                                Integer r = GridPane.getRowIndex(node);
                                Integer c = GridPane.getColumnIndex(node);
                                if ((r != null && r == i) && (c != null && c == j) && node instanceof TextField) {
                                    TextField txt = (TextField) node;
                                    txt.setText(String.valueOf(candidate));
                                    txt.setStyle("-fx-background-color: #b3ffb3; -fx-font-weight: bold;");
                                    txt.setEditable(false);
                                    break;
                                }
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    public void resetBoard() {
        boardGridpane.getChildren().clear();
        board = new Board();
        startGame();
    }
}
