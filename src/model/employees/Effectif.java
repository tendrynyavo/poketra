package model.employees;

import connection.BddObject;
import model.insert.Format;
import model.insert.Product;

public class Effectif extends BddObject{

    public Effectif() throws Exception {
        this.setTable("Effectifs");
        this.setPrimaryKeyName("id_effectif");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('eff_seq')");
        this.setCountPK(5);
        this.setPrefix("EFF");
    }
   
    Categorie categorie;
    Product produit;
    Format format;
    Integer nombre;
    
    public Format getFormat() {
        return format;
    }
    public void setFormat(Format format) {
        this.format = format;
    }

    public void setFormat(String format) throws Exception {
        Format f = new Format();
        f.setId(format);
        this.format = f;
    }

    public void setProduit(String format) throws Exception {
        Product f = new Product();
        f.setId(format);
        this.produit = f;
    }

    public void setCategorie(String format) throws Exception {
        Categorie f = new Categorie();
        f.setId(format);
        this.categorie = f;
    }

    public Categorie getCategorie() {
        return categorie;
    }
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    public Product getProduit() {
        return produit;
    }
    public void setProduit(Product produit) {
        this.produit = produit;
    }
    public Integer getNombre() {
        return nombre;
    }
    public void setNombre(Integer nombre) {
        if (nombre < 0)
            throw new IllegalArgumentException("Nombre est négatif");
        this.nombre = nombre;
    }

    public void setNombre(String nombre) {
        if (nombre.isEmpty())
            throw new IllegalArgumentException("Nombre est vide");
        this.setNombre(Integer.parseInt(nombre));
    }

    
}
