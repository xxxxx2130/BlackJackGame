package blackjack.observer;
/*
 * Interface pour le pattern Observer
 */

 public interface Ecoutable {
    void ajouterEcouteur(Ecouteur e);
    void retirerEcouteur(Ecouteur e);
}



