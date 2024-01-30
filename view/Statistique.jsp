<%@page import="formulaire.table.Table"%>
<%@page import="model.mouvement.Statistique"%>
<%

    Statistique[] statistiques = new Statistique().filtre("%"+request.getParameter("produit")+"%", "%"+request.getParameter("format")+"%");
    Table table = new Table().createTable(statistiques);
    table.getChamps()[2].setVisible(false);

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="/poketra/assets/js/Chart.js"></script>
    <link rel="stylesheet" href="/poketra/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/poketra/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/poketra/assets/bootstrap-icons/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/poketra/assets/css/style.css">
    <link rel="stylesheet" href="/poketra/assets/css/demande/demande.css">
    <title>Genre</title>
</head>
<body>
    <div class="container-fluid">
        <div class="row" style="background-color: #f5f5f5;">
            
            <jsp:include page="./header.html" />

            <div class="col-sm p-3 min-vh-100">
                <div class="mx-auto w-50 p-5 bg-white rounded shadow-sm">
                    <h2>Statistique</h2>
                    <%=table %>
                    <canvas id="doughnutChart" width="400" height="400"></canvas>
                </div>
                <!-- Create a canvas element to render the chart -->

            </div>
        </div>
    </div>
    <script>
        // Sample data for the Doughnut Chart
        var data = {
            labels: ['<%=statistiques[0].getGenre().getNom() %>', '<%=statistiques[1].getGenre().getNom() %>'],
            datasets: [{
                data: [<%=statistiques[0].getNombre() %>, <%=statistiques[1].getNombre() %>], // Sample values for each category
                backgroundColor: ['red', 'green'], // Colors for each segment
            }]
        };

        // Get the canvas element and render the Doughnut Chart
        var ctx = document.getElementById('doughnutChart').getContext('2d');
        var doughnutChart = new Chart(ctx, {
            type: 'doughnut',
            data: data,
            options: {
                // Add any additional options or customization here
            }
        });
    </script>
</body>
</html>