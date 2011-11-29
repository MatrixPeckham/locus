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