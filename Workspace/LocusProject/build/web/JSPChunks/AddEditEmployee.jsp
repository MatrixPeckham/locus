<%-- 
    Document   : AddEditEmployee
    Created on : Dec 3, 2011, 5:34:32 PM
    Author     : Owner
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>
<l:GetEmployee empl="${param.empl}" var="em" />
<div id="addeditemployee">
    <form id="userinfo" action="javascript:void(0);">
        <div>
            <span><label>User Name: </label></span><span><input type="text" value="${em.usr.username}" id="usrname" ${ param.type!="add" ? "readonly=true":"" }/></span>
        </div>
        <c:if test="${ param.type == 'add' }">
            <div>
                <span><label>Pass: </label></span><span><input type="text" value="" id="pass"/></span>
            </div>
            <div>
                <span><label>Email: </label></span><span><input type="text" value="" id="email"/></span>
            </div>
        </c:if>
        <div>
            <span><label>First Name: </label></span><span><input type="text" id="fname" value="${em.usr.fname}"/></span>
        </div>
        <div>
            <span><label>Last Name: </label></span><span><input type="text" id="lname" value="${em.usr.lname}"/></span>
        </div>
        <div>
            <span><label>Address: </label></span><span><input type="text" id="addr" value="${em.usr.addr}"/></span>
        </div>
        <div>
            <span><label>City: </label></span><span><input type="text" id="city" value="${em.usr.city}"/></span>
        </div>
        <div>
            <span><label>State: </label></span><span><input type="text" id="state" value="${em.usr.state}"/></span>
        </div>
        <div>
            <span><label>Zip Code: </label></span><span><input type="text" id="zip" value="${em.usr.zip}"/></span>
        </div>
        <div>
            <span><label>Phone Number: </label></span><span><input type="text" id="phone" value="${em.usr.phone}"/></span>
        </div>
        <div>
            <span><label>Hourly Rate: </label></span><span><input type="text" id="hourly" value="${em.hourly}"/></span>
        </div>
        <input type="submit" onclick="${param.type=="add"?"createEmployee()":"editEmployee()"}" value="${param.type=="add"?"Hire":"Save"}" />
    </form>
</div>