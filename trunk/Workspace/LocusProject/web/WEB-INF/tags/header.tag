<%-- 
    Document   : header
    Created on : Nov 13, 2011, 11:24:59 PM
    Author     : William Peckham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>
<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>

<div id="header" class ="round-corner">
    <table >
        <tr>
            <td>
                <l:Rand var="random" />
                <image src="./LocusImage.png?height=100&points=25&lines=25&random=${random}" alt="Loading Locus Logo" />
            </td>
            <td onclick="changePage('JSPChunks/Home.jsp')" class="menuitem">
                Home
            </td>
            <td onclick="changePage('JSPChunks/Profile.jsp?user=${sessionScope.userInfo.username}')" class="menuitem">
                Profile
            </td>
            <td onclick="changePage('JSPChunks/ViewMessages.jsp')" class="menuitem">
                View Messages
            </td>
            <c:if test="${sessionScope.userInfo.type==1}">
                <td onclick="changePage('JSPChunks/EmployeePage.jsp')" class="menuitem">
                    Employee Page
                </td>
            </c:if>
            <c:if test="${sessionScope.userInfo.type==2}">
                <td onclick="changePage('JSPChunks/Management.jsp')" class="menuitem">
                    Manager Page
                </td>
            </c:if>
            <td class="menuitem">
                <a href="TroubleShooting.jsp">
                    Troubleshooting
                </a>
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
