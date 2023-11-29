<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="model.mouvement.Mouvement" %>
<%

    Mouvement[] entrees = Mouvement.getEntrees();

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/gestion-stock/assets/bootstrap/css/bootstrap.min.css">
    <title>Liste entree</title>
</head>
<style>
    body {
        background-color: rgb(237, 237, 237);
    }
</style>
<body>
    <div class="bg-white shadow-sm p-5 rounded-3 container w-75 mt-5">
        <h2>Liste des entrees</h2>
        <table class="table mt-4">
            <thead>
              <tr>
                <th scope="col">Article</th>
                <th scope="col">Date</th>
                <th scope="col">Quantite</th>
                <th scope="col">Prix Unitaire</th>
                <th scope="col">Prix Unitaire Réel</th>
                <th scope="col">Magasin</th>
                <th scope="col">Unite</th>
                <th scope="col">Quantite réel</th>
                <th scope="col">Montant</th>
              </tr>
            </thead>
            <tbody>
              <% for (Mouvement entree : entrees) { %>
              <tr>
                <th class="align-middle" scope="row"><%=entree.getArticle().getNom() %></th>
                <td class="align-middle"><%=entree.getDate() %></td>
                <td class="align-middle"><%=entree.getQuantite() %></td>
                <td class="align-middle"><%=entree.getPrixUnitaire() %></td>
                <td class="align-middle"><%=entree.getPrixUnitaireReel() %></td>
                <td class="align-middle"><%=entree.getMagasin().getNom() %></td>
                <td class="align-middle"><%=entree.getUnite().getNom() %></td>
                <td class="align-middle"><%=entree.getQuantiteReel() %></td>
                <td class="align-middle"><%=entree.getMontant() %></td>
              </tr>
              <% } %>
            </tbody>
          </table>
    </div>
</body>
</html>