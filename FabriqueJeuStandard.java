package cartes;

/**
 * Implémentation standard de la fabrique de jeu.
 * Cette fabrique crée un {@link PaquetStandard} et des {@link CarteStandard}.
 */
public class FabriqueJeuStandard implements FabriqueJeu {

    /**
     * Crée un paquet de cartes standard.
     *
     * @return une nouvelle instance de {@link PaquetStandard}
     */
    @Override
    public Paquet creerPaquet() {
        return new PaquetStandard();
    }

    /**
     * Crée une carte standard avec la valeur et la couleur fournies.
     * Les paramètres ne peuvent pas être nuls.
     *
     * @param valeur la valeur de la carte
     * @param couleur la couleur de la carte
     * @return une nouvelle instance de {@link CarteStandard}
     * @throws IllegalArgumentException si la valeur ou la couleur est nulle
     */
    @Override
    public Carte creerCarte(Valeur valeur, Couleur couleur) {
        if (valeur == null) {
            throw new IllegalArgumentException("La valeur ne peut pas être null");
        }
        if (couleur == null) {
            throw new IllegalArgumentException("La couleur ne peut pas être null");
        }
        return new CarteStandard(couleur, valeur);
    }
}
