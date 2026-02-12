package blackjack.vue ;

import java.awt.event.ActionListener;
import java.util.Scanner;

import blackjack.modele.*;
import cartes.vue.*;


public class VueConsole implements VueJeu{

    private Scanner scanner ;
    private VuePaquetConsole vuepaquet;
    private PartieBlackjack partie;

    public VueConsole(PartieBlackjack partie, VuePaquetConsole vuepaquet){
        this.scanner = new Scanner(System.in);
        this.partie = partie;
        this.vuepaquet = vuepaquet;
    }

    @Override
    public void afficherPartie(PartieBlackjack partie) {
        this.partie = partie;
    }

    @Override
    public void  afficherMessage(String message){
        System.out.println(message);

    }

    @Override
    public Action demanderAction(Joueur joueur) {
        System.out.println("Quelle action voulez vous faire ?");
        System.out.println("1. Tirer(Prendre une carte)");
        System.out.println("2. Rester(Garder votre carte)");


        MainJoueur mainJoueur = joueur.getMainJoueur();
        if(mainJoueur.getCartes().size() == 2){
            if(joueur.getSold() >= mainJoueur.getMise()){
                System.out.println("3. DOUBLER (Doubler la mise + 1 carte)");
            }
            if(mainJoueur.peutSplit()){
                System.out.println("4. SPLIT (Séparer une paire en 2 mains)");
            }
        }

        while (true) {
            System.out.println("Entrer votre choix : ");
            String choix = scanner.nextLine();

            if(choix.equals("1")){return Action.TIRER;}
            if(choix.equals("2")){return Action.RESTER;}
            if(choix.equals("3")){return Action.DOUBLER;}
            if(choix.equals("4")){return Action.SPLITTER;}

            afficherMessage("Veuiller réssayer votre choix est invalid");       
        }
    }

    @Override
    public double demanderMise(Joueur joueur) {
        final double MISE_MINIMUM = 5.0;
        System.out.println("Vous avez " + joueur.getSold() + " Jetons");
        System.out.println("Mise minimum: " + MISE_MINIMUM + " €");

        while (true){
            System.out.println("Combien d'argent vouler vous miser(pas <= 0 ou < que votre solde)(minimum \" + MISE_MINIMUM + \" €) ? ? ");
            try {
                double mise = Double.parseDouble(scanner.nextLine());
                if (mise >= MISE_MINIMUM && mise <= joueur.getSold()){
                    joueur.debiter(mise);
                    joueur.getMainJoueur().setMise(mise);
                    return mise;
                }

                if (mise <MISE_MINIMUM) {afficherMessage("la mise est trop petit");}
                else{afficherMessage("Vous n'avez pas assez d'argent");}
                
            } catch (Exception e) {
                afficherMessage("Entrer un nombre valid");
            }
            
        }


    }

    @Override
    public void modeleMisAJour(Object partie) {

    }

    @Override
    public void ajouterEcouteur(EcouteurActionJeu ecouteurActionJeu) {

    }
}