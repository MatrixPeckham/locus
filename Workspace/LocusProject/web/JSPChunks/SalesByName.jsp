<%-- 
    Document   : SalesReport
    Created on : Dec 5, 2011, 10:49:29 AM
    Author     : Owner
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="l" uri="com.locus305.tags" %>

<div id="namereport">
    <table>
        <thead>
            <tr>
                <th>
                    Item
                </th>
                <th>
                    Company
                </th>
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
            <l:LoopNameTransactions name="${param.name}">
                <tr>
                    <td>
                        ${curTrans.b.item}
                    </td>
                    <td>
                        ${curTrans.b.company}
                    </td>
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
            </l:LoopNameTransactions>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>Total:</td>
                <td>${nameTotal}</td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>Total Revenue:</td>
                <td>${(nameTotal*.1)}</td>
            </tr>
        </tbody>

    </table>    
</div>