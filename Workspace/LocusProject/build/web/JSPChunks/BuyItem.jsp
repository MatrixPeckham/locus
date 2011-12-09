<%-- 
    Document   : BuyItem
    Created on : Dec 6, 2011, 1:39:12 PM
    Author     : Owner
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>
<l:GetAd adid="${param.ad}" var="item" />

<div id="buyItem" class="round-corner">
    <h1>Purchase</h1>
    You are going to purchase a ${item.item} for ${item.unitPrice}
    <c:choose>
        <c:when test="${sessionScope.userInfo.userid!=-1}">
            <br />Select an account to use:
            <form id="purchase" action="javascript:void(0);">
                <l:LoopUserAccounts ivar="ind" username="${sessionScope.userInfo.username}" var="curAcct">
                    <input type="radio" name="account" value="${curAcct.accnum}" />Card ending in: ${curAcct.ccn}<br/>
                </l:LoopUserAccounts>
                <br />Number of Items<input type="text" id="numItems" />                
                <input type="submit" onclick="makePurchase('${item.id}')" value="Buy" />
            </form>
        </c:when>
        <c:otherwise>
            <br />You are not logged on, you can't purchase anything without being logged on.
        </c:otherwise>
    </c:choose>
</div>