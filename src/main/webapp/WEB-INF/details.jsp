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
    <link rel="stylesheet" href="assets/bootstrap.css">
</head>
<body class="container mt-5">
<h1 style="text-align: center">Details consommation</h1>
<table class="table table-success table-striped">
    <tr>
        <th scope="col">Date</th>
        <th scope="col">Secteur</th>
        <th scope="col">Heure</th>
        <th scope="col">Luminosite</th>
        <th scope="col">Energie Solaire</th>
        <th scope="col">Energie Batterie</th>
        <th scope="col">Consommation par heure</th>
        <th scope="col">A prendre dans la batterie</th>
        <th scope="col">Batterie consommée</th>
    </tr>
    <% for (DetailsConso detailsConso1 : detailsConso) { %>
    <tr>
        <th scope="row"><%= detailsConso1.getDate() %></th>
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
</table>
</body>
</html>
