<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="${(action == 'delete') ? 'Do you really want to delete the setting?' : 'Setting Details'}"/>
<t:template title="${title}">
    <jsp:body>
        <h1>${title}</h1>
        <form method="post" action="${(action == 'delete') ? './settings?action=delete' : './settings?action=edit'}">
            <input type="hidden" name="id" value="${formData.getId()}">
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="name">Setting Name</label>
                    <input type="text"
                           class="form-control"
                           id="name" 
                           name="name" 
                           value="${formData.getName()}"
                           readonly>
                </div>
            </div>
                           
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="textValue">Text Value</label>
                    <input type="text"
                           class="form-control"
                           id="textValue" 
                           name="textValue" 
                           value="${formData.getTextValue()}"
                           readonly>
                </div>
            </div>
                           
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="numberValue">Number Value</label>
                    <input type="text"
                           class="form-control"
                           id="numberValue" 
                           name="numberValue" 
                           value="${formData.getNumberValue()}"
                           readonly>
                </div>
            </div>
                           
            <c:if test="${action == 'delete'}">
                <button type="submit" class="btn btn-danger">Delete</button>
            </c:if>
            <c:if test="${action != 'delete'}">
                <button type="submit" class="btn btn-primary">Edit</button>
            </c:if>
            <a class="btn btn-primary" href="./settings">Back to list</a>
        </form>
    </jsp:body>
</t:template>