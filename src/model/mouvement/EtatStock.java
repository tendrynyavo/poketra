package model.mouvement;

import java.sql.Connection;
import model.matiere.Matiere;

public class EtatStock {

    ListeStock[] listeSotcks;

    public ListeStock[] getListeStock() {
        return this.listeSotcks;
    }

    public void setListeStock(ListeStock[] listeSotck) {
        this.listeSotcks =listeSotck;
    }

    public ListeStock findMatieres(Matiere matiere) throws Exception {
        for (ListeStock listeStock : listeSotcks) {
            if (listeStock.getMatiere().equals(matiere)) {
                return listeStock;
            }
        }
        throw new IllegalArgumentException("Matiere n'existe pas");
    }

    public boolean suffisant(Mouvement matiere) throws Exception {
        return this.findMatieres(matiere.getMatiere()).getStock() > matiere.getQuantite();
    }

    public EtatStock getEtatStock(Connection c) throws Exception {
        ListeStock foo = new ListeStock();
        this.setListeStock((ListeStock[]) foo.findAll(c,null));
        return this;
    }

}