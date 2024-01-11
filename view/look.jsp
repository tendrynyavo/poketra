<%@page import="formulaire.Formulaire" %>
<%@page import="model.look.Look" %>

<%
    Formulaire formulaire = new Look().createFormulaire("");
    formulaire.getListeChamp()[1].setVisible(false);
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
    <div class="col-md-8">
        <div class="container mt-5 w-50">
            <h1>Formulaire</h1>
            <%= formulaire %>
        </div>
    </div>
    <div class="col-md-4">

    </div>
</body>
</html>