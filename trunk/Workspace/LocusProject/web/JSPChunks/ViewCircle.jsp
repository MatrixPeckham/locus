<%-- 
    Document   : ViewCircle
    Created on : Nov 29, 2011, 8:21:42 AM
    Author     : William Peckham
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>
<l:GetCircle var="circle" circle="${param.circle}"/>
<div id="circleview">
    <div id="circlehead">
        <h1>${circle.name}</h1>
    </div>
    <l:LoopPosts circle="${circle.id}" >
        <div class="postview round-corner">
            <div class="postinfo">
                <div class="postauthor">
                    ${curPost.name}
                </div>
                <div class="postlikes">
                    This Post has ${curPost.likes} likes.
                    <c:choose>
                        <c:when test="${userInfo.userid!=-1 && !l:doesLikePost(userInfo,curPost.id)}">
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
        </div>
        <div class="postcontent">
            ${curPost.content}
        </div>
        <l:LoopComments post="${curPost.id}">
            <div class="commentview round-corner">
                <div class="commentinfo">
                    <div class="commentauthor">
                        ${curComment.authorName}
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
                </div>
                <div class="commentcontent">
                    ${curComment.content}
                </div>
                <div class="clearmarker" ></div>
            </div>
        </l:LoopComments>
        <div class="clearmarker"></div>
    </div>
</l:LoopPosts>
</div>