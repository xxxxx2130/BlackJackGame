package blackjack.modele;

import cartes.*;

/**
 * Représente le croupier dans une partie de Blackjack.
 * Le croupier gère la distribution des cartes, suit des règles spécifiques
 * pour jouer sa main, et interagit avec le paquet de cartes.
 */
public class Croupier {

    // Main du croupier (cartes qu'il a reçues)
    private MainJoueur main;
    
    // Paquet de cartes utilisé pour distribuer
    private Paquet paquet;

    /**
     * Constructeur pour créer un croupier.
     * @param paquet Le paquet de cartes que le croupier utilisera pour distribuer
     */
    public Croupier(Paquet paquet) {
        this.paquet = paquet;
        this.main = new MainJoueur();
    }

    /**
     * Joue un tour pour le croupier en ajoutant une carte à sa main.
     * @param carte La carte à ajouter à la main du croupier
     */
    public void jouerTour(Carte carte) {
        this.main.ajouterCarte(carte);
    }

    /**
     * Distribue une carte depuis le paquet.
     * La carte est automatiquement rendue visible.
     * @return La carte piochée et rendue visible
     */
    public Carte distribuerCarte() {
        Carte carte = this.paquet.piocherCarte();
        carte.setVisible(true);
        return carte;
    }

    /**
     * Vérifie si le croupier a un Blackjack.
     * Un Blackjack = exactement 2 cartes avec une valeur totale de 21.
     * @return true si le croupier a un Blackjack, false sinon
     */
    public boolean aBlackjack() {
        return main.estBlackjack();
    }

    /**
     * Vérifie si le croupier est buste (dépasse 21).
     * @return true si la valeur de la main > 21, false sinon
     */
    public boolean estBuste() {
        return main.estBuste();
    }

    /**
     * Réinitialise la main du croupier pour une nouvelle manche.
     * Crée une nouvelle main vide.
     */
    public void reinitialiserMain() {
        this.main = new MainJoueur();
    }

    // =============== GETTERS ===============
    
    /** @return La main actuelle du croupier */
    public MainJoueur getMain() {
        return this.main;
    }

    /** @return La valeur actuelle de la main du croupier */
    public int getValeurMain() {
        return main.getValeur();
    }
}