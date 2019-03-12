<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="lucicd.travelbudget.model.Currency"%>
<%
    List<Currency> data = new ArrayList<>();
    data.add(new Currency(1, "AED"));
    data.add(new Currency(2, "USD"));
    data.add(new Currency(3, "EUR"));
    data.add(new Currency(4, "JPY"));
    data.add(new Currency(5, "PHP"));
    data.add(new Currency(6, "GBP"));
    data.add(new Currency(7, "HRK"));
    request.setAttribute("data", data);
%>
<t:template title="Currencies">
    <jsp:body>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${requestScope.data}">
                    <tr>
                        <td>${item.getId()}</td>
                        <td>${item.getName()}</td>
                        <td>
                            <a href="#">Edit</a>
                            <a href="#">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </jsp:body>
</t:template>
