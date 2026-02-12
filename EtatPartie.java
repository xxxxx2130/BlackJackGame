package blackjack.modele;

/**
 * Représente l'état actuel d'une partie de Blackjack.
 * Gère la phase du jeu en cours et le joueur actuellement en action.
 * Permet de suivre la progression de la partie et de contrôler le flux du jeu.
 */
public class EtatPartie {
    
    // Phase actuelle du jeu (MISE, DISTRIBUTION, etc.)
    private PhaseJeu phase;
    
    // Joueur dont c'est actuellement le tour
    private Joueur joueurActuel;

    /**
     * Constructeur initialisant l'état d'une nouvelle partie.
     * La partie commence toujours par la phase de MISE.
     */
    public EtatPartie() {
        this.phase = PhaseJeu.MISE;
        this.joueurActuel = null;
    }

    /**
     * Modifie la phase actuelle du jeu.
     * @param phase La nouvelle phase du jeu
     */
    public void setPhase(PhaseJeu phase) {
        this.phase = phase;
    }

    /**
     * @return La phase actuelle du jeu
     */
    public PhaseJeu getPhase() {
        return phase;
    }

    /**
     * Définit le joueur dont c'est actuellement le tour.
     * @param joueur Le joueur actif
     */
    public void setJoueurActuel(Joueur joueur) {
        this.joueurActuel = joueur;
    }

    /**
     * @return Le joueur actuellement en action
     */
    public Joueur getJoueurActuel() {
        return joueurActuel;
    }

    /**
     * Vérifie si le tour actuel (manche) est terminé.
     * Un tour est terminé quand on arrive à la phase RESULTAT.
     * @return true si le tour est terminé, false sinon
     */
    public boolean estTourTerminee() {
       return phase == PhaseJeu.RESULTAT;
    }

    /**
     * Vérifie si la partie complète est terminée.
     * La partie est terminée quand on arrive à la phase TERMINER.
     * @return true si la partie est terminée, false sinon
     */
    public boolean estPhaseTerminee(){
        return phase == PhaseJeu.TERMINER;
    }

    /**
     * Termine ou continue la partie selon la valeur du booléen.
     * @param bool Si true, termine la partie ; si false, passe à la phase du croupier
     */
    public void setPhaseTerminee(boolean bool){
        if(bool) {
            this.phase = PhaseJeu.TERMINER;
        } else {
            this.phase = PhaseJeu.JEU_CROUPIER;
        }
    }

    /**
     * @return true si la partie est en phase de mise, false sinon
     */
    public boolean estPhaseMise() {
        return phase == PhaseJeu.MISE;
    }

    /**
     * @return true si la partie est en phase de jeu des joueurs, false sinon
     */
    public boolean estPhaseJoueurs() {
        return phase == PhaseJeu.JEU_JOUEURS;
    }

    /**
     * @return true si la partie est en phase de jeu du croupier, false sinon
     */
    public boolean estPhaseCroupier() {
        return phase == PhaseJeu.JEU_CROUPIER;
    }

    /**
     * Passe à la phase suivante du déroulement standard d'une manche.
     * L'ordre des phases est : MISE → DISTRIBUTION → JEU_JOUEURS → JEU_CROUPIER → RESULTAT
     */
    public void phaseSuivante() {
        switch(phase) {
            case MISE -> phase = PhaseJeu.DISTRIBUTION;
            case DISTRIBUTION -> phase = PhaseJeu.JEU_JOUEURS;
            case JEU_JOUEURS -> phase = PhaseJeu.JEU_CROUPIER;
            case JEU_CROUPIER -> phase = PhaseJeu.RESULTAT;
            case RESULTAT -> {} // Ne fait rien, reste en RESULTAT
        }
    }

    /**
     * Représentation textuelle de l'état de la partie.
     * @return Une chaîne décrivant la phase et le joueur actuel
     */
    @Override
    public String toString() {
        return "Phase: " + phase + ", Joueur: " + 
               (joueurActuel != null ? joueurActuel.getNom() : "Aucun");
    }
}