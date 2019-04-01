<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="${(action == 'delete') 
                            ? 'Do you really want to delete the budget plan?' 
                            : 'Budget Plan Details'}"/>
<t:template title="${title}">
    <jsp:body>
        <h1>${title}</h1>
        <dl>
            <dt>Travel Destination</dt>
            <dd>${formData.getTravelDestination()}</dd>
            <dt>Travel Date</dt>
            <dd>${formData.getTravelDate()}</dd>
            <dt>Available Budget</dt>
            <dd>
                ${formData.getFormattedAvailableBudget()}
                ${formData.getCurrencyName()}
            </dd>
            <dt>Comments</dt>
            <dd>${formData.getComments()}</dd>
        </dl>
        <form method="post"
              action="${(action == 'delete') 
              ? './budget-plans?action=delete' 
              : './budget-plans?action=edit'}"
        >
            <input type="hidden" name="id" value="${formData.getId()}">
                           
            <c:if test="${action == 'delete'}">
                <button type="submit" class="btn btn-danger">Delete</button>
            </c:if>
            <c:if test="${action != 'delete'}">
                <button type="submit" class="btn btn-primary">Edit</button>
            </c:if>
            <a class="btn btn-primary" href="./budget-plans">Back to list</a>
        </form>
    </jsp:body>
</t:template>