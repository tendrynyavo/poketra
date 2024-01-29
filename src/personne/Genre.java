package personne;

import connection.BddObject;

public class Genre extends BddObject{
    String nom;

    public Genre() throws Exception {
        super();
        this.setTable("genres");
        this.setPrimaryKeyName("id_genre");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('genre_seq')");
        this.setCountPK(5);
        this.setPrefix("GEN");
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return this.getNom();
    }

    
}
