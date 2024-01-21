package model.insert;

import connection.BddObject;

public class Format extends BddObject{

    String nom;
    
    public Format() throws Exception {
        super();
        this.setTable("Format");
        this.setPrimaryKeyName("id_format");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('seq_format')");
        this.setCountPK(5);
        this.setPrefix("FOR");
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
