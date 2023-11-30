package com.thekraken9.prevision_de_coupure_electrique.model;

import java.sql.Date;
import java.sql.Time;

public class Historique {
    private int id;
    private int id_salle;
    private Date date;
    private Time heure;
    private int nombre_etudiant;

    public Historique() {
    }

    public Historique(int id, int id_salle, Date date, Time heure, int nombre_etudiant) {
        this.id = id;
        this.id_salle = id_salle;
        this.date = date;
        this.heure = heure;
        this.nombre_etudiant = nombre_etudiant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_salle() {
        return id_salle;
    }

    public void setId_salle(int id_salle) {
        this.id_salle = id_salle;
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

    public int getNombre_etudiant() {
        return nombre_etudiant;
    }

    public void setNombre_etudiant(int nombre_etudiant) {
        this.nombre_etudiant = nombre_etudiant;
    }
}
