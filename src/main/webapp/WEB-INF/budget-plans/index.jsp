<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<t:template title="Budget Plans">
    <jsp:body>
        <h1>Budget Plans</h1>
        <p>
            <a class="btn btn-warning" href="./budget-plans?action=create">Create</a>
        </p>
        <div class="table-responsive">
            <table class="table table-bordered table-striped table-hover">
                <thead>
                    <tr>
                        <th class="text-center">Travel Destination</th>
                        <th class="text-center">Travel Date</th>
                        <th class="text-right">Available Budget</th>
                        <th class="text-center">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${listData}">
                        <tr>
                            <td class="text-center">
                                <a href="./budget-plans?action=show-details&id=${item[0]}">
                                    ${item[2]}
                                </a>
                            </td>
                            <td class="text-center">
                                <fmt:formatDate 
                                    value="${item[1]}" 
                                    type="date" 
                                    pattern="dd-MMM-yyyy"/>
                            </td>
                            <td class="text-right">
                                <fmt:formatNumber 
                                    value="${item[3]}" 
                                    type="number" 
                                    pattern="#,##0.00"/>
                                ${item[4]}
                            </td>
                            <td class="text-center">
                                <div class="btn-group-sm">
                                    <a class="btn btn-primary" href="./budget-plans?action=edit&id=${item[0]}">Edit</a>
                                    <a class="btn btn-danger" href="./budget-plans?action=confirm-delete&id=${item[0]}">Delete</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:body>
</t:template>
