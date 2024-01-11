<%@page import="model.look.Look"%>
<%@page import="model.matiere.Matiere"%>

<% 
    Look look = new Look().getLook(request.getParameter("look"));
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
                <option value="1">Luxe</option>
                <option value="2">Normal</option>
                <option value="3">Débraille</option>
            </select>
            <input type="submit" value="ok" class="btn btn-dark mt-3">
        </form>

    </div>
    
    <table class="table table-striped mt-3">
    <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
        </tr>
    </thead>
    <tbody>
        <% for (Matiere matiere : look.getMatieres()) { %>
            <tr>
                <td><%= matiere.getId()%></td>
                <td><%= matiere.getNom()%></td>
            </tr>
        <% } %>
    </tbody>
    </table>
    
</body>
</html>