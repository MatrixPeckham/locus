<%-- 
    Document   : Ad
    Created on : Dec 3, 2011, 8:17:05 PM
    Author     : Comedy Option
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>
<l:GetAdForUser />
<div id="curadvert" class="round-corner" onclick="changePage('JSPChunks/BuyItem.jsp?ad=${curAd.id}')">
    <h3>Advertisement</h3>
    <br />${curAd.item}
    <br />Only ${curAd.available} Left
    <br />By ${curAd.company}
    <br />$${curAd.unitPrice}
    <br />From Category: ${curAd.cat}
    <br />${curAd.adContent}
</div>
<div id="bestsellers" class="round-corner">
    <h3>Best Selling Items</h3>
    <l:LoopBestSellers>
        ${curInd}) 
        <a href="javascript:void(0);" onclick="changePage('JSPChunks/BuyItem.jsp?ad=${curBest.b.id}')">
            ${curBest.b.item} 
        </a>
        with ${curBest.numsold} sold.
        <br />
    </l:LoopBestSellers>
</div>
