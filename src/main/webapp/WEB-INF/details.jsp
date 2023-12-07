<%@ page import="java.util.ArrayList" %>
<%@ page import="com.thekraken9.prevision_de_coupure_electrique.model.DetailsConso" %><%--
  Created by IntelliJ IDEA.
  User: thekraken9
  Date: 2023-12-04
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<DetailsConso> detailsConso = (ArrayList<DetailsConso>) request.getAttribute("detailsConsos");
%>
<html>
<head>
    <title>Details consommation</title>
</head>
<body>
<h1>Details consommation</h1>
<table>
    <tr>
        <th>Date</th>
        <th>Secteur</th>
        <th>Heure</th>
        <th>Luminosite</th>
        <th>Energie Solaire</th>
        <th>Energie Batterie</th>
        <th>Consommation par heure</th>
        <th>A prendre dans la batterie</th>
        <th>Batterie consommée</th>
    </tr>
    <% for (DetailsConso detailsConso1 : detailsConso) { %>
    <tr>
        <td><%= detailsConso1.getDate() %></td>
        <td><%= detailsConso1.getId_secteur() %></td>
        <td><%= detailsConso1.getHeure() %></td>
        <td><%= detailsConso1.getEtat() %></td>
        <td><%= detailsConso1.getEnergie_solaire_initiale() %></td>
        <td><%= detailsConso1.getEnergie_batterie_initiale() %></td>
        <td><%= detailsConso1.getConsommation_totale_par_heure() %></td>
        <td><%= detailsConso1.getA_prendre_dans_la_batterie() %></td>
        <td><%= detailsConso1.isBatterie_consommee() %></td>
    </tr>
    <% } %>
</body>
</html>
