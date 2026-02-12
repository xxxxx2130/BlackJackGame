package cartes;

/**
 * Représente une carte possédant une couleur, une valeur et un état de visibilité.
 */
public interface Carte {

    /**
     * Retourne la couleur de la carte.
     *
     * @return la {@link Couleur} de la carte
     */
    Couleur getCouleur();

    /**
     * Retourne la valeur de la carte.
     *
     * @return la {@link Valeur} de la carte
     */
    Valeur getValeur();

    /**
     * Indique si la carte est visible.
     *
     * @return {@code true} si la carte est visible, {@code false} sinon
     */
    boolean estVisible();

    /**
     * Définit si la carte est visible ou non.
     *
     * @param visible {@code true} pour rendre la carte visible, {@code false} pour la cacher
     */
    void setVisible(boolean visible);

    /**
     * Retourne une représentation textuelle de la carte.
     *
     * @return une chaîne décrivant la carte
     */
    @Override
    String toString();
}
