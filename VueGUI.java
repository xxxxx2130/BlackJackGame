package blackjack.vue;

import blackjack.modele.*;
import blackjack.modele.Action;
import cartes.Carte;
import cartes.vue.VueCarteGUI;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static blackjack.vue.Components.createLabel;
import static blackjack.vue.Components.createButton;
import static blackjack.vue.Components.createSubLabel;

/**
 * Classe représentant l'interface graphique du jeu de Blackjack.
 * Cette classe étend JFrame et implémente l'interface VueJeu pour
 * afficher l'état du jeu, gérer les interactions avec les joueurs
 * et mettre à jour les composants graphiques.
 *
 * <p>Elle contient :</p>
 * <ul>
 *     <li>Un panneau pour le croupier et ses cartes</li>
 *     <li>Un ensemble de PlayerPanel pour chaque joueur</li>
 *     <li>Un panneau de contrôle avec les boutons "New Game", "New Round" et "Exit"</li>
 *     <li>Un label pour afficher les messages du jeu et le statut</li>
 * </ul>
 *
 * <p>Les dimensions des cartes peuvent être configurées via setCardWidth et setCardHeight.</p>
 */
public class VueGUI extends JFrame implements VueJeu {

    private int cardWidth, cardHeight;
    private JButton exit, newRound;
    private PartieBlackjack partie;
    private EcouteurActionJeu ecouteur;
    protected String gameTitle;
    private final JLabel message;
    private JLabel status;

    private JPanel dealerPanel, controlPanel;
    private JLabel dealerScoreLabel;

    private final Map<Joueur, PlayerPanel> playersPanels;

    public VueGUI(){
        cardWidth = 150;
        cardHeight = 200;
        playersPanels = new HashMap<>();
        this.gameTitle = "BlackJack Game";
        message = createLabel("--Bienvenue--");
    }


    /**
     * Prépare l'interface graphique complète du jeu.
     * Crée et dispose tous les panneaux, labels et boutons.
     */
    public void prepareGameUI(){

        if(this.partie == null) {
            throw new IllegalArgumentException("A valid instance of PartieBlackJack must be provided.");
        }

        getContentPane().removeAll();
        setTitle(this.gameTitle);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#2b1f04"));

        JPanel container = new JPanel(new BorderLayout(20, 20));
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(20, 20, 20, 20));

        status = createSubLabel("Phase: " + partie.getEtat().getPhase() + " | Joueurs: " + partie.getJoueurs().size());

        JPanel header = new JPanel(new BorderLayout());
        header.add(createLabel("BlackJack"), BorderLayout.WEST);
        message.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(message, BorderLayout.CENTER);
        header.add(status, BorderLayout.EAST);
        header.setOpaque(false);

        container.add(header, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setOpaque(false);
        mainPanel.add(prepareDealerPanel(), BorderLayout.NORTH);
        mainPanel.add(preparePlayersPanel(), BorderLayout.CENTER);

        container.add(mainPanel, BorderLayout.CENTER);
        this.controlPanel = prepareControlPanel();
        container.add(controlPanel, BorderLayout.SOUTH);

        add(container);
    }

    /**
     * Met à jour le panneau du croupier en affichant ses cartes et la valeur de sa main.
     */
    private void updateDealerPanel(){
        dealerPanel.removeAll();

        for(Carte c: partie.getCroupier().getMain().getCartes()){
            dealerPanel.add(new VueCarteGUI(c, cardWidth, cardHeight));
        }

        dealerScoreLabel.setText("Valeur: " + partie.getCroupier().getValeurMain());

        revalidate();
        repaint();
    }

