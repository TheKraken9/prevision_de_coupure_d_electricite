package com.thekraken9.prevision_de_coupure_electrique.model;

import com.thekraken9.prevision_de_coupure_electrique.connecting.Connecting;

import java.sql.Connection;
import java.util.ArrayList;

public class Salle {
    private int id;
    private String nom;
    private int capacite;

    public Salle() {
    }

    public Salle(int id, String nom, int capacite) {
        this.id = id;
        this.nom = nom;
        this.capacite = capacite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public ArrayList<Salle> getSecteurByNom(Connection connection, int nom) throws Exception {
        boolean isConnectionProvided = false;
        if(connection == null) {
            isConnectionProvided = true;
            connection = new Connecting().getConnection("postgres");
        }

        ArrayList<Salle> salles = new ArrayList<>();
        String query = "select secteur.id as id, nom, id_salle, nom_salle,capacite from secteur join salle on secteur.id_salle=salle.id WHERE nom = " + nom;
        java.sql.Statement statement = connection.createStatement();
        try {
            java.sql.ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Salle salle = new Salle();
                salle.setId(resultSet.getInt("id"));
                salle.setNom(resultSet.getString("nom_salle"));
                salle.setCapacite(resultSet.getInt("capacite"));
                salles.add(salle);
            }
        } catch (Exception e) {
            throw e;
        }finally {
            if(isConnectionProvided) {
                statement.close();
                connection.close();
            }
        }
        return salles;
    }
}
