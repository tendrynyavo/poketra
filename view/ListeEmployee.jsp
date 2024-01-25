<%@page import="model.employees.Employee"%>
<%

    Employee[] employees = new Employee().getAncienneteEmployee();

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
                    <h2>Liste des postes par employee</h2>
                    <table class="table table-striped mt-3">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nom</th>
                                <th>Poste</th>
                                <th>Anciennete</th>
                                <th>Taux horaire</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Employee employee : employees) { %>
                            <tr>
                                <td><%= employee.getId() %></td>
                                <td><%= employee.getNom() %></td>
                                <td><%= employee.getRole() %></td>
                                <td><%= employee.getAnciennete() %></td>
                                <td><%= employee.getTauxHoraire() %></td>
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