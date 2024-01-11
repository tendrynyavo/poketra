<%@page import="formulaire.table.Table"%>
<%@page import="model.prix.ListeProduitFiltre"%>
<%

    ListeProduitFiltre[] filtres = new ListeProduitFiltre().filtre(request.getParameter("min"), request.getParameter("max"));

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
            <div class="col-sm-auto sticky-top m-3 rounded-sidebar shadow-sm" style="background-color: #353e37;">
                <div class="d-flex flex-sm-column rounded-sidebar flex-row flex-nowrap align-items-center sticky-top" style="background-color: #353e37;">
                    <ul class="p-3 nav nav-flush flex-sm-column flex-row flex-nowrap mb-auto mx-auto text-center align-items-center">
                        <li class="nav-item mt-3">
                            <a href="/poketra/Format.jsp" class="link-dark nav-link rounded-circle item">
                                <i style="color: white;" class="bi-box-seam fs-4"></i>
                            </a>
                        </li>
                        <li class="nav-item mt-3">
                            <a href="/poketra/Product.jsp" class="link-dark nav-link rounded-circle item">
                                <i style="color: white;" class="bi-view-list fs-4"></i>
                            </a>
                        </li>
                        <li class="nav-item mt-3">
                            <a href="/poketra/Matiere.jsp" class="link-dark nav-link rounded-circle item">
                                <i style="color: white;" class="bi-basket fs-4"></i>
                            </a>
                        </li>
                        <li class="nav-item mt-3">
                            <a href="/poketra/Quantite.jsp" class="link-dark nav-link rounded-circle item">
                                <i style="color: white;" class="bi-receipt fs-4"></i>
                            </a>
                        </li>
                        <li class="nav-item mt-3">
                            <a href="/commercial/log-out.do" class="link-dark nav-link rounded-circle item">
                                <i style="color: white;" class="bi-box-arrow-left fs-4"></i>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
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
                            <% for (ListeProduitFiltre filtre : filtres) { %>
                                <tr>
                                    <td><%= filtre.getProduit().getNom()%></td>
                                    <td><%= filtre.getFormat().getNom()%></td>
                                    <td><%= filtre.getPrix()%></td>
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