<%-- 
    Document   : header
    Created on : Nov 13, 2011, 11:24:59 PM
    Author     : William Peckham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>


<div id="header" class ="round-corner">
    <table >
        <tr>
            <td>
                <image src="./LocusImage.png?height=100" alt="Failed to Load Image" />
            </td>
            <td><a href="javascript:void(0);" onclick="changePage('JSPChunks/Home.jsp')">Home</a></td>
            <td>
                <a href="javascript:void(0);" onclick="changePage('JSPChunks/Profile.jsp')">Profile</a>
            </td>
            <td>
                <a href="TroubleShooting.jsp">Troubleshooting</a>
            </td>
        </tr>
    </table>
</div>
<div id="login">
    <c:choose>
        <c:when test="${sessionScope.userInfo.username==''}">
            <form id="login" action="javascript:void(0)" onsubmit="login()">
                <span><label>Display Name:</label><input type="text" id="logname"></input></span>
                <span><label>Password:</label><input type="password" id="logpass"></input></span>
                <span><input type="submit" id="loggo" value="Go"></input></span>
                <br />
                <span><label class="error" id="loginerr"></label></span>
                <br/>Not a Member? Click <a onclick="changePage('JSPChunks/Registration.jsp')" href="#">here</a> to register.
            </form>
        </c:when>
        <c:otherwise>
            Welcome ${sessionScope.userInfo.username} <a href="javascript:void(0)" onclick="logout()">logout</a>
        </c:otherwise>
    </c:choose>
</div>
