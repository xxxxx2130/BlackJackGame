package blackjack.modele;

import cartes.CarteStandard;
import cartes.Valeur;
import cartes.Couleur;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StrategieIATest {

    @Test
    public void testMiseEstPositive() {
        StrategieIA strategie = new StrategieIA();
        double mise = strategie.getMise();
        assertTrue(mise > 0);
    }

    @Test
    public void testMiseEstConstante() {
        StrategieIA strategie = new StrategieIA();
        double mise1 = strategie.getMise();
        double mise2 = strategie.getMise();
        assertTrue(mise1 != mise2);
    }

    @Test
    public void testTirerAvecMainFaible() {
        StrategieIA strategie = new StrategieIA();
        MainJoueur main = new MainJoueur();
        CarteStandard carteCroupier = new CarteStandard(Couleur.COEUR, Valeur.SIX);
        
        // Main: 5 + 5 = 10 (<= 11) → doit tirer
        main.ajouterCarte(new CarteStandard(Couleur.COEUR, Valeur.CINQ));
        main.ajouterCarte(new CarteStandard( Couleur.PIQUE, Valeur.CINQ));
        
        Action action = strategie.getAction(main, carteCroupier);
        assertTrue(action == Action.TIRER);
    }

    @Test
    public void testResterAvecMainForte() {
        StrategieIA strategie = new StrategieIA();
        MainJoueur main = new MainJoueur();
        CarteStandard carteCroupier = new CarteStandard(Couleur.COEUR, Valeur.SIX);
        
        // Main: 10 + 7 = 17 (>= 17) → doit rester
        main.ajouterCarte(new CarteStandard(Couleur.COEUR, Valeur.DIX));
        main.ajouterCarte(new CarteStandard(Couleur.PIQUE, Valeur.SEPT));
        
        Action action = strategie.getAction(main, carteCroupier);
        assertTrue(action == Action.RESTER);
    }

    @Test
    public void testTirerAvecMainMoyenneEtCroupierFort() {
        StrategieIA strategie = new StrategieIA();
        MainJoueur main = new MainJoueur();
        CarteStandard carteCroupier = new CarteStandard(Couleur.COEUR, Valeur.DIX); // Cle croupir a une main forte
        
        // Main: 10 + 6 = 16 (12-16) et croupier 10 (>=7) → doit tirer
        main.ajouterCarte(new CarteStandard( Couleur.COEUR, Valeur.DIX));
        main.ajouterCarte(new CarteStandard( Couleur.PIQUE, Valeur.SIX));
        
        Action action = strategie.getAction(main, carteCroupier);
        assertTrue(action == Action.TIRER);
    }

    @Test
    public void testResterAvecMainMoyenneEtCroupierFaible() {
        StrategieIA strategie = new StrategieIA();
        MainJoueur main = new MainJoueur();
        CarteStandard carteCroupier = new CarteStandard(Couleur.COEUR, Valeur.SIX); // la main du croupier est faible
        
        // Main: 10 + 6 = 16 (12-16) et croupier 6 (<7) → doit rester
        main.ajouterCarte(new CarteStandard(Couleur.COEUR, Valeur.DIX));
        main.ajouterCarte(new CarteStandard(Couleur.PIQUE, Valeur.SIX));
        
        Action action = strategie.getAction(main, carteCroupier);
        assertTrue(action == Action.RESTER);
    }

    @Test
    public void testActionToujoursDefinie() {
        StrategieIA strategie = new StrategieIA();
        MainJoueur main = new MainJoueur();
        CarteStandard carteCroupier = new CarteStandard(Couleur.COEUR, Valeur.AS);
        
        // Test avec main vide
        Action action = strategie.getAction(main, carteCroupier);
        assertTrue(action != null);
    }
}