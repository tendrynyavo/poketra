<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="model.magasin.Magasin" %>
<%@page import="model.mouvement.Unite" %>
<%@page import="model.article.Article" %>
<%@page isErrorPage="true" %>
<%

  Magasin[] magasins = (Magasin[]) new Magasin().findAll(null);
  Unite[] unites = Article.getUnite(request.getParameter("article"));
  String error = (exception == null) ? "" : exception.getMessage();

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/gestion-stock/assets/bootstrap/css/bootstrap.min.css">
    <title>Entree</title>
</head>
<style>
    body {
        background-color: rgb(237, 237, 237);
    }
</style>
<body>
    <form class="container w-50 p-5 shadow-sm rounded-3 mt-5 bg-white" action="/gestion-stock/controller/entree/entree.jsp" method="POST">
        <div class="mb-3">
            <label for="date" class="form-label">Date</label>
            <input type="date" class="form-control" name="date">
        </div>
        <div class="mb-3">
            <input type="hidden" class="form-control" name="article" value="<%=request.getParameter("article") %>">
        </div>
        <div class="mb-3">
            <label for="quantite" class="form-label">Quantité</label>
            <input type="number" class="form-control" name="quantite">
        </div>
        <div class="mb-3">
            <label for="quantite" class="form-label">Prix Unitaire</label>
            <input type="text" class="form-control" name="prix">
        </div>
        <div class="mb-3">
            <label for="magasin" class="form-label">Magasin</label>
            <select class="form-select" name="magasin">
                <% for (Magasin magasin : magasins) { %>
                <option value="<%=magasin.getId() %>"><%=magasin.getNom() %></option>
                <% } %>
            </select>
        </div>
        <div class="mb-3">
            <label for="magasin" class="form-label">Unite</label>
            <select class="form-select" name="unite">
                <% for (Unite unite : unites) { %>
                <option value="<%=unite.getId() %>"><%=unite.getNom() %></option>
                <% } %>
            </select>
        </div>
        <h4 class="my-3 text-danger"><%=error %></h4>
        <button type="submit" class="btn btn-outline-info px-5">Entrer</button>
    </form>
</body>
</html>