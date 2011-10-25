<%-- 
    Document   : Header
    Created on : Oct 21, 2011, 10:21:44 PM
    Author     : Bill
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="header" class ="round-corner">
    <table >
        <tr>
            <td>
                <image src="./LocusImage.png" alt="Failed to Load Image" />
            </td>
            <%for(int i=0; i<5; i++){%>
            <td>
                <a>Temp <% out.print(i+1); %></a>
            </td>
            <%}%>
        </tr>
    </table>
</div>