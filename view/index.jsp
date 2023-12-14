<%@page import="model.secteur.Secteur" %>
<%@page isErrorPage="true" %>
<%
    String error = (exception == null) ? "" : exception.getMessage();
    Secteur[] secteurs = (Secteur[]) new Secteur().findAll(null);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/gestion-solaire/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/gestion-solaire/assets/css/style.css">
    <title>Prediction</title>
</head>
<body>
    <div class="row">
        <h2 class="text-center mt-5">Prediction Solaire</h2>
        <div class="col-md-6 d-flex justify-content-center mt-5">
            <img style="border-radius: 20px;" width="500" height="500" src="/gestion-solaire/assets/img/téléchargement (1).jpg" alt="" srcset="">
        </div>
        <div class="col-md-6">
            <form class="w-75 mt-5 bg-white" action="/gestion-solaire/detail-secteur.jsp" method="GET">
                <div class="mb-3">
                    <label for="date" class="form-label">Date</label>
                    <input type="date" class="form-control" name="date">
                </div>
                <div class="mb-3">
                    <label for="magasin" class="form-label">Intervalle</label>
                    <select class="form-select" name="decallage">
                        <option value="1">Par heure</option>
                        <option value="2">Tous les 30 minutes</option>
                        <option value="60">Par minute</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="magasin" class="form-label">Secteur</label>
                    <select class="form-select" name="secteur">
                        <% for (Secteur secteur : secteurs) { %>
                        <option value="<%=secteur.getId() %>"><%=secteur.getNom() %></option>
                        <% } %>
                    </select>
                </div>
                <h4 class="my-3 text-danger"><%=error %></h4>
                <button type="submit" class="btn btn-outline-info px-5">Valider</button>
            </form>
        </div>
    </div>
</body>
</html>