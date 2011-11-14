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
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./CSS/MainStyle.css" />
        <title>Locus</title>
    </head>
    <body>
        <%@page import="java.sql.*" %>
            
        <l:header message="MESSAGE"/>
        <div id="content">
        <% try {
            
            Class.forName("com.mysql.jdbc.Driver");
            String connectionURL = "jdbc:mysql://mysql1.cs.sunysb.edu?"+
                    "user=wpeckham&password=106690352";
            Connection con = DriverManager.getConnection(connectionURL);        
        
        %>
        <h1>Hello World!</h1>
        <%
            Statement stmt = null;
            ResultSet rs = null;
            String st = "SELECT * FROM wpeckham.PERSONS";
            stmt = con.createStatement();
            rs = stmt.executeQuery(st);
        %>
        <p class="round-corner">
        <%
        while(rs.next()){
            out.println("SSN: " + rs.getString("SSN") + "&nbsp;&nbsp;&nbsp;&nbsp;NAME: " + rs.getString("First_Name") + " " + rs.getString("Last_Name")+ "<br />");
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
        
        <% } catch(SQLException e) {
            e.printStackTrace(response.getWriter());
        } catch(ClassNotFoundException e){
            e.printStackTrace(response.getWriter());
        }
        %>
       </div>
     </body>
</html>
