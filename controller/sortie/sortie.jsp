<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="model.article.Article" %>
<%@page errorPage="../../sortie.jsp" %>
<%

    Article.sortir(request.getParameter("article"), request.getParameter("quantite"), request.getParameter("magasin"), request.getParameter("date"), request.getParameter("unite"));
    response.sendRedirect("/gestion-stock/sortie.jsp?article=" + request.getParameter("article"));

%>