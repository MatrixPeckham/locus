<%-- 
    Document   : ViewCircle
    Created on : Nov 29, 2011, 8:21:42 AM
    Author     : William Peckham
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>
<l:GetCircle var="circle" circle="${param.circle}" />
<div id="circleview">
    <div id="circlehead">
        <h1>${circle.name}</h1>
        <h3>Owner: <a href="javascript:void(0);" onclick="changePage('JSPChunks/Profile.jsp?user=${circle.ownerName}')">${circle.ownerName}</a></h3>
        <h5>
            Members:
            <l:LoopMembers circle="${circle.id}">
                <a href="javascript:void(0);" onclick="changePage('JSPChunks/Profile.jsp?user=${curUsr.username}')">${curUsr.username}</a>
                <c:if test="${userInfo.userid==circle.ownerID}">
                    <a href="javascript:void(0);" onclick="removeMember('${curUsr.userid}','${circle.id}')">(remove)</a>
                </c:if>
                    , 
            </l:LoopMembers>
        </h5>
        <h4>
            <c:choose>
                <c:when test="${ !l:isMember(sessionScope.userInfo.userid,circle.id) }">
                    You are not a member of this circle, <a href="javascript:void(0);" onclick="joinCircle(${sessionScope.userInfo.userid},${circle.id})" >Join?</a>
                    <br />
                    <span class="hidden" id="joinmessage">You have requested to join this circle, if you are accepted or declined you will receive a message.</span>
                </c:when>
                <c:otherwise>
                    You are a member of this circle, <a href="javascript:void(0);" onclick="unjoinCircle(${sessionScope.userInfo.userid},${circle.id})" >Unjoin?</a>
                </c:otherwise>
            </c:choose>
        </h4>
    </div>
    <l:LoopPosts circle="${circle.id}" >
        <div class="postview round-corner">
            <div class="postinfo">
                <div class="postauthor">
                    <a href="javascript:void(0);" onclick="changePage('JSPChunks/Profile.jsp?user=${curPost.name}')">${curPost.name}</a>
                </div>
                <div class="postlikes">
                    This Post has ${curPost.likes} likes.
                    <c:choose>
                        <c:when test="${userInfo.userid!=-1 && !l:doesLikePost(userInfo,curPost.id) }">
                            <a href="javascript:void(0);" onclick="likePost('${userInfo.userid}','${curPost.id}','${circle.id}')">Like</a>
                        </c:when>
                        <c:when test="${userInfo.userid!=-1 && l:doesLikePost(userInfo,curPost.id)}">
                            <a href="javascript:void(0);" onclick="unlikePost('${userInfo.userid}','${curPost.id}','${circle.id}')">Unlike</a>
                        </c:when>
                    </c:choose>
                </div>
                <div class="postdate">
                    ${curPost.date}
                </div>
                <div class="removepost">
                    <c:choose>
                        <c:when test="${userInfo.userid==circle.ownerID || userInfo.userid==curPost.author}">
                            <a href="javascript:void(0);" onclick="removePost('${curPost.id}','${circle.id}')">Remove</a>
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <div class="postcontent">
                <div id="postcontent${curPost.id}">
                    ${curPost.content}
                    <c:if test="${userInfo.userid==circle.ownerID || userInfo.userid==curPost.author}">
                        <div>
                            <a href="javascript:void(0);" onclick="editPost('${curPost.id}')">
                                Edit
                            </a>
                        </div>
                    </c:if>
                </div>
                <div id="postedit${curPost.id}" class="hidden">
                    <form action="javascript:void(0);" id="">
                        <textarea id="posteditcontent${curPost.id}">${curPost.content}</textarea>
                        <br />
                        <input type="submit" onclick="savePost('${curPost.id}','${circle.id}')" value="Save" /> 
                    </form>
                </div>
            </div>
            <l:LoopComments post="${curPost.id}">
                <div class="commentview round-corner">
                    <div class="commentinfo">
                        <div class="commentauthor">
                            <a href="javascript:void(0);" onclick="changePage('JSPChunks/Profile.jsp?user=${curComment.authorName}')">${curComment.authorName}</a>
                        </div>
                        <div class="commentlikes">
                            This Comment has ${curComment.likes} likes.
                            <c:choose>
                                <c:when test="${userInfo.userid!=-1 && !l:doesLikeComment(userInfo,curComment.id)}">
                                    <a href="javascript:void(0);" onclick="likeComment('${userInfo.userid}','${curComment.id}','${circle.id}')">Like</a>
                                </c:when>
                                <c:when test="${userInfo.userid!=-1 && l:doesLikeComment(userInfo,curComment.id)}">
                                    <a href="javascript:void(0);" onclick="unlikeComment('${userInfo.userid}','${curComment.id}','${circle.id}')">Unlike</a>
                                </c:when>
                            </c:choose>
                        </div>
                        <div class="commentdate">
                            ${curComment.date}
                        </div>
                        <div class="removecomment">
                            <c:choose>
                                <c:when test="${userInfo.userid==circle.ownerID || userInfo.userid==curComment.author}">
                                    <a href="javascript:void(0);" onclick="removeComment('${curComment.id}','${circle.id}')">Remove</a>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                    <div class="commentcontent">
                        <div id="commentcontent${curComment.id}">
                            ${curComment.content}
                            <c:if test="${userInfo.userid==circle.ownerID || userInfo.userid==curComment.author}">
                                <div>
                                    <a href="javascript:void(0);" onclick="editComment('${curComment.id}')">
                                        Edit
                                    </a>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <div id="commentedit${curComment.id}" class="hidden">
                        <form action="javascript:void(0);" id="">
                            <textarea id="commenteditcontent${curComment.id}">${curComment.content}</textarea>
                            <br />
                            <input type="submit" onclick="saveComment('${curComment.id}','${circle.id}')" value="Save" /> 
                        </form>
                    </div>
                    <div class="clearmarker" ></div>
                </div>
            </l:LoopComments>
            <div class="clearmarker"></div>
            <c:choose>
                <c:when test="${userInfo.userid!=-1 && l:isMember(sessionScope.userInfo.userid,circle.id)}">
                    <div id="newcommentview"> 
                        <form action="javascript:void(0)" onsubmit="" >
                            <textarea id="newcomment${curPost.id}"></textarea><br />
                            <input type="submit" onclick="addComment('${curPost.id}','${circle.id}')" value="Add Comment"></input>
                        </form>
                    </div>
                </c:when>
                <c:when test="${userInfo.userid!=-1 && !l:isMember(sessionScope.userInfo.userid,circle.id)}">
                    You must be a member of the circle to add a Comment.
                </c:when>
                <c:otherwise>
                    You must be logged in to add a comment.
                </c:otherwise>
            </c:choose>
        </div>
    </l:LoopPosts>
    <c:choose>
        <c:when test="${userInfo.userid!=-1 && l:isMember(sessionScope.userInfo.userid,circle.id)}">
            <div id="newpostview">
                <form action="javascript:void(0)" onsubmit="" >
                    <textarea id="newpost"></textarea><br />
                    <input type="submit" onclick="addPost('${circle.id}')" value="Add Post"></input>
                </form>
            </div>
        </c:when>
        <c:when test="${userInfo.userid!=-1 && !l:isMember(sessionScope.userInfo.userid,circle.id)}">
            You must be a member of the circle to add a post.
        </c:when>
        <c:otherwise>
            You must be logged in to add a post.
        </c:otherwise>
    </c:choose>

</div>