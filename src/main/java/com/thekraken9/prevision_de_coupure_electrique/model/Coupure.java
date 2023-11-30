package com.thekraken9.prevision_de_coupure_electrique.model;

import java.sql.Date;
import java.sql.Time;

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
}
