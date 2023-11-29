package model.meteo;

import model.temps.Intervalle;

public class Meteo extends Intervalle {

    int luminosite;
    Meteo[] details;

    public Meteo[] getDetails() {
        return details;
    }

    public void setDetails(Meteo[] details) {
        this.details = details;
    }

    public int getLuminosite() {
        return luminosite;
    }

    public void setLuminosite(int luminosite) {
        this.luminosite = luminosite;
    }

    public Meteo() throws Exception {
        super();
        this.setTable("meteo");
        this.setPrimaryKeyName("id_meteo");
        this.setConnection("PostgreSQL");
    }
    
}
