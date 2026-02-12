package blackjack.modele;

import cartes.*;

public class StrategieBasic implements Strategie {
   // private static final double MISE_BASE = 10.0;

    @Override
    public double getMise() {
        return getRandomNumber(5,50);
    }
    private double getRandomNumber(int min, int max) {
        return (double) ((Math.random() * (max - min)) + min);
    }

    private int getValeurBlackjack(Carte carte) {
         if (carte == null) {
            return 0; 
        }
        
        Valeur valeur = carte.getValeur();
        if (valeur == Valeur.AS) return 11;
        if (valeur == Valeur.VALET || valeur == Valeur.DAME || valeur == Valeur.ROI) return 10;
        return valeur.getValeurNumerique();
    }

    @Override
    public Action getAction(MainJoueur main, Carte carteCroupier) {
        int valeurMain = main.getValeur();
        int valeurCroupier = getValeurBlackjack(carteCroupier);

        if (valeurMain <= 11) {
            return Action.TIRER;
        } else if (valeurMain >= 17) {
            return Action.RESTER;
        } else {
            if (valeurCroupier >= 7) {
                return Action.TIRER;
            } else {
                return Action.RESTER;
            }
        }
    }
}