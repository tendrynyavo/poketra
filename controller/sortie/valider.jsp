<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="model.article.Article" %>
<%@page import="model.mouvement.Mouvement" %>
<%@page errorPage="../../validation.jsp" %>
<%

    Mouvement.valider(request.getParameter("sortie"), request.getParameter("date"));
    response.sendRedirect("/gestion-stock/liste-sortie.jsp");

%>