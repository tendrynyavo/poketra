package model.meteo;

import java.sql.Connection;
import model.temps.Intervalle;

public class Meteo extends Intervalle {

    double luminosite;

    public double getLuminosite() {
        return luminosite;
    }

    public void setLuminosite(double luminosite) {
        this.luminosite = luminosite;
    }

    public Meteo() throws Exception {
        super();
        this.getColumn("date").setName("date_meteo");
        this.setTable("meteo");
        this.setPrimaryKeyName("id_meteo");
        this.setConnection("PostgreSQL");
    }

    public static Meteo createMeteo(Connection connection) throws Exception {
        return (Meteo) createIntervalle(connection, new Meteo());
    }
    
}
