package com.thekraken9.prevision_de_coupure_electrique.model;

import com.thekraken9.prevision_de_coupure_electrique.connecting.Connecting;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Historique {
    private int id;
    private int id_salle;
    private Date date;
    private LocalTime heure;
    private int nombre_etudiant;

    public Historique() {
    }

    public Historique(int id, int id_salle, Date date, LocalTime heure, int nombre_etudiant) {
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

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public int getNombre_etudiant() {
        return nombre_etudiant;
    }

    public void setNombre_etudiant(int nombre_etudiant) {
        this.nombre_etudiant = nombre_etudiant;
    }

    public ArrayList<Historique> getNombreEtudiant(Connection connection) throws Exception {
        if (connection == null)
            connection = Connecting.getConnection("postgres");

        ArrayList<Historique> historiques = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        dates = this.getDatesDeMemeJour();

        for (Date date : dates) {
            Historique historique = new Historique();
            historique = this.getHistoriqueByDate(connection, date);
            historiques.add(historique);
        }
        return historiques;
    }

    public ArrayList<Historique> getNombreEtudiantParDate(Connection connection, int id_sect) throws Exception {
        boolean isConnection = false;
        if (connection == null) {
            isConnection = true;
            connection = Connecting.getConnection("postgres");
        }
        ArrayList<Historique> historiques = new ArrayList<>();
        String query = "select s.id_secteur,sum(nombre_personne),heure_hist from historique join salle_par_secteur s on s.id_salle=historique.id_salle where date_hist= ? and id_secteur= ? group by s.id_secteur,heure_hist";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, this.getDate());
            preparedStatement.setInt(2, id_sect);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Historique historique = new Historique();
                historique.setId_salle(resultSet.getInt("id_secteur"));
                historique.setNombre_etudiant(resultSet.getInt("sum"));
                historique.setHeure(resultSet.getTime("heure_hist").toLocalTime());
                historiques.add(historique);
            }
        }catch (Exception e){
            throw e;
        }
        finally {
            if(isConnection) {
                connection.close();
            }
        }
        return historiques;
    }

    public Historique getHistoriqueByDate(Connection connection, Date date) throws Exception {
        boolean isConnection = false;
        if (connection == null) {
            isConnection = true;
            connection = Connecting.getConnection("postgres");
        }

        Historique historique = new Historique();

        String query = "SELECT * FROM historique WHERE date_hist = ?";
        java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
        try{
            preparedStatement.setDate(1, date);
            //System.out.println(date);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                historique.setId(resultSet.getInt("id"));
                historique.setDate(resultSet.getDate("date_hist"));
                historique.setHeure(resultSet.getTime("heure_hist").toLocalTime());
                historique.setId_salle(resultSet.getInt("id_salle"));
                historique.setNombre_etudiant(resultSet.getInt("nombre_personne"));
                //System.out.println(historique.getDate());
            }
        }catch (Exception e){
            throw e;
        }
        finally {
            if(isConnection) {
                preparedStatement.close();
                connection.close();
            }
        }
        return historique;
    }

    public ArrayList<Date> getDatesDeMemeJour() throws Exception{
        ArrayList<Date> dates = new ArrayList<>();
        Date datePrecedente = this.getDate();
        Date premierDate = Date.valueOf(this.getLastDate(null));
        boolean isSameDay = true;
        try{
            while (datePrecedente.compareTo(premierDate) >= 0) {
                datePrecedente = Date.valueOf(datePrecedente.toLocalDate().minusDays(7));
                //System.out.println("date precedente :" + datePrecedente);
                if(this.getHistoriqueByDate(null, datePrecedente).getHeure() != null)
                    dates.add(datePrecedente);
            }
        }catch (Exception e){
            throw e;
        }
        return dates;
    }

    public LocalDate getLastDate(Connection connection) throws Exception {
        boolean isConnectionProvided = false;
        if(connection == null) {
            isConnectionProvided = true;
            connection = new Connecting().getConnection("postgres");
        }
        String query = "select min(date_hist) from historique";
        LocalDate date = null;
        try{
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                date = resultSet.getDate("min").toLocalDate();
                //System.out.println("date2 :" + resultSet.getDate("date_hist"));
            }
        }catch (Exception e){
            throw e;
        }
        finally {
            if(isConnectionProvided)
                connection.close();
        }
        return date;
    }

    public ArrayList<Date> getAllDates(Connection connection) throws Exception {
        boolean isConnectionProvided = false;
        if(connection == null) {
            isConnectionProvided = true;
            connection = new Connecting().getConnection("postgres");
        }
        String query = "select distinct date_hist from historique";
        ArrayList<Date> dates = new ArrayList<>();
        try{
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                dates.add(resultSet.getDate("date_hist"));
                //System.out.println("date2 :" + resultSet.getDate("date_hist"));
            }
        }catch (Exception e){
            throw e;
        }
        finally {
            if(isConnectionProvided)
                connection.close();
        }
        return dates;
    }

    public static void main(String[] args) throws Exception {
        Historique historique = new Historique();
        historique.setDate(Date.valueOf("2023-12-27"));
        System.out.println(historique.getDatesDeMemeJour());
        //System.out.println(historique.getHistoriqueByDate(null, Date.valueOf("2023-10-05")).getHeure());
        //System.out.println(historique.getNombreEtudiant(null));
    }
}
