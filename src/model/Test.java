package model;

import java.sql.Connection;
import java.sql.Date;
import connection.BddObject;
import model.etat.Coupure;
import model.etat.CoupureComplet;
import model.etat.EtatSolaire;
import model.meteo.Meteo;
import model.pointage.Pointage;
import model.secteur.Salle;
import model.temps.Intervalle;

public class Test {
    
    public static void main(String[] args) throws Exception {
        try (Connection connection = BddObject.getPostgreSQL()) {
            Coupure[] coupures = (Coupure[]) new CoupureComplet().findAll(connection, "id_coupure");
            Salle salle = new Salle();
            
            for (Coupure coupure : coupures) {
                salle.setSecteur(coupure);
                Salle[] salles = (Salle[]) salle.findAll(connection, null);
                coupure.setSalles(salles);
                Meteo meteo = (Meteo) Intervalle.createIntervalle(coupure.getDate(), connection, new Meteo());
                coupure.setMeteo(meteo);
                Pointage pointage = (Pointage) Intervalle.createIntervalle(coupure.getDate(), connection, new Pointage());
                coupure.setPointage(pointage);
            }
            
        //     // Data sur la meteo et pointage a la date de coupure
        //     Meteo meteo = Meteo.createMeteo(connection);
        //     Pointage pointage = Pointage.createPointage(connection);
        //     // System.out.println(coupures[0].getTotalNombre(pointage));
        //     // EtatSolaire etat = coupures[5].getEtatSolaire(60, 100);
            // try (FileWriter myWriter = new FileWriter("filename.txt")) {
            //     for (Coupure coupure : coupures) {
                    long startTime = System.currentTimeMillis();
        
                    System.out.println(coupures[0].getId());
                    System.out.println(coupures[0].getDate());
                    System.out.println(coupures[0].getHeure());
                    // EtatSolaire etat = coupure.getEtatSolaire(60);
                    EtatSolaire etat = coupures[0].getEtatSolaire(Date.valueOf("2024-01-03"), coupures[0].getMeteo(), coupures[0].getPointage(), 80, 60);
    
                    // myWriter.write(etat.getConsommationFormat() + "\n");
                    
                    long endTime = System.currentTimeMillis();
                    
                    System.out.println(etat.getHeureCoupure());
                    System.out.println(etat.getConsommation());
                    System.out.println("That took " + (endTime - startTime) + " milliseconds");
                    // EtatSolaire[] details = etat.getDetails();
                    // for (EtatSolaire etatSolaire : details) {
                        //     System.out.println(etatSolaire.getHeure() + " " + etatSolaire.getConsommationEtudiant() + " " + etatSolaire.getPuissanceSolaire() + " " + etatSolaire.getReste() + " " + etatSolaire.getCapacite() + " " + etatSolaire.isCoupure());
                        // }
                }
            // }
        // }
    }

}