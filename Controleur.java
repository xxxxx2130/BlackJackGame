package blackjack.controleur;

import blackjack.modele.*;
import blackjack.modele.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import blackjack.modele.Action;
import blackjack.vue.VueJeu;
import cartes.Carte;

import javax.swing.*;

/**
 * Contrôleur principal du jeu de Blackjack.
 *
 * <p>Cette classe gère la logique du jeu et fait le lien entre le modèle
 * ({@link PartieBlackjack}) et la vue ({@link VueJeu}). Elle implémente
 * {@link EcouteurActionJeu} pour recevoir les actions des joueurs depuis
 * l'interface graphique.</p>
 *
 * <p>Le contrôleur :</p>
 * <ul>
 *     <li>Démarre le jeu et chaque round</li>
 *     <li>Demande et valide les mises des joueurs</li>
 *     <li>Distribue les cartes aux joueurs et au croupier</li>
 *     <li>Gère le tour des joueurs et du croupier</li>
 *     <li>Calcule les résultats et met à jour la vue</li>
 * </ul>
 */
public class Controleur implements EcouteurActionJeu {

    private VueJeu view;
    private PartieBlackjack partie;
    private Set<Joueur> joueursTerminer;
    private int joueurCourantIndex;
    private List<Joueur> allPlayers;

    /**
     * Constructeur du contrôleur.
     *
     * @param partie l'instance de {@link PartieBlackjack} représentant le jeu
     * @param view l'interface graphique ({@link VueJeu}) à mettre à jour
     */
    public Controleur(PartieBlackjack partie, VueJeu view){
        this.view = view;
        this.partie = partie;
        this.joueursTerminer = new HashSet<>();
        this.allPlayers = new ArrayList<>(partie.getJoueurs());

        view.ajouterEcouteur(this);
    }

    /**
     * Démarre le jeu complet.
     * Affiche la partie et lance le premier round.
     */
    public void demarrerJeu(){
        System.out.println(partie.getPaquet());

        if(partie.getJoueurs().isEmpty()){
            view.afficherMessage("Aucun joueur n'est présent !");
            return;
        }else{
            for(Joueur j: partie.getJoueurs()){
                j.reinitialiser();
            }
        }

        view.afficherPartie(partie);
        demarrerRound();
    }

    /**
     * Démarre un round complet :
     * <ul>
     *     <li>Phase de mise</li>
     *     <li>Distribution des cartes aux joueurs et au croupier</li>
     *     <li>Vérification des blackjacks initiaux</li>
     *     <li>Lancement du tour des joueurs</li>
     * </ul>
     */
    private void demarrerRound(){

        //Configuration initial
        partie.getCroupier().reinitialiserMain();
        partie.demarrerPartie();
        System.out.println(partie.getPaquet());

        List<Joueur> tmp = new ArrayList<>();

        for(Joueur j: partie.getJoueurs()){
            if(j.getSold() ==0){
                tmp.add(j);
            }
        }

        partie.getJoueurs().removeAll(tmp);

        view.modeleMisAJour(partie);
        //Phase mise
        partie.getEtat().setPhase(PhaseJeu.MISE);

        demanderMisePourChaqueJoueur();

        //Une carte pour croupier
        partie.getCroupier().getMain().ajouterCarte(partie.getCroupier().distribuerCarte());
        partie.setCarteCache( partie.getCroupier().distribuerCarte());

        //Distribution des cartes entre les joueurs
        partie.getEtat().setPhase(PhaseJeu.DISTRIBUTION);
        for(Joueur j: partie.getJoueurs()){
            j.getMainJoueur().clear();
            j.getMainJoueur().ajouterCarte(partie.getCroupier().distribuerCarte());
            j.getMainJoueur().ajouterCarte(partie.getCroupier().distribuerCarte());
        }

        //La carte cachée pour croupier
        partie.getCarteCache().setVisible(false);
        partie.getCroupier().getMain().ajouterCarte(partie.getCarteCache());
        view.modeleMisAJour(partie);

        //Verification des blackjacks
        for(Joueur j: partie.getJoueurs()){
            if(j.getMainJoueur().estBlackjack()){
                joueursTerminer.add(j);
            }
        }


        //Crupier a blackjack
        if(partie.getCroupier().getMain().estBlackjack()){

            //Aucun des joueurs ont de blackjack, crupier gagne
            if(joueursTerminer.isEmpty()){
                partie.getEtat().setJoueurActuel(null);
                view.afficherMessage("Croupier à gagner");
            }else{
                //Joueurs ont des blackjacks, tour terminer
                StringBuilder message = new StringBuilder();

                Set<Joueur> players = new HashSet<>(partie.getJoueurs());
                players.removeAll(joueursTerminer);

                for(Joueur j: players){
                    message.append(j.getNom()).append(", ");
                }

                message.append("ont perdu");

                partie.calculerResultats();
                view.afficherMessage(message.toString());
            }

            return;
        }


        if(partie.getJoueurs().size() == joueursTerminer.size()){
            partie.getEtat().setJoueurActuel(null);

            for (Joueur j : partie.getJoueurs()) {
                double mise = j.getMainJoueur().getMise();
                j.crediter(mise * 2.5);
            }

            view.afficherMessage("Croupier à perdu et les joueurs gagne");

            return;
        }


        //Le prochain joueur encore dans le jeu
        partie.getEtat().setPhase(PhaseJeu.JEU_JOUEURS);
        joueurCourantIndex = -1;
        partie.getEtat().setJoueurActuel(prochainJoueur());


        view.modeleMisAJour(partie);
        demanderActionJoueur();
    }

