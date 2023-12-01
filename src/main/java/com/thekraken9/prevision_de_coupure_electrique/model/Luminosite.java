package com.thekraken9.prevision_de_coupure_electrique.model;

import com.thekraken9.prevision_de_coupure_electrique.connecting.Connecting;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Luminosite {
    private int id;
    private Date date;
    private Time heure;
    private int etat;

    public Luminosite() {
    }

    public Luminosite(int id, Date date, Time heure, int etat) {
        this.id = id;
        this.date = date;
        this.heure = heure;
        this.etat = etat;
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

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public ArrayList<Luminosite> getLuminositeByDate(Connection connection) throws Exception {
        boolean isConnectionProvided = false;
        if(connection == null) {
            isConnectionProvided = true;
            connection = Connecting.getConnection("postgres");
        }

        ArrayList<Luminosite> luminosites = new ArrayList<>();
        String query = "SELECT * FROM luminosite WHERE date_lum = ?";
        java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, this.date);
        try {
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Luminosite luminosite = new Luminosite();
                luminosite.setId(resultSet.getInt("id"));
                luminosite.setDate(resultSet.getDate("date_lum"));
                luminosite.setHeure(resultSet.getTime("heure_lum"));
                luminosite.setEtat(resultSet.getInt("etat"));
                luminosites.add(luminosite);
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération des luminosités par date : " + e.getMessage());
        }
        finally {
            if (isConnectionProvided) {
                preparedStatement.close();
                connection.close();
            }
        }
        return luminosites;
    }

    public static void main(String[] args) throws Exception {
        Luminosite luminosite = new Luminosite();
        luminosite.setDate(Date.valueOf("2023-11-02"));
        ArrayList<Luminosite> luminosites = luminosite.getLuminositeByDate(null);
        for (Luminosite l : luminosites) {
            System.out.println(l.getId() + " " + l.getDate() + " " + l.getHeure() + " " + l.getEtat());
        }
    }
}
