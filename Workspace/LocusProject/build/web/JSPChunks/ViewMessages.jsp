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
            <div>
                <a href="javascript:void(0);" onclick="changePage('JSPChunks/SendMessage.jsp')">Send New Message</a>
            </div>
            <l:ListMessages user="${userInfo.userid}">
                <div class="round-corner message" onclick="showMessage(${curIndex})">
                    <div id="messageheader${curIndex}}">
                        <span>
                            From: 
                            <c:choose>
                                <c:when test="${curMessage.sender!=0}">
                                    <a href="javascript:void(0);" onclick="changePage('JSPChunks/Profile.jsp?user=${curMessage.sendername}')">${curMessage.sendername}</a>
                                </c:when>
                                <c:otherwise>
                                    ${curMessage.sendername}
                                </c:otherwise>
                            </c:choose>
                        </span>
                        <span>Subject: ${curMessage.subject}</span>
                        <span>Sent: ${curMessage.date}</span>
                    </div>
                    <div id="messagecontent${curIndex}" class="hidden">
                        ${curMessage.content}
                    </div>
                    <div id="deletemessage${curIndex}">
                        <a href ="javascript:void(0);" onclick="delMessage(${curMessage.id})">Delete</a>
                    </div>
                </div>
            </l:ListMessages>


        </c:when>
        <c:otherwise>
            You must be logged in to view this page.
        </c:otherwise>
    </c:choose>
</div>