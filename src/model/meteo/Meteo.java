package model.meteo;

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
        this.getColumns().get(1).setName("date_meteo");
        this.setTable("meteo");
        this.setPrimaryKeyName("id_meteo");
        this.setConnection("PostgreSQL");
    }
    
}
