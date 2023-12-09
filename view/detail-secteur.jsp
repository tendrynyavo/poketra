<%@page import="model.etat.Coupure" %>
<%@page import="model.etat.EtatSolaire" %>
<%@page import="model.secteur.Secteur" %>
<%@page errorPage="./index.jsp" %>
<%
    Coupure coupure = Secteur.predir("SEC001", request.getParameter("date"), request.getParameter("decallage"));
    EtatSolaire etatSolaire = coupure.getEtat();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/gestion-solaire/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/gestion-solaire/assets/css/style.css">
    <title>Detail</title>
</head>
<body>
    <div class="container d-flex justify-content-center mt-4">
        <img width="100" height="100" src="/gestion-solaire/assets/icons/cloudy (1).png" alt="" srcset="">
    </div>
    <div class="container border w-75 mb-5 mt-3 p-5" style="border-radius: 20px;">
        <h3 class="mb-4">Detail de consommation du <%=coupure.getNom() %></h3>
        <ul>
            <li>Capacite : <%=coupure.getPanneau().getCapacite() %> W</li>
            <li>Puissance : <%=coupure.getPanneau().getPuissance() %> Wh</li>
            <li>Puissance : <%=coupure.getPanneau().getPuissance() %> Wh</li>
            <li>Salles : <%=coupure.getSallesLettre() %></li>
            <li>Date : <%=coupure.getDate() %></li>
            <li>Heure de coupure : <%=etatSolaire.getHeureCoupure() %></li>
        </ul>
        <table class="table table-borderless">
            <thead>
                <tr>
                    <th>Time</th>
                    <th>Luminosite (C°)</th>
                    <th>Nombre d'étudiant</th>
                    <th>Consommation (Wh)</th>
                    <th>Puissance (Wh)</th>
                    <th>Capacite</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <% for (EtatSolaire detail : etatSolaire.getDetails()) { %>
                <tr>
                    <td><%=detail.getHeure() %></td>
                    <td><%=detail.getLuminosite() %></td>
                    <td><%=detail.getNombre() %></td>
                    <td><%=detail.getConsommationEtudiantFormat() %></td>
                    <td><%=detail.getPuissanceSolaireFormat() %></td>
                    <td><%=detail.getCapaciteFormat() %></td>
                    <td><img src="/gestion-solaire/assets/icons/light_<%=detail.getIcon() %>_FILL0_wght400_GRAD0_opsz24.svg"></td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <a href="/gestion-solaire/" class="btn btn-outline-info px-5">Retour</a>
    </div>
</body>
</html>