package model.affichage;

import connection.BddObject;

public class Filtre extends BddObject {

    String min;
    String max;
    
    public String getMin() {
        return min;
    }
    
    public void setMin(String min) {
        this.min = min;
    }
    
    public String getMax() {
        return max;
    }
    
    public void setMax(String max) {
        this.max = max;
    }
    
    public Filtre() throws Exception {
        super();
    }
    
}
