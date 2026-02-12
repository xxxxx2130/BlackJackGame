package cartes;

import java.util.List;

/**
 * Représente un paquet de cartes, permettant d'ajouter,
 * piocher, mélanger et consulter les cartes qu'il contient.
 */
public interface Paquet {

    /**
     * Ajoute une carte au paquet.
     *
     * @param carte la carte à ajouter
     */
    void ajouterCarte(Carte carte);

    /**
     * Pioche et retire une carte du paquet.
     *
     * @return la carte piochée, ou {@code null} si le paquet est vide
     */
    Carte piocherCarte();

    /**
     * Mélange les cartes du paquet.
     */
    void melangerCartes();

    /**
     * Retourne le nombre de cartes présentes dans le paquet.
     *
     * @return le nombre de cartes
     */
    int getNombreCartes();

    /**
     * Retourne la liste des cartes présentes dans le paquet.
     *
     * @return une liste contenant les cartes du paquet
     */
    List<Carte> getCartes();

    /**
     * Indique si le paquet est vide.
     *
     * @return {@code true} si le paquet ne contient aucune carte,
     *         {@code false} sinon
     */
    boolean estVide();
}
