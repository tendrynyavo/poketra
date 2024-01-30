package personne;

import connection.BddObject;

public class Client extends BddObject {
    
    String nom;
    Genre genre;

    public Client() throws Exception{
        super();
        this.setTable("Client");
        this.setPrimaryKeyName("id_client");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('client_seq')");
        this.setCountPK(5);
        this.setPrefix("CLI");
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return this.getNom();
    }
    
}