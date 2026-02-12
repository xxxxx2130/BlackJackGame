package blackjack.modele;

import cartes.Carte;

public interface Strategie{
    
    /**
     * return le montant de la mise
     */
    public double getMise();

    /**
     *  c'est l'action jouer
     * @param main main du joueur
     * @param carteCroupier carte visible du croupier
     * @return action voulu
     */
    public Action getAction(MainJoueur main , Carte carteCroupier);



} 
