<%@page import="model.employees.Employee"%>
<%@page import="formulaire.table.Table"%>
<%@page import="formulaire.component.Button"%>
<%

    Employee[] employees = new Employee().getAncienneteEmployee();
    Table table = new Table().createTable(employees).addButton(new Button("Update", "Id", "btn btn-primary", "update.jsp?id="));
    table.getChamps()[4].setVisible(false);
    table.getChamps()[7].setVisible(false);
    table.getChamps()[5].setVisible(false);
    table.getChamps()[3].setLabel("Taux horaire");

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
                <div class="mx-auto p-5 bg-white rounded shadow-sm">
                    <h2>Liste des postes par employee</h2>
                    <%=table %>
                </div>
            </div>
        </div>
    </div>
</body>
</html>