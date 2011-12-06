<%-- 
    Document   : EmployeePage
    Created on : Dec 6, 2011, 8:36:18 AM
    Author     : Owner
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>
<div id="employeepage">
    <div id="advertisements" class="round-corner">
        <l:LoopEmployeeAds empl="${sessionScope.userInfo.userid}">
            <div id="ad${curAd.id}}">
                ${curAd.item}
                ${curAd.company}
                ${curAd.cat}
                ${curAd.unitPrice}
                ${curAd.date}
                ${curAd.adContent}
                ${curAd.available}
                (<a href="javascript:void(0);" onclick="deleteAd('${curAd.id}')">delete</a>)
            </div>
        </l:LoopEmployeeAds>
    </div>
    <div id="newad">
        <form id="newadform" action="javascript:void(0);" onsubmit="">
            Category:<input type="text" id="newcat" />
            <br />Company:<input type="text" id="newcomp" />
            <br />Item:<input type="text" id="newitem" />
            <br />Unit Price:<input type="text" id="newprice" />
            <br />Number Available:<input type="text" id="newnum" />
            <br />Content:<textarea id="newcont"></textarea>
            <br /><input type="submit" id="addad" onclick="addAd()" value="Add Ad" />
        </form>
    </div>
</div>