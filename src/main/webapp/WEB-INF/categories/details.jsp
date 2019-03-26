<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="${(action == 'delete') ? 'Do you really want to delete the category?' : 'Currency Details'}"/>
<t:template title="${title}">
    <jsp:body>
        <h1>${title}</h1>
        <form method="post" action="${(action == 'delete') ? './categories?action=delete' : './categories?action=edit'}">
            <input type="hidden" name="id" value="${formData.getId()}">
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="name">Category Description</label>
                    <input type="text"
                           class="form-control"
                           id="description" 
                           name="description" 
                           value="${formData.getDescription()}"
                           readonly>
                </div>
            </div>
            <c:if test="${action == 'delete'}">
                <button type="submit" class="btn btn-danger">Delete</button>
            </c:if>
            <c:if test="${action != 'delete'}">
                <button type="submit" class="btn btn-primary">Edit</button>
            </c:if>
            <a class="btn btn-primary" href="./categories">Back to list</a>
        </form>
    </jsp:body>
</t:template>