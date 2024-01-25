package personne;

import connection.BddObject;

public class Client extends BddObject{
    String idClient, nom;
    Genre genre;

    public Client() throws Exception{
        this.setTable("Clients");
        this.setPrimaryKeyName("id_client");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('client_seq')");
        this.setCountPK(5);
        this.setPrefix("CLI");
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
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

    public void setGenre(String genre) throws Exception {
        this.setGenre((Genre) new Genre().setId(genre));
    }

    
}
