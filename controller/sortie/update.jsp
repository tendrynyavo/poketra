<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="model.mouvement.Mouvement" %>
<%@page errorPage="../../update.jsp" %>
<%

    Mouvement.update(request.getParameter("sortie"), request.getParameter("date"), request.getParameter("article"), request.getParameter("quantite"), request.getParameter("magasin"));
    response.sendRedirect("/gestion-stock/liste-sortie.jsp");

%>