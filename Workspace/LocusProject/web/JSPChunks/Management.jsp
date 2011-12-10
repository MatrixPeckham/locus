<%-- 
    Document   : Management
    Created on : Dec 3, 2011, 11:45:27 AM
    Author     : William Peckham
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>
<div id="manamgerpage">
    <c:choose>
        <c:when test="${sessionScope.userInfo.type==2}">
            <div id="employees" class="round-corner floatleft">
                <h2>Your Employees</h2>
                <l:LoopEmployees manager="${sessionScope.userInfo.userid}">
                    <div>
                        <a href="javascript:void(0);" onclick="changePage('JSPChunks/AddEditEmployee.jsp?type=edit&empl=${curEmployee.usr.userid}')">${curEmployee.usr.fname} ${curEmployee.usr.lname} earns ${curEmployee.hourly}/h has generated $${curEmployee.revenue*.1}</a>
                        <a href="javascript:void(0);" onclick="fireEmployee('${curEmployee.usr.userid}')">(fire)</a>
                    </div>
                </l:LoopEmployees>
                <a href="javascript:void(0);" onclick="changePage('JSPChunks/AddEditEmployee.jsp?type=add')">Add Employee</a>
            </div>
            <div class="clearmarker"></div>
            <div id="sales" class ="round-corner floatleft">
                <h2>Monthly Sales</h2>
                <form>
                    <l:DateSelector />
                </form>
                <div id="chosensales">
                    <%@include file="/JSPChunks/SalesReport.jsp" %>
                </div>
            </div>
            <div class="clearmarker"></div>
            <div id="allads" class ="round-corner floatleft">
                <h2>All Ads:</h2>
                <l:LoopAllAds>
                    <div>${curAd.item} by ${curAd.company}</div>
                </l:LoopAllAds>
            </div>
            <div class="clearmarker"></div>
            <div id="salesbyitem" class="round-corner floatleft">
                <h2>Sales By Item</h2>
                <form>
                    <l:ItemSelector />
                </form>
                <div id="chosenitemsales">
                    <%@include file="/JSPChunks/SalesByItem.jsp" %>
                </div>
            </div>
            <div class="clearmarker"></div>
            <div id="salesbyname" class="round-corner floatleft">
                <h2>Sales By User</h2>
                <form>
                    <l:NameSelector />
                </form>
                <div id="chosennamesales">
                    <%@include file="/JSPChunks/SalesByName.jsp" %>
                </div>
            </div>
            <div class="clearmarker"></div>
            <div id="companyads" class="round-corner floatleft">
                <h2>Advertisements By Company</h2>
                <form>
                    <l:SelectCompany />
                </form>
                <div id="chosencompads">
                    <%@include file="/JSPChunks/CompanyAds.jsp" %>
                </div>
            </div>
            <div class="clearmarker"></div>
            <div id="highspenders" class="round-corner floatleft">
                <h2>Highest Spending users</h2>
                <l:LoopHighSpenders>
                    ${curIndex}. ${curUser.username} spent $${curUser.totalSpent} <br />
                </l:LoopHighSpenders>
            </div>
        </c:when>
        <c:otherwise>
            You Need to be a manager to view this page
        </c:otherwise>
    </c:choose>
</div>