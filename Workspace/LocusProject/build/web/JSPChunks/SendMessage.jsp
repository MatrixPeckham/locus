<%-- 
    Document   : SendMessage
    Created on : Nov 30, 2011, 6:06:42 PM
    Author     : Comedy Option
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>
<!DOCTYPE html>
<div id="sendmessage" class="round-corner"> 
    <div>
        <h1>Send Message</h1>
    </div>
    <c:choose>
        <c:when test="${sessionScope.userInfo.username!=''}">

            <form id="sendmessage" action="javascript:void(0);">
                <table id ="sendmessagetable">
                    <tr>
                        <td class="formlabel"><label>To: </label></td>
                        <td class="forminput"><input id="recipient" onchange="targetNameValid(this.value)" onkeyup="targetNameValid(this.value)" type="text" value="${param.touser}"/></td>
                        <td class="formerror">
                            <label id="usernotfound" class="error">User Does Not Exist</label>
                            <label id="lookupname" class="error">Looking Up Name...</label>
                            <label id="servererrname" class="error">Error Contacting Server</label>
                        </td>
                    </tr>
                    <tr>
                        <td class="formlabel"><label>Subject: </label></td>
                        <td class="forminput"><input id="subject" type ="text" /></td>
                    </tr>
                    <tr>
                        <td class="formlabel"><label>Message: </label></td>
                        <td class="forminput"><textarea id="msgcontent"></textarea></td>
                    </tr>
                    <tr>
                        <td class ="formlabel"><label>Message may be up to 100 characters long.</label></td>
                    </tr>
                    <tr>
                        <td><input type="submit" onclick="sendMessage()" value="Send"</input></td>
                    </tr>
                </table>
            </form>
        </c:when>
        <c:otherwise>
            You must log on before viewing this page.
        </c:otherwise>
    </c:choose>
</div>
