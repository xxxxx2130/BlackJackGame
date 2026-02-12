package cartes;

import java.util.*;

/**
 * Implémentation standard d'un paquet de cartes.
 * Ce paquet permet d'ajouter, piocher, mélanger, vider et consulter des cartes.
 */
public class PaquetStandard implements Paquet {
    private List<Carte> cartes;

    /**
     * Crée un paquet standard vide.
     */
    public PaquetStandard() {
        this.cartes = new ArrayList<>();
    }

    /**
     * Retourne une copie de la liste des cartes du paquet.
     *
     * @return une nouvelle liste contenant les cartes du paquet
     */
    public List<Carte> getCartes() {
        return new ArrayList<>(cartes);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException si la carte est nulle
     */
    @Override
    public void ajouterCarte(Carte carte) {
        if (carte == null) {
            throw new IllegalArgumentException("La carte ne peut pas être null");
        }
        cartes.add(carte);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException si le paquet est vide
     */
    @Override
    public Carte piocherCarte() {
        if (cartes.isEmpty()) {
            throw new IllegalStateException("Le paquet est vide");
        }
        return cartes.remove(cartes.size() - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void melangerCartes() {
        Collections.shuffle(cartes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNombreCartes() {
        return cartes.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean estVide() {
        return cartes.isEmpty();
    }

    /**
     * Donne une représentation textuelle du paquet.
     *
     * @return une chaîne indiquant le nombre de cartes dans le paquet
     */
    public String toString() {
        return "Paquet de " + getNombreCartes() + " cartes";
    }

    /**
     * Vide complètement le paquet.
     */
    public void vider() {
        cartes.clear();
    }
}
