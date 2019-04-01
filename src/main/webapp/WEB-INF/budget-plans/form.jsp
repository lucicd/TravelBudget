<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" 
       value="${(empty formData.id) 
                ? 'Create Budget Plan' 
                : 'Edit Budget Plan'}"
/>
<t:template title="${title}">
    <jsp:body>
        <h1>${title}</h1>
        <form method="post" action="./budget-plans?action=save">
            <input type="hidden" name="id" value="${formData.getId()}">
            <input type="hidden" name="currencyId" value="${formData.getCurrencyId()}">
            
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="travelDestination">Travel Destination</label>
                    <input type="text"
                           class="${(empty formErrors.get('travelDestination')) 
                                    ? 'form-control' 
                                    : 'form-control is-invalid'}"
                           id="travelDestination" 
                           name="travelDestination" 
                           value="${formData.getTravelDestination()}">
                    <c:if test="${!empty formErrors.get('travelDestination')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('travelDestination')}
                        </div>
                    </c:if>
                </div>
            </div>
                    
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="travelDate">Travel Date</label>
                    <input type="date"
                           class="${(empty formErrors.get('travelDate')) 
                                    ? 'form-control' 
                                    : 'form-control is-invalid'}"
                           id="travelDate" 
                           name="travelDate" 
                           value="${formData.getTravelDate()}">
                    <c:if test="${!empty formErrors.get('travelDate')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('travelDate')}
                        </div>
                    </c:if>
                </div>
            </div>
                    
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="availableBudget">Available Budget</label>
                    <div class="input-group">
                        <input type="number"
                               class="${(empty formErrors.get('availableBudget')) 
                                        ? 'form-control' 
                                        : 'form-control is-invalid'}"
                               id="availableBudget" 
                               name="availableBudget"
                               min="0"
                               step="0.01"
                               value="${formData.getAvailableBudget()}">
                        <div class="input-group-append">
                            <span class="input-group-text">
                                ${formData.getCurrencyName()}
                            </span>
                        </div>
                        <c:if test="${!empty formErrors.get('availableBudget')}">
                            <div class="invalid-feedback">
                                ${formErrors.get('availableBudget')}
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
                    
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="comments">Comments</label>
                    <textarea type="text"
                           class="${(empty formErrors.get('comments')) 
                                    ? 'form-control' 
                                    : 'form-control is-invalid'}"
                           id="comments"
                           rows="5"
                           name="comments" 
                    >${formData.getComments()}</textarea>
                    <c:if test="${!empty formErrors.get('comments')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('comments')}
                        </div>
                    </c:if>
                </div>
            </div>
            
            <button type="submit" class="btn btn-primary">Submit</button>
            <a class="btn btn-primary" href="budget-plans">Back to list</a>
        </form>
    </jsp:body>
</t:template>