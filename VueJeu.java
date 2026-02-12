package blackjack.vue;

import blackjack.modele.Action;
import blackjack.modele.EcouteurActionJeu;
import blackjack.modele.Joueur;
import blackjack.modele.PartieBlackjack;
import blackjack.observer.Ecouteur;

/**
 * Interface représentant la vue du jeu de Blackjack.
 *
 * <p>Cette interface définit les méthodes qu'une interface graphique ou
 * console doit implémenter pour afficher l'état du jeu, interagir avec le
 * joueur et communiquer avec le contrôleur via un {@link EcouteurActionJeu}.</p>
 */
public interface VueJeu extends Ecouteur {

    /**
     * Affiche l'état complet d'une partie de Blackjack.
     *
     * @param partie la {@link PartieBlackjack} à afficher
     */
    void afficherPartie(PartieBlackjack partie);

    /**
     * Affiche un message ou une information à l'utilisateur.
     *
     * @param message le texte à afficher
     */
    void afficherMessage(String message);

    /**
     * Demande à un joueur humain de choisir une action (TIRER, RESTER, DOUBLER, etc.).
     *
     * @param joueur le {@link Joueur} qui doit choisir une action
     * @return l'action choisie par le joueur sous forme de {@link Action}
     */
    Action demanderAction(Joueur joueur);

    /**
     * Demande à un joueur humain de saisir sa mise pour le tour en cours.
     *
     * @param joueur le {@link Joueur} qui doit miser
     * @return la mise saisie par le joueur
     */
    double demanderMise(Joueur joueur);

    /**
     * Ajoute un {@link EcouteurActionJeu} pour écouter les actions du joueur
     * (mise, tirage, rester, doubler, nouveau tour, nouveau jeu).
     *
     * @param ecouteurActionJeu l'écouteur à ajouter
     */
    void ajouterEcouteur(EcouteurActionJeu ecouteurActionJeu);
}
