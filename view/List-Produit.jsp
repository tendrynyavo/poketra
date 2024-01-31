<%@page import="model.matiere.Matiere"%>
<%@page import="model.insert.Quantite"%>

<% 

    Matiere matiere = new Matiere(request.getParameter("matiere"));
    Matiere[] matieres = (Matiere[]) new Matiere().findAll(null);
    
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/poketra/assets/bootstrap/css/bootstrap.css">
    <title>Document</title>
</head>
<body>
    <div class="container mt-5">
        <form method="get" action="./Liste.jsp">
            <select name="look" class="form-control">
                <% for (Matiere element : matieres) { %>
                <option value="<%= element.getId()%>"><%=element.getNom()%></option>
                <% } %>
            </select>
            <input type="submit" value="ok" class="btn btn-dark mt-3">
        </form>

    </div>
    
    <table class="table table-striped mt-3">
    <thead>
        <tr>
            <th>Nom</th>
            <th>Quantite</th>
        </tr>
    </thead>
    <tbody>
        <% for (Quantite quantite : matiere.getProductQuantite()) { %>
            <tr>
                <td><%= quantite.getProduct().getNom()%> <%= quantite.getFormat().getNom()%></td>
                <td><%= quantite.getQuantite()%></td>
            </tr>
        <% } %>
    </tbody>
    </table>
    
</body>
</html>