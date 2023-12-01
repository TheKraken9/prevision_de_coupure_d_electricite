package com.thekraken9.prevision_de_coupure_electrique.model;

import com.thekraken9.prevision_de_coupure_electrique.connecting.Connecting;

import java.sql.Array;
import java.sql.Connection;
import java.util.ArrayList;

public class Secteur {
    private int id;
    private String nom;
    private int id_salle;
    private int id_materiel;
    private int nombre;
    private String nom_materiel;
    private double puissance;

    public Secteur() {
    }

    public Secteur(int id, String nom, int id_salle) {
        this.id = id;
        this.nom = nom;
        this.id_salle = id_salle;
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

    public int getId_salle() {
        return id_salle;
    }

    public void setId_salle(int id_salle) {
        this.id_salle = id_salle;
    }

    public int getId_materiel() {
        return id_materiel;
    }

    public void setId_materiel(int id_materiel) {
        this.id_materiel = id_materiel;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public String getNom_materiel() {
        return nom_materiel;
    }

    public void setNom_materiel(String nom_materiel) {
        this.nom_materiel = nom_materiel;
    }

    public double getPuissance() {
        return puissance;
    }

    public void setPuissance(double puissance) {
        this.puissance = puissance;
    }

    public ArrayList<Secteur> getAllSecteur(Connection connection) throws Exception {
        boolean isConnectionProvided = false;
        if(connection == null) {
            isConnectionProvided = true;
            connection = new Connecting().getConnection("postgres");
        }

        ArrayList<Secteur> secteurs = new ArrayList<>();
        String query = "select * from secteur";
        java.sql.Statement statement = connection.createStatement();
        try {
            java.sql.ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Secteur secteur = new Secteur();
                secteur.setId(resultSet.getInt("id"));
                secteur.setNom(resultSet.getString("nom"));
                secteurs.add(secteur);
            }
        } catch (Exception e) {
            throw e;
        }
        finally {
            if(isConnectionProvided)
                connection.close();
        }
        return secteurs;
    }

    public Secteur getEnergieSolaireParSecteur(Connection connection) throws Exception {
        boolean connectionExists = true;
        if(connection == null) {
            connectionExists = false;
            connection = Connecting.getConnection("postgres");
        }
        Secteur secteur = new Secteur();
        String query = "select * from energie_par_secteur e join materiel on materiel.id=e.id_materiel where id_secteur=? and nom_materiel like 'panneau%'";
        try{
            //pourcentage
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                secteur.setId(resultSet.getInt("id_secteur"));
                secteur.setId_materiel(resultSet.getInt("id_materiel"));
                secteur.setNombre(resultSet.getInt("nombre"));
                secteur.setNom_materiel(resultSet.getString("nom_materiel"));
                secteur.setPuissance(resultSet.getDouble("puissance"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(!connectionExists){
                connection.close();
            }
        }
        return secteur;
    }

    public Secteur getEnergieBatterieParSecteur(Connection connection) throws Exception {
        boolean connectionExists = true;
        if(connection == null) {
            connectionExists = false;
            connection = Connecting.getConnection("postgres");
        }
        Secteur secteur = new Secteur();
        String query = "select * from energie_par_secteur e join materiel on materiel.id=e.id_materiel where id_secteur=? and nom_materiel like 'batterie%'";
        try{
            //pourcentage
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                secteur.setId(resultSet.getInt("id_secteur"));
                secteur.setId_materiel(resultSet.getInt("id_materiel"));
                secteur.setNombre(resultSet.getInt("nombre"));
                secteur.setNom_materiel(resultSet.getString("nom_materiel"));
                secteur.setPuissance(resultSet.getDouble("puissance"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(!connectionExists){
                connection.close();
            }
        }
        return secteur;
    }

    public void getEnergieParSecteur(Connection connection) throws Exception {
        boolean connectionExists = true;
        if(connection == null){
            connectionExists = false;
            connection = Connecting.getConnection("postgres");
        }
        ArrayList<Secteur> secteurs = new ArrayList<>();
        Secteur energiesSolaires = new Secteur();
        Secteur energiesBatteries = new Secteur();
        double energieSolaire = 0;
        double energieBatterie = 0;
        secteurs = getAllSecteur(null);
        for (Secteur secteur : secteurs) {
            this.id = secteur.getId();
            energiesSolaires = getEnergieSolaireParSecteur(connection);
            energiesBatteries = getEnergieBatterieParSecteur(connection);
            energieSolaire = energiesSolaires.getNombre() * energiesSolaires.getPuissance();
            energieBatterie = energiesBatteries.getNombre() * energiesBatteries.getPuissance();
            System.out.println("Secteur: " + secteur.getNom() + " Energie solaire: " + energieSolaire + " Energie batterie: " + energieBatterie);
        }
        try{

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(!connectionExists){
                connection.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Secteur secteur = new Secteur();
        secteur.getEnergieParSecteur(null);
    }
}
