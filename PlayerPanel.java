package blackjack.vue;

import blackjack.modele.Action;
import blackjack.modele.EcouteurActionJeu;
import blackjack.modele.Joueur;
import blackjack.modele.PartieBlackjack;
import cartes.Carte;
import cartes.vue.VueCarteGUI;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * Panel graphique représentant un joueur dans l'interface du jeu Blackjack.
 *
 * <p>Ce panel affiche le nom du joueur, son solde, la valeur de sa main,
 * ainsi que les cartes qu'il possède. Il gère également l'affichage des
 * boutons d'action ({@code Tirer}, {@code Rester}, {@code Doubler}) pour le joueur actif,
 * ou des messages indiquant si le joueur a un Blackjack ou a perdu.</p>
 *
 * <p>Chaque {@code PlayerPanel} est lié à un {@link Joueur} et
 * à une {@link PartieBlackjack} afin de pouvoir se mettre à jour
 * dynamiquement en fonction de l'état du jeu.</p>
 */
public class PlayerPanel extends JPanel {
    private JPanel cardsPanel, footer;
    private final Joueur joueur;
    private final EcouteurActionJeu ecouteur;
    private final PartieBlackjack partie;
    private JLabel score, name;
    private final int width, height;

    /**
     * Constructeur du panel d'un joueur.
     *
     * @param joueur le joueur représenté par ce panel
     * @param ecouteur l'objet écoutant les actions du joueur
     * @param partie la partie de Blackjack associée
     * @param width la largeur des cartes affichées
     * @param height la hauteur des cartes affichées
     */
    public PlayerPanel(Joueur joueur, EcouteurActionJeu ecouteur, PartieBlackjack partie, int width, int height){
        this.width = width;
        this.height = height;
        this.ecouteur = ecouteur;
        this.joueur = joueur;
        this.partie = partie;

        buildUI();
    }

    /**
     * Met à jour le panel en fonction de l'état actuel du joueur.
     *
     * <p>Cette méthode met à jour :</p>
     * <ul>
     *     <li>Le nom et le solde du joueur</li>
     *     <li>La valeur de sa main</li>
     *     <li>L'affichage de ses cartes</li>
     *     <li>Les boutons d'action pour le joueur actif :
     *         {@link Action#TIRER},
     *         {@link Action#RESTER},
     *         {@link Action#DOUBLER}</li>
     *     <li>Les messages si le joueur a {@link blackjack.modele.Main#estBlackjack() un Blackjack} ou
     *     {@link blackjack.modele.Main#estBuste() a perdu}</li>
     * </ul>
     */
    public void update(){

        name.setText(joueur.getNom());
        score.setText(String.format("Solde %.2f", joueur.getSold())  + " | Valeur: " + joueur.getMainJoueur().getValeur());

        cardsPanel.removeAll();
        for(Carte carte: joueur.getMainJoueur().getCartes()){
            cardsPanel.add(new VueCarteGUI(carte, width, height));
            System.out.println("card: " + carte);
        }

        Color borderColor = new Color(255, 255, 255, 40);
        setBackground(Color.decode("#2b1f04"));

        if(joueur == partie.getEtat().getJoueurActuel()){
            setBackground(Color.decode("#423108"));
            borderColor = Color.decode("#8a6d29");

        }

        if(joueur.getMainJoueur().estBlackjack()){
            borderColor = Color.decode("#B26B00");
            setBackground(Color.decode("#392200"));
        }

        if(joueur.getMainJoueur().estBuste()){
            borderColor = Color.decode("#C00000");
            setBackground(Color.decode("#5A0000"));
        }

        setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, borderColor),
                new EmptyBorder(12, 12, 12, 12)
        ));

        footer.removeAll();

        if(joueur == partie.getEtat().getJoueurActuel()){

            if(partie.getEtat().getJoueurActuel() != null){
                JButton hit = Components.createButton("Hit");
                JButton stand = Components.createButton("Stand");
                JButton doubler = Components.createButton("Doubler");
                //JButton splitter = createButton("Splitter");
                //JButton assurer = createButton("Assurer");

                hit.addActionListener(e -> ecouteur.onAction(joueur, Action.TIRER));
                stand.addActionListener(e -> ecouteur.onAction(joueur, Action.RESTER));
                doubler.addActionListener(e -> ecouteur.onAction(joueur, Action.DOUBLER));
                //splitter.addActionListener(e -> ecouteur.onAction(joueur, Action.SPLITTER));
                //assurer.addActionListener(e -> ecouteur.onAction(joueur, Action.ASSURER));


                footer.add(hit);
                footer.add(stand);
                footer.add(doubler);
                //controlPanel.add(splitter);
                //controlPanel.add(assurer);

            }
        }else{
            if(joueur.getMainJoueur().estBlackjack()){
                JLabel label = Components.createLabel("Tu as Blackjack");
                label.setHorizontalAlignment(JLabel.CENTER);
                footer.add(label, BorderLayout.SOUTH);
            }

            if(joueur.getMainJoueur().estBuste()){
                JLabel label = Components.createLabel("Tu as perdu");
                label.setHorizontalAlignment(JLabel.CENTER);
                footer.add(label);
            }
        }


        repaint();
        revalidate();
    }

    /**
     * Construit l'interface graphique initiale du panel.
     */
    public void buildUI(){
        setBackground(Color.decode("#2b1f04"));

        setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, new Color(255, 255, 255, 40)),
                new EmptyBorder(12, 12, 12, 12)
        ));

        setLayout(new BorderLayout());

        this.cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        cardsPanel.setOpaque(false);


        for(Carte c: joueur.getMainJoueur().getCartes()){
            cardsPanel.add(new VueCarteGUI(c, width, height));
        }

        footer = new JPanel();
        footer.setOpaque(false);
        add(footer, BorderLayout.SOUTH);

        add(createHeader(joueur.getNom(), "Solde: " + joueur.getSold() + " | Valeur: " + joueur.getMainJoueur().getValeur()), BorderLayout.NORTH);
        add(cardsPanel, BorderLayout.CENTER);
    }

    /**
     * Crée un header affichant le nom et le solde/valeur du joueur.
     * @param label le nom du joueur
     * @param sublabel le solde et la valeur de la main
     * @return le JPanel du header
     */
    private JPanel createHeader(String label, String sublabel){
        JPanel header = new JPanel(new BorderLayout());
        this.name = Components.createLabel(label);
        this.score = Components.createSubLabel(sublabel);

        header.add(this.name, BorderLayout.WEST);
        header.add(this.score, BorderLayout.EAST);
        header.setOpaque(false);

        return header;
    }
}