package blackjack.modele;

/**
 * Représente un joueur contrôlé par l'intelligence artificielle (IA).
 * L'IA prend des décisions de jeu et de mise selon une stratégie spécifique.
 * Hérite de la classe Joueur pour bénéficier des fonctionnalités de base.
 */
public class IA extends Joueur {

    // Stratégie utilisée par l'IA pour prendre ses décisions
    private Strategie strategie;

    /**
     * Constructeur pour créer une IA.
     * @param Nom Le nom de l'IA
     * @param Sold Le solde initial de l'IA
     * @param strategie La stratégie que l'IA suivra pour prendre ses décisions
     */
    public IA(String Nom, int Sold, Strategie strategie) {
        super(Nom, Sold);
        this.strategie = strategie;
    }

    /**
     * Récupère la stratégie actuelle de l'IA.
     * @return La stratégie utilisée par l'IA
     */
    public Strategie getStrategie() {
        return this.strategie;
    }

    /**
     * Détermine quelle action l'IA doit effectuer.
     * Utilise la stratégie assignée pour prendre la décision en fonction de la main actuelle.
     * @return L'action choisie par l'IA (TIRER, RESTER, DOUBLER, etc.)
     */
    @Override
    public Action prendreDecision() {
        return this.strategie.getAction(getMainJoueur(), null);
    }

    /**
     * Détermine le montant de la mise que l'IA doit placer.
     * Utilise la stratégie assignée pour calculer la mise.
     * @return Le montant de la mise
     */
    @Override
    public double placerMise() {
        return strategie.getMise();
    }
    
    /**
     * Génère un nombre aléatoire dans un intervalle donné.
     * Utilitaire pour les stratégies qui nécessitent des choix aléatoires.
     * @param min La valeur minimale (incluse)
     * @param max La valeur maximale (incluse)
     * @return Un nombre aléatoire entre min et max
     */
    public double getRandomNumber(int min, int max) {
        return (double) ((Math.random() * (max - min)) + min);
    }
}