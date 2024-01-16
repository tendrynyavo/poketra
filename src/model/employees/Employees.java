package model.employees;

import connection.BddObject;

public class Employees extends BddObject{
    String nom;
    Categories categories;

    public Employees() throws Exception{
        super();
        this.setTable("Employees");
        this.setPrimaryKeyName("id_employe");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('empl_seq')");
        this.setCountPK(5);
        this.setPrefix("EMP");
    }
    
    public String getNom() {
        return nom;
    }
    public Categories getCategories() {
        return categories;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    
}
