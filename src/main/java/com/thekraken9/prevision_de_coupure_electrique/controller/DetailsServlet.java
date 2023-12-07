package com.thekraken9.prevision_de_coupure_electrique.controller;

import com.thekraken9.prevision_de_coupure_electrique.model.Coupure;
import com.thekraken9.prevision_de_coupure_electrique.model.DetailsConso;
import com.thekraken9.prevision_de_coupure_electrique.model.Secteur;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@WebServlet(name = "DetailsServlet", value = "/DetailsServlet")
public class DetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<DetailsConso> detailsConsos = new ArrayList<>();
        Date date = Date.valueOf(request.getParameter("date"));
        int id_secteur = Integer.parseInt(request.getParameter("id_secteur"));
        double conso = Double.parseDouble(request.getParameter("conso"));
        double matin = Double.parseDouble(request.getParameter("matin"));
        double midi = Double.parseDouble(request.getParameter("midi"));
        int matin_int = (int) matin;
        int midi_int = (int) midi;
        Coupure coupure = new Coupure();
        Secteur secteur = new Secteur();
        secteur.setId(id_secteur);
        try {
            detailsConsos = coupure.detailsCoupure(null,date, secteur, conso, matin_int, midi_int);
            request.setAttribute("detailsConsos", detailsConsos);
            request.getRequestDispatcher("WEB-INF/details.jsp").forward(request, response);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}