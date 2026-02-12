package blackjack.modele;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EtatPartieTest {

    @Test
    public void testInitialisationPhaseMise() {
        EtatPartie etat = new EtatPartie();
        assertTrue(etat.estPhaseMise());
    }

    @Test
    public void testJoueurActuelInitialNull() {
        EtatPartie etat = new EtatPartie();
        assertTrue(etat.getJoueurActuel() == null);
    }

    @Test
    public void testSetJoueurActuel() {
        EtatPartie etat = new EtatPartie();
        Joueur joueur = new Humain("Test", 20);
        
        etat.setJoueurActuel(joueur);
        assertTrue(etat.getJoueurActuel() == joueur);
    }

    @Test
    public void testChangementPhase() {
        EtatPartie etat = new EtatPartie();
        etat.setPhase(PhaseJeu.JEU_CROUPIER);
        assertTrue(etat.estPhaseCroupier());
    }

    @Test
    public void testPhaseSuivanteDepuisMise() {
        EtatPartie etat = new EtatPartie();
        etat.phaseSuivante();
        assertTrue(etat.getPhase() == PhaseJeu.DISTRIBUTION);
    }

    @Test
    public void testPhaseSuivanteComplete() {
        EtatPartie etat = new EtatPartie();
        
        // MISE → DISTRIBUTION
        etat.phaseSuivante();
        assertTrue(etat.getPhase() == PhaseJeu.DISTRIBUTION);
        
        // DISTRIBUTION → JEU_JOUEURS
        etat.phaseSuivante();
        assertTrue(etat.getPhase() == PhaseJeu.JEU_JOUEURS);
        
        // JEU_JOUEURS → JEU_CROUPIER
        etat.phaseSuivante();
        assertTrue(etat.getPhase() == PhaseJeu.JEU_CROUPIER);
        
        // JEU_CROUPIER → RESULTAT
        etat.phaseSuivante();
        assertTrue(etat.getPhase() == PhaseJeu.RESULTAT);
        
        // RESULTAT → RESULTAT (reste)
        etat.phaseSuivante();
        assertTrue(etat.getPhase() == PhaseJeu.RESULTAT);
    }

    @Test
    public void testEstTerminee() {
        EtatPartie etat = new EtatPartie();
        
        // Initialement pas terminée
        assertTrue(!etat.estTourTerminee());
        
        // Aller jusqu'à RESULTAT
        etat.setPhase(PhaseJeu.RESULTAT);
        assertTrue(etat.estTourTerminee());
    }

    @Test
    public void testMethodesEstPhase() {
        EtatPartie etat = new EtatPartie();
        
        // Test MISE
        assertTrue(etat.estPhaseMise());
        assertTrue(!etat.estPhaseJoueurs());
        assertTrue(!etat.estPhaseCroupier());
        
        // Test JEU_JOUEURS
        etat.setPhase(PhaseJeu.JEU_JOUEURS);
        assertTrue(etat.estPhaseJoueurs());
        assertTrue(!etat.estPhaseMise());
        
        // Test JEU_CROUPIER
        etat.setPhase(PhaseJeu.JEU_CROUPIER);
        assertTrue(etat.estPhaseCroupier());
    }

    @Test
    public void testToStringContientPhase() {
        EtatPartie etat = new EtatPartie();
        String texte = etat.toString();
        assertTrue(texte.contains("Phase"));
    }
}