<%-- 
    Document   : index
    Created on : Oct 21, 2011, 4:38:39 PM
    Author     : Bill
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Locus</title>
    </head>
    <body>
        <%@include file="Header.jsp" %>
        <h1>Hello World!</h1>
        <%
        for(int i = 0; i < 10; i++){
        %>
        <image src="./LocusImage.png?points=<% out.print(i+50); %>" alt="Image Failed to Load" /><br/>
        <%
        }
        %>
    </body>
</html>
