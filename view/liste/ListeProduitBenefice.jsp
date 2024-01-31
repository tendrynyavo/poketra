<%@page import="formulaire.table.Table"%>
<%@page import="model.prix.ListeProduitBenefice"%>
<%@page import="formulaire.Formulaire"%>
<%@page import="model.affichage.Filtre"%>
<%

    ListeProduitBenefice[] filtres = new ListeProduitBenefice().filtre(request.getParameter("min"), request.getParameter("max"));
    Table table = new Table().createTable(filtres);
    table.getChamps()[7].setVisible(false);

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/poketra/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/poketra/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/poketra/assets/bootstrap-icons/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/poketra/assets/css/style.css">
    <link rel="stylesheet" href="/poketra/assets/css/demande/demande.css">
    <title>Document</title>
</head>
<body>
    <div class="container-fluid">
        <div class="row" style="background-color: #f5f5f5;">
            
            <jsp:include page="../header.html" />

            <div class="col-sm p-3 min-vh-100">
                <div class="mx-auto p-5 bg-white rounded shadow-sm">
                    <h2>Liste Produit entre <%=request.getParameter("min") %> et <%=request.getParameter("max") %></h2>
                    <%=table %>
                </div>
            </div>
        </div>
    </div>
</body>
</html>