package model.employees;

import java.sql.Connection;

import connection.Column;
import model.insert.Format;

public class EffectifFormat extends Effectif {

    public EffectifFormat() throws Exception {
        super();
    }

    @Override
    public void insert(Connection connection, Column... args) throws Exception {
        boolean connect = true;
        try {
            if (connection == null) {
                connection = this.getConnection();
                connect = false;
            }

            Effectif[] effectifs = new Effectif[2];
    
            effectifs[0] = new Effectif();
            
            effectifs[0].setCategorie(this.getCategorie());
            effectifs[0].setProduit(this.getProduit());
            effectifs[0].setNombre(this.getNombre());
            Format foo0 = new Format();
            foo0.setId("1");
            effectifs[0].setFormat(foo0);
            
            effectifs[1] = new Effectif();
            effectifs[1].setCategorie(this.getCategorie());
            effectifs[1].setProduit(this.getProduit());
            effectifs[1].setNombre(this.getNombre() * 2);
            Format foo1 = new Format();
            foo1.setId("2");
            effectifs[1].setFormat(foo1);
    
            for (Effectif effectif : effectifs) {
                effectif.insert(connection);
            }

            connection.commit();
        } catch (Exception e) {
            if (connection != null)
                connection.rollback();
            throw e;
        } finally {
            if (connection != null && connect)
                connection.close();
        }
    }
    
}
