package com.thekraken9.prevision_de_coupure_electrique.model;

public class Materiel {
    private int id;
    private String nom;
    private double puissance;

    public Materiel() {
    }

    public Materiel(int id, String nom, double puissance) {
        this.id = id;
        this.nom = nom;
        this.puissance = puissance;
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

    public double getPuissance() {
        return puissance;
    }

    public void setPuissance(double puissance) {
        this.puissance = puissance;
    }
}
