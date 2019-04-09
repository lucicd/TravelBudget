<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="title" value="${(action == 'delete') 
                            ? 'Do you really want to delete the budget plan item?' 
                            : 'Budget Plan Item Details'}"/>
<t:template title="${title}">
    <jsp:body>
        <h1>${title}</h1>
        <dl class="row">
            <dt class="col-sm-3">Item Description:</dt>
            <dd class="col-sm-9">${formData.getItemDescription()}</dd>
            
            <dt class="col-sm-3">Category:</dt>
            <dd class="col-sm-9">${formData.getCategory()}</dd>
            
            <dt class="col-sm-3">Start Date:</dt>
            <dd class="col-sm-9">${formData.getStartDate()}</dd>
            
            <dt class="col-sm-3">Completion Date:</dt>
            <dd class="col-sm-9">${formData.getCompletionDate()}</dd>
            
            <dt class="col-sm-3">URL:</dt>
            <dd class="col-sm-9"><a target="_blank" href="${formData.getUrl()}">${formData.getUrl()}</a></dd>
            
            <dt class="col-sm-3">Status:</dt>
            <dd class="col-sm-9">${formData.getStatus()}</dd>
            
            <dt class="col-sm-3">Cost in Currency:</dt>
            <dd class="col-sm-9">
                 <fmt:formatNumber 
                    value="${formData.getCostInCurrency()}" 
                    type="number" 
                    pattern="#,##0.00"/>
                ${formData.getCurrency()}
            </dd>
            
            <dt class="col-sm-3">Exchange Rate:</dt>
            <dd class="col-sm-9">
                <fmt:formatNumber 
                    value="${formData.getExchangeRate()}" 
                    type="number" 
                    pattern="#,##0.000000"/>
                </dd>
            
            <dt class="col-sm-3">Cost:</dt>
            <dd class="col-sm-9">
                <fmt:formatNumber 
                    value="${formData.getCost()}" 
                    type="number" 
                    pattern="#,##0.00"/>
                ${currency}
            </dd>
            
            <dt class="col-sm-3">Comments:</dt>
            <dd class="col-sm-9"><pre>${formData.getComments()}</pre></dd>
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