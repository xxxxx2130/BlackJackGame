package blackjack.modele;

/**
 * Interface définissant les écouteurs d'événements du jeu Blackjack.
 * Permet à des observateurs d'être notifiés des actions importantes
 * survenant pendant une partie.
 * Cette interface suit le pattern Observer pour la gestion des événements.
 */
public interface EcouteurActionJeu {
    
    /**
     * Méthode appelée lorsqu'un joueur effectue une action de jeu.
     * @param joueur Le joueur qui a effectué l'action
     * @param action L'action effectuée (TIRER, RESTER, DOUBLER, etc.)
     */
    void onAction(Joueur joueur, Action action);
    
    /**
     * Méthode appelée lorsqu'un joueur place une mise.
     * @param joueur Le joueur qui a placé la mise
     * @param mise Le montant de la mise placée
     */
    void onMise(Joueur joueur, double mise);
    
    /**
     * Méthode appelée lorsqu'une nouvelle partie commence.
     * Permet de réinitialiser l'affichage ou l'état des observateurs.
     */
    void onNouveauJeu();
    
    /**
     * Méthode appelée lorsqu'un nouveau tour (manche) commence.
     * Un tour comprend toutes les phases : mise, distribution, jeu, résultat.
     */
    void onNouveauTour();
}