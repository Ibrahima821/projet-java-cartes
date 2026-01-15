package com.jeuxcartes.blackjack.model;

public class Carte {
    // Définition des Couleurs
    public enum Couleur { 
        COEUR, CARREAU, TREFLE, PIQUE 
    }

    // Définition des Valeurs avec leurs points
    public enum Valeur {
        AS(1), DEUX(2), TROIS(3), QUATRE(4), CINQ(5), SIX(6),
        SEPT(7), HUIT(8), NEUF(9), DIX(10), VALET(10), DAME(10), ROI(10);
        
        public final int points;
        Valeur(int p) { this.points = p; }
    }

    private final Valeur valeur;
    private final Couleur couleur;

    public Carte(Valeur v, Couleur c) { 
        this.valeur = v; 
        this.couleur = c; 
    }
    
    public int getPoints() { return valeur.points; }
    
    /**
     * Cette méthode convertit le nom Java (ex: ROI de COEUR)
     * vers le nom de fichier image (ex: "K-H.png")
     */
    public String getImageName() {
        String vStr;
        String cStr = "";

        // 1. Traduction de la VALEUR (Value)
        switch (valeur) {
            case AS:    vStr = "A"; break;
            case VALET: vStr = "J"; break; // Jack
            case DAME:  vStr = "Q"; break; // Queen
            case ROI:   vStr = "K"; break; // King
            default:    vStr = String.valueOf(valeur.points); // Cas 2, 3, ... 10
        }

        // 2. Traduction de la COULEUR (Suit)
        switch (couleur) {
            case COEUR:   cStr = "H"; break; // Hearts
            case CARREAU: cStr = "D"; break; // Diamonds
            case TREFLE:  cStr = "C"; break; // Clubs
            case PIQUE:   cStr = "S"; break; // Spades
        }

        // 3. Assemblage final : "K-H.png" ou "10-D.png"
        return vStr + "-" + cStr + ".png";
    }
    
    @Override 
    public String toString() { 
        return valeur + " de " + couleur; 
    }
}