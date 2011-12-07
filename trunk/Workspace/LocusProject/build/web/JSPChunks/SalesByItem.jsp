<%-- 
    Document   : SalesByItem
    Created on : Dec 7, 2011, 12:24:44 PM
    Author     : Owner
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>

<div id="itemreport">
    <table>
        <thead>
            <tr>
                <th>
                    Unit Price
                </th>
                <th>
                    Date
                </th>
                <th>
                    Number Purchased
                </th>
                <th>
                    Total
                </th>
            </tr>
        </thead>
        <tbody>
            <l:LoopItemTransactions item="${param.item}">
                <tr>
                    <td>
                        ${curTrans.b.unitPrice}
                    </td>
                    <td>
                        ${curTrans.date}
                    </td>
                    <td>
                        ${curTrans.numUnits}
                    </td>
                    <td>
                        ${curTrans.total}
                    </td>
                </tr>
            </l:LoopItemTransactions>
            <tr>
                <td></td>
                <td></td>
                <td>Total:</td>
                <td>${itemTotal}</td>
            </tr>
        </tbody>

    </table>    
</div>
