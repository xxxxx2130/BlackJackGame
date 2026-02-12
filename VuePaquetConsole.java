package cartes.vue;

import cartes.Paquet;
import cartes.Carte;
import java.util.List;

public class VuePaquetConsole {
    private VueCarte vueCarte;
    
    public VuePaquetConsole(VueCarte vueCarte) {
        this.vueCarte = vueCarte;
    }
    
    public void afficher(Paquet paquet) {
        List<Carte> cartes = paquet.getCartes();
        for (Carte carte : cartes) {
            vueCarte.afficherAvecVisibilite(carte);
            System.out.print(" ");
        }
        System.out.println();
    }
    
    public void afficherEventail(Paquet paquet, int cartesVisibles) {
        List<Carte> cartes = paquet.getCartes();
        System.out.print("Éventail: ");
        
        for (int i = 0; i < cartes.size(); i++) {
            Carte carte = cartes.get(i);
            if (i < cartesVisibles) {
                vueCarte.afficherAvecVisibilite(carte);
            } else {
                System.out.print("[?]");
            }
            System.out.print(" ");
        }
        System.out.println();
    }
}