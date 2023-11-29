<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="model.article.Article" %>
<%@page errorPage="../../sortie.jsp" %>
<%

    Article.entrer(request.getParameter("article"), request.getParameter("quantite"), request.getParameter("magasin"), request.getParameter("date"), request.getParameter("prix"), request.getParameter("unite"));
    response.sendRedirect("/gestion-stock/entree.jsp?article=" + request.getParameter("article"));

%>