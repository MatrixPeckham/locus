<%-- 
    Document   : Home
    Created on : Nov 23, 2011, 2:20:45 PM
    Author     : William Peckham
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>

<h1>Welcome to Locus.</h1>
<h2>Currently Active Circles</h2>
<l:LoopCircles>
    <div class="round-corner" style="margin:20px">
        <span><h3>${curCircle.name}</h3> created by <!--TODO make link--> <a href="javascript:void(0);">${curCircle.ownerName}</a></span>
    </div>
</l:LoopCircles>
