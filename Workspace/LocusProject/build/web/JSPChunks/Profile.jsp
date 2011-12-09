<%-- 
    Document   : Profile
    Created on : Nov 24, 2011, 10:54:48 PM
    Author     : Owner
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>

<div id="userinfo" class="round-corner">
    <l:GetUser usr="${param.user}" var="prof" />
    <div> 
        <h1>Profile ${param.user}</h1>
    </div>
    <c:choose>
        <c:when test="${sessionScope.userInfo.username!='' && (sessionScope.userInfo.username==param.user || sessionScope.userInfo.type==1)}">
            <div id="profileinfo" class="round-corner">
                <form id="userinfoform" action="javascript:void(0);">
                    <div>
                        <span><label>User Name: </label></span><span><input type="text" readonly value="${sessionScope.userInfo.username}" /></span>
                    </div>
                    <div>
                        <span><label>First Name: </label></span><span><input type="text" id="fname" value="${sessionScope.userInfo.fname}" /></span>
                    </div>
                    <div>
                        <span><label>Last Name: </label></span><span><input type="text" id="lname" value="${sessionScope.userInfo.lname}" /></span>
                    </div>
                    <div>
                        <span><label>Address: </label></span><span><input type="text" id="addr" value="${sessionScope.userInfo.addr}" /></span>
                    </div>
                    <div>
                        <span><label>City: </label></span><span><input type="text" id="city" value="${sessionScope.userInfo.city}" /></span>
                    </div>
                    <div>
                        <span><label>State: </label></span><span><input type="text" id="state" value="${sessionScope.userInfo.state}" /></span>
                    </div>
                    <div>
                        <span><label>Zip Code: </label></span><span><input type="text" id="zip" value="${sessionScope.userInfo.zip}" /></span>
                    </div>
                    <div>
                        <span><label>Phone Number: </label></span><span><input type="text" id="phone" value="${sessionScope.userInfo.phone}" /></span>
                    </div>
                    <div>
                        <span><label>Advertisement Preferences:<br/>Comma Separated</label></span><span><input type="text" id="preferences" value="${sessionScope.userInfo.preferences}" /></span>
                    </div>
                    <input type="hidden" id="numCards" value="0"/>
                    <table id="cardtable"> 
                        <l:LoopUserAccounts username="${param.user}" var="acct" ivar="ind">
                            <tr>
                                <td>
                                    Credit Card Number #${ind+1}
                                </td>
                                <td>
                                    <input type="hidden" value="${acct.accnum}" id="cardacc${ind}"/>
                                    <input type="text" id="cardnum${ind}" value="${acct.ccn}"/>
                                </td>
                                <td>
                                    <a href="javascript:void(0);" onclick="changePage('JSPChunks/AcctHistory.jsp?acct=${acct.accnum}&user=${param.user}')">View History</a>
                                </td>
                            </tr>
                        </l:LoopUserAccounts>
                    </table>
                    <div id="newcard">
                    </div>
                    <input type="submit" onclick="saveProfile('${param.user}')" value="Save"/>
                </form>
                <h2>Add a new Card</h2>
                <form action="javascript:void(0);">
                    Card Number:<input type="text" id="newcardnum" />
                    <input type="submit" onclick="addCard('${param.user}')"/>
                </form>
            </div>
        </c:when>
        <c:when test="${prof.username!=''}">
            <div id="profileinfo" class="round-corner">
                <div>Name: ${prof.fname} ${prof.lname}</div>
                <div>Location: ${prof.city}, ${prof.state}</div>
                <div>Advertisement Preferences: ${prof.preferences}</div>
            </div>
        </c:when>
        <c:otherwise>
            You need to be logged on to view this page.
        </c:otherwise>
    </c:choose>
    <c:if test="${prof.username!=''}">
        <div class="clearmarker">
            <div id="memberof" class="round-corner">
                <h4>Member of:</h4>
                <l:LoopCircles user="${prof.username}">
                    <div>
                        <h5><a href="javascript:void(0);" onclick="changePage('JSPChunks/ViewCircle.jsp?circle=${curCircle.id}')">${curCircle.name}</a></h5>
                    </div>
                </l:LoopCircles>
            </div>
            <div id="ownerof" class="round-corner">
                <h4>Owner of:</h4>
                <div>
                    <l:LoopOwnedCircles user="${prof.userid}">
                        <h5>
                            <a href="javascript:void(0);" onclick="changePage('JSPChunks/ViewCircle.jsp?circle=${curCircle.id}')">${curCircle.name}</a> 
                            <c:if test="${sessionScope.userInfo.username!='' && sessionScope.userInfo.username==param.user}">
                                <a href="javascript:void(0);" onclick="removeCircle('${curCircle.id}','${sessionScope.userInfo.username}')"> (remove)</a>
                            </c:if>
                        </h5>
                    </l:LoopOwnedCircles>
                    <c:if test="${sessionScope.userInfo.username!='' && sessionScope.userInfo.username==param.user}">
                        <form onsubmit="javascript:void(0)" action="javascript:void(0)" >
                            Name: <input type="text" id="newcirclename" value="" />
                            Category: <input type="text" id="newcirclecatagory" value="" />
                            <input type="checkbox" id="newpubliccheck" checked />Public
                            <input type="submit" value="Create" onclick="newCircle('${sessionScope.userInfo.username}','${sessionScope.userInfo.userid}')" />
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="clearmarker round-corner">
            Personalized ads for ${param.user}:
            <l:LoopUserAds userid="${prof.userid}">
                <div class="message round-corner" onclick="changePage('JSPChunks/BuyItem.jsp?ad=${curAd.id}')">
                    ${curAd.item} from ${curAd.company} for $${curAd.unitPrice} From Category: ${curAd.cat}: ${curAd.adContent}
                </div>
            </l:LoopUserAds>
        </div>
        <div class="clearmarker"></div>
    </c:if>
</div>