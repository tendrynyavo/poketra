<%@page import="formulaire.Formulaire"%>
<%@page import="model.employees.Categorie"%>
<%

    Formulaire formulaire = new Categorie().createFormulaire("insert");
    formulaire.setTitle("Insertion de Categorie");
    formulaire.setRedirect("/poketra/entity/Categorie.jsp");
    formulaire.setRedirectError("/poketra/entity/Categorie.jsp?error=");
    String error = (request.getParameter("error") == null) ? "" : request.getParameter("error");
    formulaire.setError(error);

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
                <jsp:include page="./navbar.html" />
                <div class="mx-auto w-50 p-5 bg-white rounded shadow-sm">
                    <%=formulaire %>
                </div>
            </div>
        </div>
    </div>
</body>
</html>