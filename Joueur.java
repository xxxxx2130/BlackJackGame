package blackjack.modele;

public abstract class Joueur {

    private String nom;
    private double sold;
    private MainJoueur mainJoueur;


    public Joueur(String Nom ,double sold){
        this.nom = Nom;
        this.sold=sold;
        this.mainJoueur = new MainJoueur() ;
    }

    
    public abstract Action prendreDecision();
    public abstract double placerMise();


    public void calculerSold( double PerteOuBenefice){
        setSolde( getSold() + PerteOuBenefice);
    }

    public String getNom(){
        return this.nom;
    }

     public double getSold(){
        return this.sold;
    }

    public MainJoueur getMainJoueur(){
        return this.mainJoueur;
    }

    public void setSolde(double newSold){
         this.sold = newSold;
    }

    public void setMainJoueur(MainJoueur newMainJoueur){
         this.mainJoueur = newMainJoueur;
    }
    public void crediter(double montant) {
        System.out.println("montat = "+montant);
        this.sold += montant;
        System.out.println("sold = "+this.sold);
    }
    public void debiter(double montant) {
        if (montant <= this.sold) {
            this.sold -= montant;
        }
    }

    public boolean estEliminer(){
        return getMainJoueur().estBuste() || getMainJoueur().estBlackjack();
    }
public void reinitialiser(){
        this.mainJoueur.reinitialiser();
        this.setSolde(100);
}


}
