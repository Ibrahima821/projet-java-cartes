package com.jeuxcartes.blackjack.model;
import java.util.*;

public class Paquet {
    private final Stack<Carte> cartes = new Stack<>();

    public Paquet() { initialiser(); }

    public void initialiser() {
        cartes.clear();
        for (Carte.Couleur c : Carte.Couleur.values()) {
            for (Carte.Valeur v : Carte.Valeur.values()) {
                cartes.push(new Carte(v, c));
            }
        }
        Collections.shuffle(cartes);
    }

    public Carte tirer() { return cartes.isEmpty() ? null : cartes.pop(); }
}