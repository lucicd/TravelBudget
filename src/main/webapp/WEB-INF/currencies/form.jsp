<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="${(empty formData.id) ? 'Create Currency' : 'Edit Currency'}"/>
<t:template title="${title}">
    <jsp:body>
        <h1>${title}</h1>
        <form method="post" action="./currencies?action=save">
            <input type="hidden" name="id" value="${formData.id}">
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="name">Currency</label>
                    <input type="text"
                           class="${(empty formErrors.get('name')) ? 'form-control' : 'form-control is-invalid'}"
                           id="name" 
                           name="name" 
                           value="${formData.name}">
                    <c:if test="${!empty formErrors.get('name')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('name')}
                        </div>
                    </c:if>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </jsp:body>
</t:template>