package com.thekraken9.prevision_de_coupure_electrique.model;

import com.thekraken9.prevision_de_coupure_electrique.connecting.Connecting;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Coupure {
    private int id;
    private Date date;
    private int id_secteur;
    private Time heure;

    public Coupure() {
    }

    public Coupure(int id, Date date, int id_secteur, Time heure) {
        this.id = id;
        this.date = date;
        this.id_secteur = id_secteur;
        this.heure = heure;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId_secteur() {
        return id_secteur;
    }

    public void setId_secteur(int id_secteur) {
        this.id_secteur = id_secteur;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public void prevoirCoupure(Connection connection, Date date) throws Exception{
        if (connection == null)
            connection = Connecting.getConnection("postgres");

        double consommation = 60;
        double consommationExtra = 0;
        double nombreEtudiant = 0;
    }

    public ArrayList<Coupure> getCoupureByDate(Connection connection) throws Exception {
        boolean isConnectionProvided = false;
        if (connection == null) {
            isConnectionProvided = true;
            connection = Connecting.getConnection("postgres");
        }

        ArrayList<Coupure> coupures = new ArrayList<>();
        String query = "SELECT * FROM coupure where date_coupure = ?";
        java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
        try {
            preparedStatement.setDate(1, this.date);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Coupure coupure = new Coupure();
                coupure.setId(resultSet.getInt("id"));
                coupure.setDate(resultSet.getDate("date_coupure"));
                coupure.setId_secteur(resultSet.getInt("id_secteur"));
                coupure.setHeure(resultSet.getTime("heure_coupure"));
                System.out.println(coupure.getId() + " " + coupure.getDate() + " " + coupure.getId_secteur() + " " + coupure.getHeure());
                coupures.add(coupure);
            }
        }catch (Exception e){
            throw e;
        }finally {
            if(isConnectionProvided) {
                connection.close();
            }
        }
        return coupures;
    }

    public static void main(String[] args) throws Exception {
        Coupure coupure = new Coupure();
        coupure.setDate(Date.valueOf("2023-11-02"));
        coupure.getCoupureByDate(null);
    }

}
