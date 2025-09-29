package com.proyecto.EscrituraRapida.model;

import java.text.Normalizer;
import java.util.Random;

public class LogicGame {
    private int nivel;
    private int tiempoNivel;
    private final Random random = new Random();

    public LogicGame() {
        nivel = 1;
        tiempoNivel = TimeControl.TIEMPO_INICIAL;
    }

    public String nuevaPalabra() {
        String[] lista = words.getLista();
        return lista[random.nextInt(lista.length)];
    }


    public boolean validar(String escrito, String objetivo) {
        if (escrito == null || objetivo == null) return false;


        String e = escrito.trim();
        String o = objetivo.trim();


        e = Normalizer.normalize(e, Normalizer.Form.NFKC);
        o = Normalizer.normalize(o, Normalizer.Form.NFKC);


        return e.equals(o);
    }

    public void subirNivel() {
        nivel++;
        if (nivel % 5 == 0 && tiempoNivel > TimeControl.TIEMPO_MINIMO) {
            tiempoNivel -= TimeControl.REDUCCION_TIEMPO;
        }
    }

    public int getNivel() {
        return nivel;
    }

    public int getTiempoNivel() {
        return tiempoNivel;
    }
}