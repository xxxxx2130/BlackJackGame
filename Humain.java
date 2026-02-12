package blackjack.modele;

import java.util.Scanner;

/**
 * Représente un joueur humain dans une partie de Blackjack.
 * Le joueur humain prend ses décisions via une interface en ligne de commande.
 * Hérite de la classe Joueur pour bénéficier des fonctionnalités de base.
 */
public class Humain extends Joueur {

    /**
     * Constructeur pour créer un joueur humain.
     * @param Nom Le nom du joueur humain
     * @param Sold Le solde initial du joueur humain
     */
    public Humain(String Nom, int Sold) {
        super(Nom, Sold);
    }

    /**
     * Demande au joueur humain de choisir une action de jeu.
     * Affiche un menu d'actions dans la console et lit le choix de l'utilisateur.
     * @return L'action choisie par le joueur (TIRER, RESTER, DOUBLER, etc.)
     *         Retourne null si le choix n'est pas valide
     */
    @Override
    public Action prendreDecision() {
        // Afficher le menu des actions disponibles
        System.out.println("choisir une action \n 1.TIRER,\n" + //
                        "2.RESTER,\n" + //
                        "3.DOUBLER,\n" + //
                        "4.SPLITTER,\n" + //
                        "5.ASSURER\n" + //
                        "}");
        
        Scanner sc = new Scanner(System.in);
        int choix = sc.nextInt();
        
        // Convertir le choix numérique en action correspondante
        switch(choix) {
            case 1 : return Action.TIRER;
            case 2 : return Action.RESTER;
            case 3 : return Action.DOUBLER;
            case 4 : return Action.SPLITTER;
            case 5 : return Action.ASSURER;
            default : return null; // Choix invalide
        }
        // Note: Le scanner n'est pas fermé pour éviter de fermer System.in
    }

    /**
     * Demande au joueur humain de placer une mise.
     * Lit le montant de la mise depuis l'entrée standard.
     * @return Le montant de la mise placée par le joueur
     */
    @Override
    public double placerMise() {
        System.out.println("Combien souhaitez-vous placer pour ce tour ?");
        Scanner sc = new Scanner(System.in);
        int choix = sc.nextInt();
        return choix;
        // Note: Le scanner n'est pas fermé pour éviter de fermer System.in
    }
}