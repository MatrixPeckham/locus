<%-- 
    Document   : AcctHistory
    Created on : Dec 6, 2011, 8:18:32 PM
    Author     : Owner
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>

<div id="accthistory" class="round-corner">
    <l:LoopAccountTransactions account="${param.acct}">
        <div class="round-corner">
            Purchased ${curTrans.numUnits} ${curTrans.b.item}(s) from ${curTrans.b.company} on ${curTrans.date}
        </div>
    </l:LoopAccountTransactions>
</div>