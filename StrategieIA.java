package blackjack.modele;

import cartes.*;

public class StrategieIA implements Strategie {

    @Override
    public double getMise() {
        return getRandomNumber(5,50);
    }
    private double getRandomNumber(int min, int max) {
        return (double) ((Math.random() * (max - min)) + min);
    }

    private int getValeurBlackjack(Carte carte) {
        if (carte == null) {
            return 2;//valeur asuré 
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
        }

        if (valeurMain >= 17) {
            return Action.RESTER;
        }

        if (valeurMain >= 12 && valeurMain <= 16) {
    if (valeurCroupier >= 7) {
        return Action.TIRER;
    } else if (valeurCroupier >= 2 && valeurCroupier <= 6) {  // ✅ CORRIGÉ
        return Action.RESTER;
    } else if (valeurCroupier == 1) { // As
        return Action.TIRER;
    }
}

        return Action.RESTER;
    }
}