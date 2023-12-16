package model.secteur;

import model.etat.EtatSolaire;
import model.pointage.Pointage;

public class EtatSolaireMoyenne extends EtatSolaire {

    Pointage[] pointages;

    public Pointage[] getPointages() {
        return pointages;
    }

    public void setPointages(Pointage[] pointages) {
        this.pointages = pointages;
    }
    
}
