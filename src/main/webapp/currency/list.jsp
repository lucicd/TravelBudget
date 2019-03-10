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

<jsp:include page="../shared/template.jsp">
    <jsp:param name="content" value="../currency/list_content"/>
    <jsp:param name="title" value="Currencies"/>
</jsp:include>
