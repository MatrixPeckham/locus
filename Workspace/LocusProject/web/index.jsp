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
        <link rel="stylesheet" type="text/css" href="./CSS/MainStyle.css" />
        <title>Locus</title>
    </head>
    <body>
        <%@include file="Header.jsp" %>
        <div id="content">
        <h1>Hello World!</h1>
        <p class="round-corner">
        <%
        for(int i = 0; i < 10; i++){
        %>
            Hello this is a lot of text. 
        <%
        }
        %>
        </p>
        <%
        for(int i = 0; i < 10; i++){
        %>
        <p class="round-corner"><image src="./LocusImage.png?points=<% out.print(i+50); %>" alt="Image Failed to Load" /><br/></p>

        <%
        }
        %>
        </div>
    </body>
</html>
