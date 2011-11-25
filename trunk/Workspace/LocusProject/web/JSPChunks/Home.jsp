<%-- 
    Document   : Home
    Created on : Nov 23, 2011, 2:20:45 PM
    Author     : William Peckham
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>

<h1>Hello World!</h1>
<%
    out.print(true + " ");
    out.print(false);
    for(int i = 0; i < 10; i++){
%>
<p class="round-corner"><image src="./LocusImage.png?points=<% out.print(i+50); %>&height=<% out.print(10+i*50); %>" alt="Image Failed to Load" /><br/></p>
<%
    }
%>
<%
    for(int i = 0; i < 10; i++){
%>
<p class="round-corner"><image src="./LocusImage.png?points=<% out.print(5+i*5); %>&height=200" alt="Image Failed to Load" /><br/></p>
<%
    }
%>
        
<%
    for(int i = 0; i < 10; i++){
%>
<p class="round-corner"><image src="./LocusImage.png?lines=<% out.print(5+i*5); %>&height=200&points=50" alt="Image Failed to Load" /><br/></p>
<%
    }
%>
        
