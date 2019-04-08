<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<t:template title="Budget Plan Items">
    <jsp:body>
        <h1>Budget Plan Items</h1>
        <p>
            <a class="btn btn-warning"
               href="./budget-plan-items?action=create&budgetPlanId=${budgetPlanId}">Create</a>
        </p>
        <div class="table-responsive">
            <table class="table table-bordered table-striped table-hover">
                <thead>
                    <tr>
                        <th class="text-left">Description</th>
                        <th class="text-center">Start Date</th>
                        <th class="text-center">Completion Date</th>
                        <th class="text-center">Category</th>
                        <th class="text-center">Status</th>
                        <th class="text-right">Cost</th>
                        <th class="text-center">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${listData}">
                        <tr>
                            <td class="text-left">
                                <a href="./budget-plan-items?action=show-details&id=${item[0]}">
                                    ${item[2]}
                                </a>
                            </td>
                            <td class="text-center">
                                <fmt:formatDate 
                                    value="${item[4]}" 
                                    type="date" 
                                    pattern="dd-MMM-yyyy"/>
                            </td>
                            <td class="text-center">
                                <fmt:formatDate 
                                    value="${item[5]}" 
                                    type="date" 
                                    pattern="dd-MMM-yyyy"/>
                            </td>
                            <td class="text-left">
                                ${item[14]}
                            </td>
                            <td class="text-center">
                                ${item[7]}
                            </td>
                            <td class="text-right">
                                <fmt:formatNumber 
                                    value="${item[10]}" 
                                    type="number" 
                                    pattern="#,##0.00"/>
                                ${item[12]}
                            </td>
                            <td class="text-center">
                                <div class="btn-group-sm">
                                    <a class="btn btn-primary" href="./budget-plan-items?action=edit&id=${item[0]}">Edit</a>
                                    <a class="btn btn-danger" href="./budget-plan-items?action=confirm-delete&id=${item[0]}">Delete</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <a class="btn btn-primary" href="budget-plans">Back to Budget Plans</a>
        </div>
    </jsp:body>
</t:template>
