<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<t:template title="Budget Plans">
    <jsp:body>
        <h1>Budget Plans</h1>
        <p>
            <a class="btn btn-warning" href="./budget-plans?action=create">Create</a>
        </p>
        <form action="./budget-plans" method="get">
            <div class="form-group">
                <span class="align-middle">
                    Destination: <input type="text" name="searchString" value="${currentFilter}" />
                    <input type="submit" value="Search" class="btn btn-sm btn-primary" />
                    <a class="btn btn-sm btn-primary" href="./budget-plans">
                        Back to Full List
                    </a>
                </span>
            </div>
        </form>
        <div class="table-responsive">
            <table class="table table-bordered table-striped table-hover">
                <thead>
                    <tr>
                        <th class="text-center">
                            <a href="./budget-plans?sortOrder=${destinationSortOrder}&currentFilter=${currentFilter}">
                            Travel Destination
                            </a>
                        </th>
                        <th class="text-center">
                            <a href="./budget-plans?sortOrder=${dateSortOrder}&currentFilter=${currentFilter}">
                                Travel Date
                            </a>
                        </th>
                        <th class="text-right">
                            <a href="./budget-plans?sortOrder=${availableBudgetSortOrder}&currentFilter=${currentFilter}">
                                Available Budget
                            </a>
                        </th>
                        <th class="text-center">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${listData.list}">
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
                                    <a class="btn btn-primary" href="./budget-plan-items?action=list&budgetPlanId=${item[0]}">Items</a>
                                    <a class="btn btn-primary" href="./budget-plans?action=edit&id=${item[0]}">Edit</a>
                                    <a class="btn btn-danger" href="./budget-plans?action=confirm-delete&id=${item[0]}">Delete</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <a class="btn btn-primary btn-sm ${(listData.hasPrevPage() ? "" : "disabled")}" 
            href="./budget-plans?pageNumber=${listData.getCurrentPage() - 1}&currentFilter=${currentFilter}&sortOrder=${currentSort}">
            Previous
        </a>
        <a class="btn btn-primary btn-sm ${(listData.hasNextPage() ? "" : "disabled")}" 
            href="./budget-plans?pageNumber=${listData.getCurrentPage() + 1}&currentFilter=${currentFilter}&sortOrder=${currentSort}">
            Next
        </a>
    </jsp:body>
</t:template>
