package cartes.vue;

import cartes.Carte;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URL;

/**
 * Représente l'affichage graphique d'une carte de jeu.
 * <p>
 * Cette classe étend {@link JPanel} et affiche une image correspondant à
 * la carte fournie. Si la carte est masquée, elle affichera le dos de la carte.
 * La taille de l'affichage est configurable via le constructeur.
 * </p>
 */
public class VueCarteGUI extends JPanel {

    /** Image représentant la carte. */
    private Image image;

    /** Largeur de l'affichage de la carte. */
    private final int width;

    /** Hauteur de l'affichage de la carte. */
    private final int height;

    /**
     * Crée une représentation graphique d'une carte avec une taille spécifique.
     *
     * @param carte la {@link Carte} à afficher
     * @param width largeur souhaitée en pixels
     * @param height hauteur souhaitée en pixels
     */
    public VueCarteGUI(Carte carte, int width, int height){

        this.width = width;
        this.height = height;

        setBackground(Color.decode("#382901"));

        URL url;
        image = null;

        String cardPath = "/assets/images/"+
                carte.getCouleur().toString().toLowerCase() + "_" +
                carte.getValeur().toString().toLowerCase() + ".png";

        if(carte.estVisible()){
            url = getClass().getResource(cardPath);
        }else{
            url = getClass().getResource("/assets/images/dos_de_carte.png");
        }

        if(url != null){
            this.image = new ImageIcon(url).getImage();
        }
    }

    /**
     * Crée une représentation graphique d'une carte avec une taille par défaut
     * de 150x200 pixels.
     *
     * @param carte la {@link Carte} à afficher
     */
    public VueCarteGUI(Carte carte){
        this(carte, 150, 200);
    }

    /**
     * Retourne la taille préférée de cette carte pour la mise en page.
     *
     * @return un objet {@link Dimension} avec la largeur et la hauteur configurées
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    /**
     * Dessine l'image de la carte dans le JPanel.
     *
     * @param g l'objet {@link Graphics} utilisé pour le dessin
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        if(image != null){
            g2.drawImage(image, 0, 0, width, height, this);

        }
    }
}