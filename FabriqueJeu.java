package cartes;

/**
 * Interface définissant une fabrique capable de créer des jeux de cartes
 * ainsi que des cartes individuelles.
 */
public interface FabriqueJeu {

    /**
     * Crée un nouveau paquet de cartes selon l'implémentation de la fabrique.
     *
     * @return un nouvel objet {@link Paquet}
     */
    Paquet creerPaquet();

    /**
     * Crée une carte avec la valeur et la couleur spécifiées.
     *
     * @param valeur la valeur de la carte
     * @param couleur la couleur de la carte
     * @return une nouvelle instance de {@link Carte}
     */
    Carte creerCarte(Valeur valeur, Couleur couleur);
}
