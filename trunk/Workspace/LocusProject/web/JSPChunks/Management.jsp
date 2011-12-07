<%-- 
    Document   : Management
    Created on : Dec 3, 2011, 11:45:27 AM
    Author     : William Peckham
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>
<div id="manamgerpage">
    <div id="employees" class="round-corner floatleft">
        <h2>Your Employees</h2>
        <l:LoopEmployees manager="${sessionScope.userInfo.userid}">
            <div>
                <a href="javascript:void(0);" onclick="changePage('JSPChunks/AddEditEmployee.jsp?type=edit&empl=${curEmployee.usr.userid}')">${curEmployee.usr.fname} ${curEmployee.usr.lname} earns ${curEmployee.hourly}/h</a>
                <a href="javascript:void(0);" onclick="fireEmployee('${curEmployee.usr.userid}')">(fire)</a>
            </div>
        </l:LoopEmployees>
        <a href="javascript:void(0);" onclick="changePage('JSPChunks/AddEditEmployee.jsp?type=add')">Add Employee</a>
    </div>
    <div id="sales" class ="round-corner floatleft">
        <form>
            <l:DateSelector />
        </form>
        <div id="chosensales">
            <%@include file="/JSPChunks/SalesReport.jsp" %>
        </div>
    </div>
    <div class="clearmarker"></div>
    <div id="allads" class ="round-corner floatleft">
        <h3>All Ads:</h3>
        <l:LoopAllAds>
            <div>${curAd.item} by ${curAd.company}</div>
        </l:LoopAllAds>
    </div>
    <div id="salesbyitem" class="round-corner floatleft">
        <form>
            <l:ItemSelector />
        </form>
        <div id="chosenitemsales">
            <%@include file="/JSPChunks/SalesByItem.jsp" %>
        </div>
    </div>
    <div id="salesbyname" class="round-corner floatleft">
        <form>
            <l:NameSelector />
        </form>
        <div id="chosennamesales">
            <%@include file="/JSPChunks/SalesByName.jsp" %>
        </div>
    </div>
</div>