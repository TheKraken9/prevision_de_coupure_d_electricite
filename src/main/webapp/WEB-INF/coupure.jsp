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
</head>
<body>
    <h1>Coupures</h1>
    <table>
        <tr>
            <th>Secteur</th>
            <th>Date</th>
            <th>Heure</th>
            <th>Consommation par étudiant</th>
            <th>Nombre Etudiant matin</th>
            <th>Nombre Etudiant midi</th>
            <th>Actions</th>
        </tr>
        <% for (Coupure coupure : coupures) { %>
            <tr>
                <td><%= coupure.getId_secteur() %></td>
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
