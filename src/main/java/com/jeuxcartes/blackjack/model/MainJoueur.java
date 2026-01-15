package com.jeuxcartes.blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class MainJoueur {
    private final List<Carte> cartes = new ArrayList<>();

    public void ajouter(Carte c) { cartes.add(c); }
    public void vider() { cartes.clear(); }
    public List<Carte> getCartes() { return cartes; }

    public int getScore() {
        int total = 0;
        int as = 0;
        for (Carte c : cartes) {
            total += c.getPoints();
            if (c.getPoints() == 1) as++;
        }
        // Logique de l'As (1 ou 11)
        while (as > 0 && total + 10 <= 21) {
            total += 10;
            as--;
        }
        return total;
    }
}
