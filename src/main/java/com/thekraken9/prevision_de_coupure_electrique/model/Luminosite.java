package com.thekraken9.prevision_de_coupure_electrique.model;

import java.sql.Date;
import java.sql.Time;

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
}
