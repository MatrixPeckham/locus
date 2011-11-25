<%-- 
    Document   : Profile
    Created on : Nov 24, 2011, 10:54:48 PM
    Author     : Owner
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>

<div id="userinfo" class="round-corner">
    
    <form id="userinfo">
        
    </form>
    
    
    you have accounts
    <table>
        <l:LoopUserAccounts username="${sessionScope.userInfo.username}" var="acct">
            <tr><td>${acct.ccn}</td></tr>
        </l:LoopUserAccounts>
    </table>

</div>