package blackjack.modele;

import cartes.CarteStandard;
import cartes.Valeur;
import cartes.Couleur;
import blackjack.modele.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StrategieBasicTest {

    @Test
    public void testMisePositive() {
        StrategieBasic strategie = new StrategieBasic();
        double mise = strategie.getMise();
        assertTrue(mise > 0);
    }

    @Test
    public void testActionExiste() {
        StrategieBasic strategie = new StrategieBasic();
        MainJoueur main = new MainJoueur();
        CarteStandard carteCroupier = new CarteStandard( Couleur.COEUR, Valeur.SIX);
        
        main.ajouterCarte(new CarteStandard(Couleur.COEUR, Valeur.DIX));
        Action action = strategie.getAction(main, carteCroupier);
        
        assertTrue(action != null);
    }

    @Test
    public void testAvecMainVide() {
        StrategieBasic strategie = new StrategieBasic();
        MainJoueur main = new MainJoueur();
        CarteStandard carteCroupier = new CarteStandard(Couleur.COEUR, Valeur.SIX);
        
        Action action = strategie.getAction(main, carteCroupier);
        assertTrue(action instanceof Action);
    }
}