    /**
     * Demande la mise pour chaque joueur.
     * Les mises sont soit saisies par l'humain via la vue, soit générées pour l'IA.
     */
    private void demanderMisePourChaqueJoueur() {
        for(Joueur j: partie.getJoueurs()){
            if(j instanceof Humain) {
                double mise = view.demanderMise(j);
                onMise(j, mise);
            }
            else{
                onMise(j , j.placerMise());
            }
            view.modeleMisAJour(partie);
        }
    }

    /**
     * Lance le tour du joueur courant.
     * Si c'est un humain, affiche un message ; si c'est une IA, exécute sa décision.
     */
    public void demanderActionJoueur() {

        Joueur joueur = partie.getEtat().getJoueurActuel();

        if(joueur instanceof Humain){
            view.afficherMessage("A ton tour " + joueur.getNom());
        }else{
            onAction(joueur, joueur.prendreDecision());
        }

        view.modeleMisAJour(partie);
    }

    /**
     * Retourne le prochain joueur encore actif.
     *
     * @return le {@link Joueur} suivant, ou {@code null} si tous ont terminé
     */
    public Joueur prochainJoueur(){
        joueurCourantIndex++;

        while(joueurCourantIndex < partie.getJoueurs().size()){
            Joueur joueur = partie.getJoueurs().get(joueurCourantIndex);

            if(!joueur.getMainJoueur().estBuste() && !joueur.getMainJoueur().estBlackjack()){
                return joueur;
            }
            joueurCourantIndex++;

        }
        return null;
    }

    /**
     * Exécute le tour complet du croupier après le tour des joueurs.
     */
    public void tourCroupier() {
        System.out.println("tour croupier debut");
        view.afficherMessage("Tour de croupier");

        // Révéler la carte cachée si nécessaire
        if (!partie.getCarteCache().estVisible()) {
            partie.getCarteCache().setVisible(true);
            view.modeleMisAJour(partie);

            // Délai de 3 secondes avant de commencer
            executerAvecDelai(2000, this::distribuerCartesCroupier);
        } else {
            distribuerCartesCroupier();
        }
    }

    private void distribuerCartesCroupier() {
        if (partie.getCroupier().getMain().getValeur() < 17) {
            partie.getCroupier().jouerTour(partie.getCroupier().distribuerCarte());
            view.modeleMisAJour(partie);

            // Délai de 3 secondes avant la prochaine carte
            executerAvecDelai(1500, this::distribuerCartesCroupier);
        } else {
            // Délai de 1 seconde avant les résultats
            executerAvecDelai(1000, this::afficherResultatsFinaux);
        }
    }

    private void afficherResultatsFinaux() {
        partie.getEtat().setPhase(PhaseJeu.RESULTAT);
        view.modeleMisAJour(partie);

        if (partie.getCroupier().getMain().estBuste()) {
            partie.getEtat().setJoueurActuel(null);
            view.afficherMessage("Croupier a bust → les joueurs gagnent !");
            partie.calculerResultats();
            return;
        }

        partie.getEtat().setJoueurActuel(null);
        partie.calculerResultats();

        System.out.println("isiiiiiii");
        for (Joueur j : partie.getJoueurs()) {
            System.out.println(j.getNom() + ": " + j.getSold());
        }

        StringBuilder results = new StringBuilder("");
        for (Joueur j : partie.getJoueurs()) {
            if (j.getMainJoueur().estBlackjack() ||
                    (!j.getMainJoueur().estBuste() &&
                            j.getMainJoueur().getValeur() > partie.getCroupier().getValeurMain())) {
                results.append(j.getNom()).append(", ");
            }
        }

        if (results.toString().equals("")) {
            view.afficherMessage("Tour du croupier terminé - Aucun gagne.");
        } else {
            results.append("ont gagné");
            view.afficherMessage(results.toString());
        }
    }

    // Méthode utilitaire pour exécuter du code avec délai
    private void executerAvecDelai(int delaiMs, Runnable action) {
        new Thread(() -> {
            try {
                Thread.sleep(delaiMs);
                SwingUtilities.invokeLater(action);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    @Override
    public void onAction(Joueur joueur, Action action) {
        if(partie.getEtat().getJoueurActuel() != joueur){
            view.afficherMessage("Ce n'est pas le tour de " + joueur.getNom());
            return;
        }

        //Execution de l'action
        System.out.println(joueur.getNom() + ": action " + action);
        partie.executerAction(joueur, action);

        boolean joueurContinue = action == Action.TIRER && !joueur.getMainJoueur().estBuste();

        if(joueurContinue){
            demanderActionJoueur();
            return;
        }

        Joueur prochainJoueur = prochainJoueur();

        if(prochainJoueur == null){
            //Plus de joueurs: tour de crupier
            partie.getEtat().setJoueurActuel(null);
            partie.getEtat().setPhase(PhaseJeu.JEU_CROUPIER);
            tourCroupier();
            return;
        }

        partie.getEtat().setJoueurActuel(prochainJoueur);
        demanderActionJoueur();
    }

    @Override
    public void onMise(Joueur joueur, double mise) {

        if (mise <= 0 || mise > joueur.getSold()) {
            view.afficherMessage("Mise invalide pour " + joueur.getNom());
            return;
        }
        System.out.println("la mise est "+mise+"de j "+ joueur.getNom());

        joueur.debiter(mise);
        joueur.getMainJoueur().setMise(mise);
        System.out.println("la mise apres set"+joueur.getMainJoueur().getMise()+"de"+joueur.getNom());
        view.modeleMisAJour(partie);
    }

    @Override
    public void onNouveauJeu() {
        partie.getJoueurs().clear();
        partie.getJoueurs().addAll(allPlayers);
        demarrerJeu();
    }

    @Override
    public void onNouveauTour() {
        demarrerRound();
    }
}