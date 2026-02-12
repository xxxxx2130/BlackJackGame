package cartes;

import java.util.Objects;

/**
 * Représente une carte standard comportant une couleur, une valeur
 * et un état de visibilité. Fournit également des méthodes utilitaires
 * pour déterminer si la carte est une figure ou un as.
 */
public class CarteStandard implements Carte {
    private Couleur couleur;
    private Valeur valeur;
    private boolean visible;

    /**
     * Crée une carte standard avec une couleur et une valeur données.
     * La carte est visible par défaut.
     *
     * @param couleur la couleur de la carte
     * @param valeur la valeur de la carte
     */
    public CarteStandard(Couleur couleur, Valeur valeur) {
        this.couleur = couleur;
        this.valeur = valeur;
        this.visible = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Couleur getCouleur() { return couleur; }

    /**
     * {@inheritDoc}
     */
    @Override
    public Valeur getValeur() { return valeur; }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean estVisible() { return visible; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(boolean visible) { this.visible = visible; }

    /**
     * Retourne le symbole Unicode associé à la couleur de la carte.
     *
     * @return un symbole représentant la couleur (♥, ♦, ♣, ♠)
     */
    private String getSymboleCouleur() {
        switch(couleur) {
            case COEUR: return "♥";
            case CARREAU: return "♦";
            case TREFLE: return "♣";
            case PIQUE: return "♠";
            default: return "?";
        }
    }

    /**
     * Retourne le symbole utilisé pour afficher la valeur de la carte.
     * Les figures et l'as utilisent leur lettre correspondante.
     *
     * @return une chaîne représentant la valeur de la carte
     */
    private String getSymboleValeur() {
        switch(valeur) {
            case AS: return "A";
            case VALET: return "J";
            case DAME: return "Q";
            case ROI: return "K";
            default: return String.valueOf(valeur.getValeurNumerique());
        }
    }

    /**
     * Retourne une représentation textuelle de la carte,
     * incluant son symbole de valeur et de couleur si elle est visible.
     * Si elle est cachée, retourne "[X]".
     *
     * @return une représentation textuelle de la carte
     */
    @Override
    public String toString() {
        if (!visible) return "[X]";
        String symboleValeur = getSymboleValeur();
        String symboleCouleur = getSymboleCouleur();
        if (symboleValeur.length() == 1) symboleValeur = " " + symboleValeur;
        return "[" + symboleValeur + symboleCouleur + "]";
    }

    /**
     * Vérifie si une autre carte est égale à celle-ci.
     * Deux cartes sont égales si elles ont la même couleur et la même valeur.
     *
     * @param obj l'objet à comparer
     * @return {@code true} si les cartes sont identiques, {@code false} sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CarteStandard other = (CarteStandard) obj;
        return couleur == other.couleur && valeur == other.valeur;
    }

    /**
     * Calcule le hashcode basé sur la couleur et la valeur de la carte.
     *
     * @return un entier représentant le hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(couleur, valeur);
    }

    /**
     * Indique si la carte est une figure (Valet, Dame ou Roi).
     *
     * @return {@code true} si la carte est une figure, {@code false} sinon
     */
    public boolean estFigure() {
        return valeur == Valeur.VALET || valeur == Valeur.DAME || valeur == Valeur.ROI;
    }

    /**
     * Indique si la carte est un As.
     *
     * @return {@code true} si la carte est un As, {@code false} sinon
     */
    public boolean estAs() {
        return valeur == Valeur.AS;
    }
}
