<%-- 
    Document   : index
    Created on : Oct 21, 2011, 4:38:39 PM
    Author     : Bill
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>
<!DOCTYPE html>
<html>
    <jsp:useBean scope="session" id="userInfo" class="com.locus305.beans.UserBean" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./CSS/MainStyle.css" />
        <script type="text/javascript" src="scripts/AjaxHandler.js"></script>
        <title>Locus</title>
    </head>
    <body>
        <div id="indheader">
            <%@include file="JSPChunks/Header.jsp" %>
        </div>
        <div id="AdvertiseDiv">
            <%@include file="JSPChunks/Ad.jsp" %>
        </div>
        <div id="content">
            <%@include file="JSPChunks/Home.jsp" %>
        </div>
    </body>
</html>
