<%@ page contentType="text/html; charset=UTF-8" %>
<%@page isErrorPage="true" %>
<%

    String error = (exception == null) ? "" : exception.getMessage();

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./assets/bootstrap/css/bootstrap.min.css">
    <title>Sortie</title>
</head>
<style>
    body {
        background-color: rgb(237, 237, 237);
    }
</style>
<body>
    <form class="container w-50 p-5 shadow-sm rounded-3 mt-5 bg-white" action="./etat-stock.jsp">
        <div class="mb-3">
            <label for="date" class="form-label">Date Initiale</label>
            <input type="date" class="form-control" name="initiale">
        </div>
        <div class="mb-3">
            <label for="article" class="form-label">Date Finale</label>
            <input type="date" class="form-control" name="finale">
        </div>
        <div class="mb-3">
            <label for="quantite" class="form-label">Article</label>
            <input type="text" class="form-control" name="article">
        </div>
        <div class="mb-3">
            <label for="quantite" class="form-label">Magasin</label>
            <input type="text" class="form-control" name="magasin">
        </div>
        <h4 class="my-3 text-danger"><%=error %></h4>
        <button type="submit" class="btn btn-outline-info px-5">Valider</button>
    </form>
</body>
</html>