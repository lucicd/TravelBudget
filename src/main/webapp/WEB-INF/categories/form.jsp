<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="${(empty formData.id) ? 'Create Category' : 'Edit Category'}"/>
<t:template title="${title}">
    <jsp:body>
        <h1>${title}</h1>
        <form method="post" action="./categories?action=save">
            <input type="hidden" name="id" value="${formData.getId()}">
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="description">Category Description</label>
                    <input type="text"
                           class="${(empty formErrors.get('description')) ? 'form-control' : 'form-control is-invalid'}"
                           id="description" 
                           name="description" 
                           value="${formData.getDescription()}">
                    <c:if test="${!empty formErrors.get('description')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('description')}
                        </div>
                    </c:if>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
            <a class="btn btn-primary" href="./categories">Back to list</a>
        </form>
    </jsp:body>
</t:template>