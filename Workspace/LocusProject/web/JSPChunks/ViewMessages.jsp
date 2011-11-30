<%-- 
    Document   : ViewMessages
    Created on : Nov 29, 2011, 9:44:54 PM
    Author     : William Peckham
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>
<div id="messages" class="round-corner">
    <c:choose>
        <c:when test="${userInfo.userid!=-1}">
            <l:ListMessages user="${userInfo.userid}">
                <div class="round-corner message" onclick="showMessage(${curIndex})">
                    <div id="messageheader${curIndex}}">
                        <span>From: ${curMessage.sendername}</span>
                        <span>Subject: ${curMessage.subject}</span>
                        <span>Sent: ${curMessage.date}</span>
                    </div>
                    <div id="messagecontent${curIndex}" class="hidden">
                        ${curMessage.content}
                    </div>
                </div>
            </l:ListMessages>
        </c:when>
        <c:otherwise>
            You must be logged in to view this page.
        </c:otherwise>
    </c:choose>
</div>