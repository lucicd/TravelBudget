<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="${(action == 'delete') ? 'Do you really want to delete the currency?' : 'Currency Details'}"/>
<t:template title="${title}">
    <jsp:body>
        <h1>${title}</h1>
        <form method="post" action="${(action == 'delete') ? './currencies?action=delete' : './currencies?action=edit'}">
            <input type="hidden" name="id" value="${formData.id}">
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="name">Currency</label>
                    <input type="text"
                           class="form-control"
                           id="name" 
                           name="name" 
                           value="${formData.name}"
                           readonly>
                </div>
            </div>
            <c:if test="${action == 'delete'}">
                <button type="submit" class="btn btn-danger">Delete</button>
            </c:if>
            <c:if test="${action != 'delete'}">
                <button type="submit" class="btn btn-primary">Edit</button>
            </c:if>
            <a class="btn btn-primary" href="./currencies">Back to list</a>
        </form>
    </jsp:body>
</t:template>