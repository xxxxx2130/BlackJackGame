package blackjack.modele;

import cartes.*;
import java.util.*;

public class MainJoueur {
    private List<Carte> cartes; 
    private double mise; 

    public MainJoueur() {
        this.cartes = new ArrayList<>();
        this.mise = 0.0;
    }

    public void ajouterCarte(Carte carte) {
        cartes.add(carte);
    }

    public int getValeur() {
        int valeur = 0;
        int nombreAs = 0;

        for (Carte carte : cartes) {
            if(!carte.estVisible())continue;
            Valeur valeurCarte = carte.getValeur();
            if (valeurCarte == Valeur.AS) {
                valeur += 11;
                nombreAs++;
            } else if (valeurCarte == Valeur.VALET || valeurCarte == Valeur.DAME || valeurCarte == Valeur.ROI) {
                valeur += 10;
            } else {
                valeur += valeurCarte.getValeurNumerique();
            }
        }

        // Ajustement pour les As
        while (valeur > 21 && nombreAs > 0) {
            valeur -= 10;
            nombreAs--;
        }

        return valeur;
    }

    public boolean estBlackjack() {
       return cartes.size() == 2 && getValeur() == 21;

    }

    public boolean estBuste() {
        return getValeur() > 21;
    }

    public boolean peutSplit() {
        return cartes.size() == 2 && 
               cartes.get(0).getValeur() == cartes.get(1).getValeur();
    }

    public void clear() {
        // Nettoyer les cartes mais conserver la mise
        // this.mise = 0; // ← NE PAS faire ça ici
        this.cartes.clear();
    }

    public void reinitialiser() {
        cartes.clear();
        mise = 0.0;
    }

    public List<Carte> getCartes() { return cartes; }
    public double getMise() { return mise; }
    public void setMise(double mise) { this.mise = mise; }

    @Override
    public String toString() {
        return "main.Main { " + getCartes() + " } Valeur : " + getValeur();
    }


}