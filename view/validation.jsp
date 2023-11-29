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
    <link rel="stylesheet" href="/gestion-stock/assets/bootstrap/css/bootstrap.min.css">
    <title>Sortie</title>
</head>
<style>
    body {
        background-color: rgb(237, 237, 237);
    }
</style>
<body>
    <form class="container w-50 p-5 shadow-sm rounded-3 mt-5 bg-white" action="/gestion-stock/controller/sortie/valider.jsp" method="POST">
        <div class="mb-3">
            <label for="date" class="form-label">Date Initiale</label>
            <input type="date" class="form-control" name="date">
        </div>
        <input type="hidden" name="sortie" value="<%=request.getParameter("sortie") %>">
        <h4 class="my-3 text-danger"><%=error %></h4>
        <button type="submit" class="btn btn-outline-info px-5">Valider</button>
    </form>
</body>
</html>