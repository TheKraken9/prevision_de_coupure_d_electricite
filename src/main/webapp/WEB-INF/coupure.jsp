<%@ page import="java.util.ArrayList" %>
<%@ page import="com.thekraken9.prevision_de_coupure_electrique.model.Coupure" %><%--
  Created by IntelliJ IDEA.
  User: thekraken9
  Date: 2023-12-03
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<Coupure> coupures = (ArrayList<Coupure>) request.getAttribute("coupures");
%>
<html>
<head>
    <title>Coupures</title>
    <link rel="stylesheet" href="assets/bootstrap.css">
</head>
<body class="container mt-5">
    <h1 class="" style="text-align: center">Coupures</h1>
    <table class="table table-striped">
        <tr>
            <th scope="col">Secteur</th>
            <th scope="col">Date</th>
            <th scope="col">Heure</th>
            <th scope="col">Consommation par étudiant</th>
            <th scope="col">Nombre Etudiant matin</th>
            <th scope="col">Nombre Etudiant midi</th>
            <th scope="col">Actions</th>
        </tr>
        <% for (Coupure coupure : coupures) { %>
            <tr>
                <th scope="row"><%= coupure.getId_secteur() %></th>
                <td><%= coupure.getDate() %></td>
                <td><%= coupure.getHeure() %></td>
                <td><%= coupure.getConsommation_par_etudiant() %></td>
                <td><%= coupure.getNombre_etudiant_matin() %></td>
                <td><%= coupure.getNombre_etudiant_midi() %></td>
                <td><a href="DetailsServlet?date=<%= coupure.getDate() %>&id_secteur=<%= coupure.getId_secteur() %>&conso=<%= coupure.getConsommation_par_etudiant() %>&matin=<%= coupure.getNombre_etudiant_matin() %>&midi=<%= coupure.getNombre_etudiant_midi() %>">Details</a></td>
            </tr>
        <% } %>
    </table>


</body>
</html>
