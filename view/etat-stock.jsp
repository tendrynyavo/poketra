<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="model.etat.EtatStock" %>
<%@page import="model.etat.ListeStock" %>
<%@page errorPage="./choix-stock.jsp" %>
<%

  EtatStock etatStock = EtatStock.getEtatStock(request.getParameter("initiale"), request.getParameter("finale"), "%"+request.getParameter("article")+"%", "%"+request.getParameter("magasin")+"%");

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/gestion-stock/assets/bootstrap/css/bootstrap.min.css">
    <title>Etat stock</title>
</head>
<style>
    body {
        background-color: rgb(237, 237, 237);
    }
</style>
<body>
    <div class="bg-white shadow-sm p-5 rounded-3 container w-75 mt-5">
        <h2>Etat de stock</h2>
        <div class="d-flex">
            <h5>Date initial : <%=etatStock.getInitiale() %></h5>
            <h5 class="ms-5">Date finale : <%=etatStock.getFinale() %></h5>
        </div>
        <table class="table mt-4">
            <thead>
              <tr>
                <th scope="col">Code</th>
                <th scope="col">Article</th>
                <th scope="col">Quantite Initiale</th>
                <th scope="col">Reste</th>
                <th scope="col">Prix Unitaire Moyenne Pondérée</th>
                <th scope="col">Magasin</th>
                <th scope="col">Montant</th>
              </tr>
            </thead>
            <tbody>
              <% for (ListeStock stock : etatStock.getStocks()) { %>
              <tr>
                <th scope="row"><%=stock.getCode() %></th>
                <td><%=stock.getNom() %></td>
                <td><%=stock.getQuantiteFormat() %></td>
                <td><%=stock.getResteFormat() %></td>
                <td><%=stock.getPrixUnitaireMoyennePondereeFormat() %></td>
                <td><%=stock.getMagasin().getNom() %></td>
                <td><%=stock.getMontantFormat() %></td>
              </tr>
              <% } %>
            </tbody>
          </table>
          <h5>Montant Total : <%=etatStock.getMontantFormat() %></h5>
          <div class="mt-4">
            <a href="./sortie.jsp" class="btn btn-outline-info">Sortir un article</a>
            <a href="./liste-sortie.jsp" class="btn btn-outline-info">Liste des sorties</a>
            <a href="./choix-stock.jsp" class="btn btn-outline-info">Revenir</a>
          </div>
    </div>
</body>
</html>