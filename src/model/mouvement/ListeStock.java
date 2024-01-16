package model.mouvement;

import connection.BddObject;
import model.matiere.Matiere;

public class ListeStock extends BddObject{

    Matiere matiere;
    double entree, sortie, stock;

    public Matiere getMatiere() {
        return this.matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Double getEntree() {
        return this.entree;
    }

    public Double getSortie() {
        return this.sortie;
    }

    public Double getStock() {
        return this.stock;
    }

    public void setEntree(double entree) {
        this.entree = entree;
    }

    public void setSortie(double sortie) {
        this.sortie = sortie;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public ListeStock() throws Exception {
        super();
        this.setTable("v_etat_stock");
        this.setConnection("PostgreSQL");
        this.setPrimaryKeyName("");
    }

}