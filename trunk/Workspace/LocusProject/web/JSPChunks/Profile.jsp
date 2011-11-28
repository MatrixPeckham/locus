<%-- 
    Document   : Profile
    Created on : Nov 24, 2011, 10:54:48 PM
    Author     : Owner
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>

<div id="userinfo" class="round-corner">
    <c:choose>
        <c:when test="${sessionScope.userInfo.username!=''}">
            <form id="userinfo" action="javascript:void(0);">
                <div>
                    <span><label>User Name: </label></span><span><input type="text" readonly="true" value="${sessionScope.userInfo.username}"></input></span>
                </div>
                <div>
                    <span><label>First Name: </label></span><span><input type="text" id="fname" value="${sessionScope.userInfo.fname}"></input></span>
                </div>
                <div>
                    <span><label>Last Name: </label></span><span><input type="text" id="lname" value="${sessionScope.userInfo.lname}"></input></span>
                </div>
                <div>
                    <span><label>Address: </label></span><span><input type="text" id="addr" value="${sessionScope.userInfo.addr}"></input></span>
                </div>
                <div>
                    <span><label>City: </label></span><span><input type="text" id="city" value="${sessionScope.userInfo.city}"></input></span>
                </div>
                <div>
                    <span><label>State: </label></span><span><input type="text" id="state" value="${sessionScope.userInfo.state}"></input></span>
                </div>
                <div>
                    <span><label>Zip Code: </label></span><span><input type="text" id="zip" value="${sessionScope.userInfo.zip}"></input></span>
                </div>
                <div>
                    <span><label>Phone Number: </label></span><span><input type="text" id="phone" value="${sessionScope.userInfo.phone}"></input></span>
                </div>
                <div>
                    <span><label>Advertisement Preferences:<br/>Comma Separated</label></span><span><input type="text" id="preferences" value="${sessionScope.userInfo.preferences}"></input></span>
                </div>
                <input type="hidden" id="numCards" value="0"/>
                <table id="cardtable"> 
                    <l:LoopUserAccounts username="${sessionScope.userInfo.username}" var="acct" ivar="ind">
                        <tr><td>Credit Card Number #${ind+1}</td><td><input type="hidden" value="${acct.accnum}" id="cardacc${ind}"/><input type="text" id="cardnum${ind}" value="${acct.ccn}"/></td></tr>
                        <script type="text/javascript">
                            incNumCards();
                        </script>
                    </l:LoopUserAccounts>
                </table>
                <!--<input type="submit" onclick="addCard()" value="Add Another"/><br/>-->
                <input type="submit" onclick="saveProfile()" value="Save"/>
            </form>
        </c:when>
        <c:otherwise>
            You must log on before viewing this page.
        </c:otherwise>
    </c:choose>
</div>