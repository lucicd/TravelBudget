<%@page import="java.util.List"%>
<%@page import="lucicd.travelbudget.model.Currency"%>
<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <% 
            List<Currency> data = (List<Currency>)request.getAttribute("data");
            for (Currency myRec : data) { 
        %>
        <tr>
            <td><%= myRec.getId() %></td>
            <td><%= myRec.getName() %></td>
            <td>
                <a href="#">Edit</a>
                <a href="#">Delete</a>
            </td>
        </tr>
        <% } %>
    </tbody>
</table>