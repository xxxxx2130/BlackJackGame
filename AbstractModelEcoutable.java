package blackjack.observer;

import java.util.*;

public abstract class AbstractModelEcoutable implements Ecoutable {
    private List<Ecouteur> ecouteurs;

    public AbstractModelEcoutable() {
        this.ecouteurs = new ArrayList<>();
    }

    @Override
    public void ajouterEcouteur(Ecouteur e){
        ecouteurs.add(e);

    }
    
    @Override
    public void retirerEcouteur(Ecouteur e) {
        ecouteurs.remove(e);
    }
    
    protected void fireChange() {
        for (Ecouteur e : ecouteurs) {
            e.modeleMisAJour( this);
        }
    }
}