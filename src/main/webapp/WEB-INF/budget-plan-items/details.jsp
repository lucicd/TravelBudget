<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="${(action == 'delete') 
                            ? 'Do you really want to delete the budget plan item?' 
                            : 'Budget Plan Item Details'}"/>
<t:template title="${title}">
    <jsp:body>
        <h1>${title}</h1>
        <dl>
            <dt>Item Description</dt>
            <dd>${formData.getItemDescription()}</dd>
            <dt>Start Date</dt>
            <dd>${formData.getStartDate()}</dd>
            <dt>Cost in Currency</dt>
            <dd>
                ${formData.getCostInCurrency()}
                ${formData.getCurrency()}
            </dd>
            <dt>Comments</dt>
            <dd>${formData.getComments()}</dd>
        </dl>
        <form method="post"
              action="${(action == 'delete') 
              ? './budget-plan-items?action=delete' 
              : './budget-plan-items?action=edit'}"
        >
            <input type="hidden" name="id" value="${formData.getId()}">
                           
            <c:if test="${action == 'delete'}">
                <button type="submit" class="btn btn-danger">Delete</button>
            </c:if>
            <c:if test="${action != 'delete'}">
                <button type="submit" class="btn btn-primary">Edit</button>
            </c:if>
            <a class="btn btn-primary" href="./budget-plan-items?budgetPlanId=${formData.getBudgetPlanId()}">Back to list</a>
        </form>
    </jsp:body>
</t:template>