<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="model.mouvement.Mouvement" %>
<%

    Mouvement[] sorties = Mouvement.getSorties();

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/gestion-stock/assets/bootstrap/css/bootstrap.min.css">
    <title>Liste sortie</title>
</head>
<style>
    body {
        background-color: rgb(237, 237, 237);
    }
</style>
<body>
    <div class="bg-white shadow-sm p-5 rounded-3 container w-75 mt-5">
        <h2>Liste des sorties</h2>
        <table class="table mt-4">
            <thead>
              <tr>
                <th scope="col">Article</th>
                <th scope="col">Date</th>
                <th scope="col">Quantite</th>
                <th scope="col">Magasin</th>
                <th scope="col">Unite</th>
                <th scope="col">Quantite réel</th>
                <th scope="col"></th>
                <th scope="col"></th>
              </tr>
            </thead>
            <tbody>
              <% for (Mouvement sortie : sorties) { %>
              <tr>
                <th class="align-middle" scope="row"><%=sortie.getArticle().getNom() %></th>
                <td class="align-middle"><%=sortie.getDate() %></td>
                <td class="align-middle"><%=sortie.getQuantite() %></td>
                <td class="align-middle"><%=sortie.getMagasin().getNom() %></td>
                <td class="align-middle"><%=sortie.getUnite().getNom() %></td>
                <td class="align-middle"><%=sortie.getQuantiteReel() %></td>
              </tr>
              <% } %>
            </tbody>
          </table>
    </div>
</body>
</html>