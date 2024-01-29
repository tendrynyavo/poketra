package model.look;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import connection.BddObject;
import model.matiere.*;

public class Look extends BddObject{
    String nom;

    Matiere[] matieres;

    public Look() throws Exception {
        super();
        this.setTable("Look");
        this.setPrimaryKeyName("id");
        this.setConnection("PostgreSQL");
    }

    public Look(String id, String nom) throws Exception {
        super();
        setId(id);
        setNom(nom);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Matiere[] getMatieres() {
        return matieres;
    }

    public void setMatieres(Matiere[] matieres) {
        this.matieres = matieres;
    }

    public Look getLook(String id) throws Exception {
        String sql = "SELECT * FROM v_look_matiere WHERE id_look = ?;";
        List<Matiere> matieres = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Look look = null;
        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, Integer.parseInt(id));

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                if(look == null) {
                    look = new Look(resultSet.getString(2), resultSet.getString(3));
                }
            }

            if (look != null)
                look.setMatieres(matieres.toArray(new Matiere[0]));

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return look;
    }
}