    /**
     * Prépare le panneau du croupier avec ses cartes et son score.
     * @return un JPanel contenant le croupier
     */
    private JPanel prepareDealerPanel(){
        //Conteneur de la fenetre de croupier
        JPanel area = new JPanel(new BorderLayout());
        area.setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, new Color(255, 255, 255, 40)),
                new EmptyBorder(12, 12, 12, 12)
        ));
        area.setOpaque(false);

        //L'entete de la fenetre de croupier
        JPanel header = new JPanel(new BorderLayout());
        dealerScoreLabel = createSubLabel("Valeur: " + partie.getCroupier().getValeurMain());
        header.add(createLabel("Croupier"), BorderLayout.WEST);
        header.add(dealerScoreLabel, BorderLayout.EAST);
        header.setOpaque(false);
        area.add(header, BorderLayout.NORTH);

        //Conteneur des cartes de  croupier
        dealerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        dealerPanel.setOpaque(false);

        for(Carte c: partie.getCroupier().getMain().getCartes()){
            dealerPanel.add(new VueCarteGUI(c, cardWidth, cardHeight));
        }

        area.add(dealerPanel, BorderLayout.CENTER);

        return area;
    }

    /**
     * Prépare le panneau contenant tous les joueurs.
     * Chaque joueur est représenté par un PlayerPanel.
     * @return un JPanel contenant les joueurs
     */
    private JPanel preparePlayersPanel() {
        JPanel playersPanel = new JPanel();
        playersPanel.setOpaque(false);
        int playersCount = partie.getJoueurs().size();
        int rows = Math.max(1, (playersCount + 1) / 2);

        playersPanel.setLayout(new GridLayout(rows, 2, 12, 12));

        playersPanels.clear();
        if(!this.partie.getJoueurs().isEmpty()){
            for(Joueur joueur: partie.getJoueurs()){
                PlayerPanel playerPanel = new PlayerPanel(joueur, ecouteur, partie, cardWidth, cardHeight);
                playersPanels.put(joueur, playerPanel);
                playersPanel.add(playerPanel);
            }
        }else{
            playersPanel.add(createLabel("Aucun joueur pour le moment"));
        }

        return playersPanel;
    }

    /**
     * Prépare le panneau de contrôle avec les boutons "New Game", "New Round" et "Exit".
     * Les boutons sont connectés à l'EcouteurActionJeu.
     * @return un JPanel contenant les contrôles
     */
    private JPanel prepareControlPanel(){
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        this.exit = createButton("Exit");
        this.newRound = createButton("New Round");


        newRound.addActionListener(a -> ecouteur.onNouveauTour());
        exit.addActionListener(e -> dispose());

        panel.add(exit);

        if(partie.getEtat().getPhase() == PhaseJeu.RESULTAT){
            panel.add(newRound);
        }

        return panel;
    }

    /**
     * Met à jour dynamiquement le panneau de contrôle selon la phase du jeu.
     */
    private void updateControlPanel(){

        controlPanel.removeAll();
        controlPanel.add(exit);

        if(partie.getEtat().getPhase() == PhaseJeu.RESULTAT){
            controlPanel.add(newRound);
        }

        controlPanel.revalidate();
        controlPanel.repaint();
    }

    /**
     * Affiche la fenêtre et ajuste sa taille automatiquement.
     */
    public void display(){
        setVisible(true);
        pack();
    }

    /**
     * Définit le titre de la fenêtre du jeu.
     * @param gameTitle le titre à afficher
     */
    public void setGameTitle(String gameTitle){
        this.gameTitle = gameTitle;
    }

    /**
     * Définit la largeur des cartes.
     * @param width largeur des cartes en pixels
     */
    public void setCardWidth(int width){
        this.cardWidth = width;
    }

    /**
     * Définit la hauteur des cartes.
     * @param height hauteur des cartes en pixels
     */
    public void setCardHeight(int height){
        this.cardHeight = height;
    }

    @Override
    public void afficherPartie(PartieBlackjack partie) {
        this.partie = partie;
        prepareGameUI();
        display();
    }

    @Override
    public void afficherMessage(String message) {
        this.message.setText(message);
        revalidate();
        repaint();
    }

    @Override
    public Action demanderAction(Joueur joueur) {
        return Action.DOUBLER;
    }

    @Override
    public double demanderMise(Joueur joueur) {
        while(true){
            String input = JOptionPane.showInputDialog(this,
                    joueur.getNom() + " : saisir votre mise",
                    "Mise",
                    JOptionPane.PLAIN_MESSAGE);
            if(input == null){
                return 0;
            }
            try{
                double mise = Double.parseDouble(input);
                if(mise >= 0 && mise <= joueur.getSold()){
                    return mise;
                }
                JOptionPane.showMessageDialog(this, "Mise invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void ajouterEcouteur(EcouteurActionJeu ecouteurActionJeu) {
        this.ecouteur = ecouteurActionJeu;
    }

    @Override
    public void modeleMisAJour(Object partie) {
        if(partie instanceof PartieBlackjack){
            this.partie = (PartieBlackjack) partie;

            status.setText("Phase: " + this.partie.getEtat().getPhase() + " | Joueurs: " + this.partie.getJoueurs().size());

            //Remet à jour les panels des joueurs

            Set<Joueur> joueursEliminee = new HashSet<>();

            for(Map.Entry<Joueur, PlayerPanel> panel: playersPanels.entrySet()){
                if(!this.partie.getJoueurs().contains(panel.getKey())){
                 joueursEliminee.add(panel.getKey());

                 Container parent = panel.getValue().getParent();
                 parent.remove(panel.getValue());
                 parent.revalidate();
                 parent.repaint();
                }else{
                    panel.getValue().update();
                }
            }

            revalidate();
            repaint();

            for(Joueur j: joueursEliminee){
                playersPanels.remove(j);
            }

            updateDealerPanel();
            updateControlPanel();

        }
    }
}

