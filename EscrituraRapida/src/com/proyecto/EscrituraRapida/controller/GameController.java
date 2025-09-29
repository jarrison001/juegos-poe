package com.proyecto.EscrituraRapida.controller;

import com.proyecto.EscrituraRapida.model.LogicGame;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

public class GameController {

    @FXML private Label labelNivel;
    @FXML private Label labelPalabra;
    @FXML private Label labelTiempo;
    @FXML private Label labelMensaje;
    @FXML private TextField campoTexto;
    @FXML private Button btnValidar;

    private LogicGame logica;
    private Timeline timeline;
    private int tiempoRestante;
    private String palabraObjetivo;

    @FXML
    public void initialize() {
        logica = new LogicGame();
        iniciarNivel();
    }

    private void iniciarNivel() {
        campoTexto.clear();
        campoTexto.setDisable(false);
        btnValidar.setDisable(false);
        campoTexto.requestFocus();

        palabraObjetivo = logica.nuevaPalabra();
        labelPalabra.setText(palabraObjetivo);
        labelNivel.setText("Nivel: " + logica.getNivel());

        tiempoRestante = logica.getTiempoNivel();
        labelTiempo.setText("Tiempo: " + tiempoRestante);
        labelMensaje.setText("");

        if (timeline != null) timeline.stop();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            tiempoRestante--;
            labelTiempo.setText("Tiempo: " + tiempoRestante);

            if (tiempoRestante <= 0) {
                timeline.stop();
                finDelTiempo();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void validarPalabra() {
        String escrito = campoTexto.getText();

        if (logica.validar(escrito, palabraObjetivo)) {
            labelMensaje.setText("¡Correcto! Nivel superado.");
            timeline.stop();
            logica.subirNivel();
            iniciarNivel();
        } else {

            labelMensaje.setText("Incorrecto, intenta de nuevo.");
            campoTexto.clear();
            campoTexto.requestFocus();
        }
    }

    private void finDelTiempo() {
        String escrito = campoTexto.getText();

        if (logica.validar(escrito, palabraObjetivo)) {
            labelMensaje.setText("¡Correcto en el último segundo! Nivel superado.");
            logica.subirNivel();
            iniciarNivel();
        } else {
            labelMensaje.setText("Tiempo agotado. Juego terminado.");
            campoTexto.setDisable(true);
            btnValidar.setDisable(true);
        }
    }
}