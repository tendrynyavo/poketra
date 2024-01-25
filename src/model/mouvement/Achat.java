package model.mouvement;

import java.sql.Date;

import connection.BddObject;
import model.insert.Format;
import model.insert.Product;
import personne.Client;

public class Achat extends BddObject{
    Client client;
    Product product;
    Format format;
    Date date;
    int quantite;

    public Achat() throws Exception {
        this.setTable("achats");
        this.setPrimaryKeyName("id_achat");
        this.setConnection("PostgreSQL");
        this.setFunctionPK("nextval('achat_seq')");
        this.setCountPK(5);
        this.setPrefix("ACH");
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setClient(String client) throws Exception {
        this.setClient((Client) new Client().setId(client));
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setProduct(String product) throws Exception {
        this.setProduct((Product) new Product().setId(product));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date) {
        this.setDate(Date.valueOf(date));
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setQuantite(String quantite) {
        this.setQuantite(Integer.valueOf(quantite));
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void setFormat(String format) throws Exception {
        this.setFormat((Format) new Format().setId(format));
    }
    
}
