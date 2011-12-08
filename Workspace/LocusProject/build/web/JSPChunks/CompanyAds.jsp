<%-- 
    Document   : CompanyAds
    Created on : Dec 7, 2011, 6:06:46 PM
    Author     : Owner
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>
<div id="selectedads">
    <l:LoopCompanyAds company="${param.company}">
        <div>${curAd.item}</div>
    </l:LoopCompanyAds>
</div>