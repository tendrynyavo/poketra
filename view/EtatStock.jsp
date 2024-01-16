<%@page import="formulaire.table.Table"%>
<%@page import="model.prix.ListeProduitFiltre"%>
<%

    EtatStock etatStock = new EtatStock().getEtatStock(null);

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
            
            <jsp:include page="./header.html" />
            
            <div class="col-sm p-3 min-vh-100">
                <div class="mx-auto w-50 p-5 bg-white rounded shadow-sm">
                    <h2>Liste Produit entre <%=request.getParameter("min")%> et <%=request.getParameter("max")%></h2>
                    <table class="table table-striped mt-3">
                        <thead>
                            <tr>
                                <th>Nom</th>
                                <th>Format</th>
                                <th>Prix</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (ListeStock stock : etatStock.getListeStocks()) { %>
                                <tr>
                                    <td><%= stock.getMatiere().getId()%></td>
                                    <td><%= stock.getMatiere().getNom()%></td>
                                    <td><%= stock.getStock()%></td>
                                </tr>
                            <% } %>
                        </tbody>
                        </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>