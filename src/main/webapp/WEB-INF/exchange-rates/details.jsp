<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="${(action == 'delete') ? 'Do you really want to delete the exchange rate?' : 'Exchange Rate Details'}"/>
<t:template title="${title}">
    <jsp:body>
        <h1>${title}</h1>
        <dl>
            <dt>Currency</dt>
            <dd>${formData.getCurrencyName()}</dd>
            <dt>Current Exchange Rate</dt>
            <dd>${formData.getCurrentExchangeRate()}</dd>
        </dl>
        <form method="post" action="${(action == 'delete') ? './exchange-rates?action=delete' : './exchange-rates?action=edit'}">
            <input type="hidden" name="id" value="${formData.getId()}">
                           
            <c:if test="${action == 'delete'}">
                <button type="submit" class="btn btn-danger">Delete</button>
            </c:if>
            <c:if test="${action != 'delete'}">
                <button type="submit" class="btn btn-primary">Edit</button>
            </c:if>
            <a class="btn btn-primary" href="./exchange-rates">Back to list</a>
        </form>
    </jsp:body>
</t:template>