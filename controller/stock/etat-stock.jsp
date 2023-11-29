<%@page contentType="application/json; charset=UTF-8"%>
<%@page import="com.google.gson.Gson" %>
<%@page import="com.google.gson.GsonBuilder" %>
<%@page import="model.etat.EtatStock" %>
<%

    EtatStock etatStock = EtatStock.getEtatStock(request.getParameter("initiale"), request.getParameter("finale"), "%"+request.getParameter("article")+"%", "%"+request.getParameter("magasin")+"%");
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    out.print(gson.toJson(etatStock));

%>