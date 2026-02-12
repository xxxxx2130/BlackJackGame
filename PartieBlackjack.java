package blackjack.modele;

import blackjack.observer.AbstractModelEcoutable;
import cartes.*;
import java.util.*;

public class PartieBlackjack extends AbstractModelEcoutable {
    private List<Joueur> joueurs;
    private Croupier croupier;
    private EtatPartie etat;
    private Paquet paquet;
    private Carte carteCache;

    public PartieBlackjack() {
        this.joueurs = new ArrayList<>();
        this.paquet = new PaquetStandard();
        this.croupier = new Croupier(paquet);
        this.etat = new EtatPartie();
        this.carteCache = null ;
    }

   public void demarrerPartie() {
    // Réinitialiser le paquet
    if (paquet instanceof PaquetStandard) {
        initialiserPaquetBlackjack();
    }

    // Réinitialiser seulement les cartes, pas les mises
  //  croupier.reinitialiserMain();
    for (Joueur joueur : joueurs) {
        System.out.println("mise est :"+joueur.getMainJoueur().getMise()+"le joeur :"+joueur.getNom());
        // SAUVEGARDER LA MISE AVANT DE CLEAR
        double miseSauvegardee = joueur.getMainJoueur().getMise();
        joueur.getMainJoueur().clear();
        // RESTAURER LA MISE aprs CLEAR
        joueur.getMainJoueur().setMise(miseSauvegardee);
    }

    etat.setPhase(PhaseJeu.MISE);
    fireChange();
}
    /**
     * Initialise le paquet avec 52 cartes pour le Blackjack
     */
    private void initialiserPaquetBlackjack() {
        // Vider le paquet
        if (paquet instanceof PaquetStandard) {
            ((PaquetStandard) paquet).vider();
        } else {
            while (!paquet.estVide()) {
                paquet.piocherCarte();
            }
        }

        // Ajouter 52 cartes standard
        for (Couleur couleur : Couleur.values()) {
            for (Valeur valeur : Valeur.values()) {
                paquet.ajouterCarte(new CarteStandard(couleur, valeur));
            }
        }
        paquet.melangerCartes();
    }

    public void ajouterJoueur(Joueur joueur) {
        joueurs.add(joueur);
        fireChange();
    }

    public void executerAction(Joueur joueur, Action action) {
        switch(action) {
            case TIRER:
                Carte carte = croupier.distribuerCarte();
                joueur.getMainJoueur().ajouterCarte(carte);
                break;
            case RESTER:
                // Ne rien faire
                break;
            case DOUBLER:
                double miseActuelle = joueur.getMainJoueur().getMise();
                //  Vérifier que le joueur a assez d'argent
                if (joueur.getSold() >= miseActuelle) {
                    joueur.debiter(miseActuelle);
                    joueur.getMainJoueur().setMise(miseActuelle * 2);
                    carte = croupier.distribuerCarte();
                    joueur.getMainJoueur().ajouterCarte(carte);
                }
                break;
            case SPLITTER:
                // À implémenter plus tard
                System.out.println("Split non implémenté");
                break;
            case ASSURER:
                // À implémenter plus tard
                System.out.println("Assurance non implémentée");
                break;
        }
        fireChange();
    }

    public void calculerResultats() {
        //Thread.sleep(5000);
        etat.setPhase(PhaseJeu.RESULTAT);
        System.out.println("je suis ici et le résultas est :"+etat);
        int valeurCroupier = croupier.getValeurMain();
        boolean croupierBuste = croupier.estBuste();

        for (Joueur joueur : joueurs) {
            MainJoueur main = joueur.getMainJoueur();
            double mise = main.getMise();

            if (main.estBuste()) {
                continue; // Joueur perd sa mise
            }

            if (croupierBuste) {
                System.out.println("val du mise"+mise );
                System.out.println("crediter du tout les joueurs"+mise * 2);
                joueur.crediter(mise * 2);
                System.out.println("le Est croupier buste :"+joueur.getSold());

            }
            if (main.estBlackjack() && !croupier.aBlackjack()) {
                System.out.println("val du mise"+mise );
                System.out.println("crediter du j"+mise * 2.5);
                joueur.crediter(mise * 2.5);
                System.out.println("la main de joeur est blackjack"+main.getValeur());
                System.out.println("la main de joeur est blackjack"+joueur.getNom());
            }
            if (main.getValeur() > valeurCroupier) {
                System.out.println("val du mise"+mise );
                joueur.crediter(mise * 2);
                System.out.println("crediter du j"+mise * 2);
                System.out.println("pas de blackjack mais main supérieur "+main.getValeur()+"de la main du dealer"+croupier.getMain().getValeur());
                System.out.println("le joeur "+joueur.getNom());
            }
            if (main.getValeur() == valeurCroupier) {
                System.out.println("main joueur  "+main.getValeur()+"egale a la main du dealer"+croupier.getMain().getValeur());
                System.out.println("le joeur "+joueur.getNom());
                joueur.crediter(mise); // Égalité
            }
            if (main.estBuste()) {
                System.out.println("buste "+main.getValeur());
                System.out.println("le joeur "+joueur.getNom());
                }

        }
        fireChange();
    }

    // Getters
    public List<Joueur> getJoueurs() { return joueurs; }
    public Croupier getCroupier() { return croupier; }
    public EtatPartie getEtat() { return etat; }
    public Paquet getPaquet() { return paquet; }

    /**
     * Vérifie si la partie est terminée
     */
    public boolean estTerminee() {
        for (Joueur j : joueurs) {
            if (j.getSold() > 0) {
                return false; // au moins un joueur peut continuer → jeu NON terminé
            }
        }
        return true; // plus aucun joueur ne peut miser → jeu TERMINÉ
    }


    public void verfierSiJeuTerminer () {


        boolean allEleminated = true;
       for(Joueur j : joueurs){
           if(!j.estEliminer()){
               allEleminated = false;
               break;
           }
       }
       if(allEleminated) {
           etat.setPhaseTerminee(true);
           etat.setJoueurActuel(null);
       }
        if(croupier.estBuste() || croupier.aBlackjack() ){
            etat.setPhaseTerminee(true);
            etat.setJoueurActuel(null);
        }

    }

    public Carte getCarteCache() {
        return carteCache;
    }

    public void setCarteCache( Carte carteCache){
        this.carteCache = carteCache;
    }
    public void setJoueurs(List<Joueur> copieListeJoueurs) {
        this.joueurs = copieListeJoueurs;
    }

   
}