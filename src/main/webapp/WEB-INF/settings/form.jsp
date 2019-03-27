<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="${(empty formData.id) ? 'Create Setting' : 'Edit Setting'}"/>
<t:template title="${title}">
    <jsp:body>
        <h1>${title}</h1>
        <form method="post" action="./settings?action=save">
            <input type="hidden" name="id" value="${formData.getId()}">
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="name">Setting Name</label>
                    <input type="text"
                           class="${(empty formErrors.get('name')) ? 'form-control' : 'form-control is-invalid'}"
                           id="name" 
                           name="name" 
                           value="${formData.getName()}">
                    <c:if test="${!empty formErrors.get('name')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('name')}
                        </div>
                    </c:if>
                </div>
            </div>
                    
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="textValue">Text Value</label>
                    <input type="text"
                           class="${(empty formErrors.get('textValue')) ? 'form-control' : 'form-control is-invalid'}"
                           id="textValue" 
                           name="textValue" 
                           value="${formData.getTextValue()}">
                    <c:if test="${!empty formErrors.get('textValue')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('textValue')}
                        </div>
                    </c:if>
                </div>
            </div>
                    
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="numberValue">Number Value</label>
                    <input type="text"
                           class="${(empty formErrors.get('numberValue')) ? 'form-control' : 'form-control is-invalid'}"
                           id="numberValue" 
                           name="numberValue" 
                           value="${formData.getNumberValue()}">
                    <c:if test="${!empty formErrors.get('numberValue')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('numberValue')}
                        </div>
                    </c:if>
                </div>
            </div>
                    
            <button type="submit" class="btn btn-primary">Submit</button>
            <a class="btn btn-primary" href="./settings">Back to list</a>
        </form>
    </jsp:body>
</t:template>