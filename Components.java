package blackjack.vue;

import javax.swing.*;
import java.awt.*;

/**
 * Classe utilitaire pour créer des composants Swing avec un style prédéfini
 * utilisé dans l'interface graphique du jeu de Blackjack.
 *
 * <p>Elle fournit des méthodes statiques pour créer des boutons et des labels
 * avec des couleurs et des polices uniformes, afin de faciliter la cohérence
 * de l'UI.</p>
 */
public class Components {

    /**
     * Crée un bouton ({@link JButton}) avec un style standard pour le jeu.
     *
     * <p>Le bouton aura un fond vert (#78c435) et peut être utilisé pour toutes
     * les actions du jeu (Hit, Stand, Doubler, etc.).</p>
     *
     * @param text le texte affiché sur le bouton
     * @return un {@link JButton} stylisé
     */
    public static JButton createButton(String text){
        JButton btn = new JButton(text);
        btn.setBackground(Color.decode("#78c435"));
        return btn;
    }

    /**
     * Crée un label principal ({@link JLabel}) avec un style standard.
     *
     * <p>Le label aura une police Arial en gras taille 16 et une couleur blanche.</p>
     *
     * @param text le texte affiché sur le label
     * @return un {@link JLabel} stylisé
     */
    public static JLabel createLabel(String text){
        JLabel label =  new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }

    /**
     * Crée un label secondaire ({@link JLabel}) avec un style standard pour
     * les sous-informations ou détails.
     *
     * <p>Le label aura une police Arial en gras taille 13 et une couleur verte.</p>
     *
     * @param text le texte affiché sur le label
     * @return un {@link JLabel} stylisé
     */
    public static JLabel createSubLabel(String text){
        JLabel label =  new JLabel(text);
        label.setForeground(Color.GREEN);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        return label;
    }

}
