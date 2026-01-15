package com.jeuxcartes.blackjack.controller;
import com.jeuxcartes.blackjack.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class GameController {

    @FXML private HBox dealerCardsBox, playerCardsBox;
    @FXML private Label dealerScoreLabel, playerScoreLabel, messageLabel;
    @FXML private Button btnHit, btnStand, btnRestart;

    private Paquet paquet;
    private MainJoueur mainJoueur, mainCroupier;

    @FXML
    public void initialize() {
        paquet = new Paquet();
        mainJoueur = new MainJoueur();
        mainCroupier = new MainJoueur();
        startNewGame();
    }

    private void startNewGame() {
        paquet.initialiser();
        mainJoueur.vider();
        mainCroupier.vider();
        
        dealerCardsBox.getChildren().clear();
        playerCardsBox.getChildren().clear();
        messageLabel.setText("Bienvenue ! Appuie sur TIRER ou RESTER.");
        
        btnHit.setDisable(false);
        btnStand.setDisable(false);
        btnRestart.setVisible(false);

        // Distribution initiale
        tirerPourJoueur();
        tirerPourJoueur();
        tirerPourCroupier(); 
        
        updateUI();
    }

    private void tirerPourJoueur() {
        Carte c = paquet.tirer();
        mainJoueur.ajouter(c);
        afficherCarte(c, playerCardsBox);
    }
    
    private void tirerPourCroupier() {
        Carte c = paquet.tirer();
        mainCroupier.ajouter(c);
        afficherCarte(c, dealerCardsBox);
    }

    private void afficherCarte(Carte c, HBox box) {
        try {
            // Charge l'image depuis les resources
        	String path = "/com/jeuxcartes/blackjack/images/" + c.getImageName();
            Image img = new Image(getClass().getResourceAsStream(path));
            ImageView view = new ImageView(img);
            view.setFitHeight(100);
            view.setPreserveRatio(true);
            box.getChildren().add(view);
        } catch (Exception e) {
            System.err.println("Erreur image: " + c.getImageName());
            box.getChildren().add(new Label(c.toString())); // Fallback textuel
        }
    }

    @FXML
    private void onHit() {
        tirerPourJoueur();
        updateUI();
        if (mainJoueur.getScore() > 21) {
            gameOver("PERDU ! Vous avez sauté.");
        }
    }

    @FXML
    private void onStand() {
        btnHit.setDisable(true);
        btnStand.setDisable(true);
        
        // IA DU CROUPIER (Animation)
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (mainCroupier.getScore() < 17) {
                tirerPourCroupier();
                dealerScoreLabel.setText("Score Croupier: " + mainCroupier.getScore());
            }
        }));
        
        timeline.setCycleCount(Timeline.INDEFINITE);
        // Condition d'arrêt dynamique : on vérifie à chaque cycle si on doit arrêter
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1.1), e -> {
            if (mainCroupier.getScore() >= 17) {
                timeline.stop();
                checkWinner();
            }
        }));
        
        timeline.play();
    }

    private void checkWinner() {
        int pScore = mainJoueur.getScore();
        int dScore = mainCroupier.getScore();
        
        if (dScore > 21) gameOver("GAGNÉ ! Le croupier a sauté.");
        else if (pScore > dScore) gameOver("GAGNÉ !");
        else if (pScore == dScore) gameOver("ÉGALITÉ.");
        else gameOver("PERDU ! Le croupier gagne.");
    }

    private void gameOver(String msg) {
        messageLabel.setText(msg);
        btnHit.setDisable(true);
        btnStand.setDisable(true);
        btnRestart.setVisible(true);
    }
    
    @FXML private void onRestart() { startNewGame(); }

    private void updateUI() {
        playerScoreLabel.setText("Score: " + mainJoueur.getScore());
        dealerScoreLabel.setText("Score: " + mainCroupier.getScore());
    }
}