<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" 
       value="${(empty formData.id) 
                ? 'Create Budget Plan Item' 
                : 'Edit Budget Plan Item'}"
/>
<t:template title="${title}">
    <jsp:body>
        <h1>${title}</h1>
        <form method="post" action="./budget-plan-items?action=save">
            <input type="hidden" name="id" value="${formData.getId()}">
            <input type="hidden" name="budgetPlanId" 
                   value="${formData.getBudgetPlanId()}">
            <c:if test="${!empty formErrors.get('budgetPlanId')}">
                <div class="invalid-feedback">
                    ${formErrors.get('budgetPlanId')}
                </div>
            </c:if>
            
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="itemDescription">Item Description</label>
                    <input type="text"
                           class="${(empty formErrors.get('itemDescription')) 
                                    ? 'form-control' 
                                    : 'form-control is-invalid'}"
                           id="itemDescription" 
                           name="itemDescription" 
                           value="${formData.getItemDescription()}">
                    <c:if test="${!empty formErrors.get('itemDescription')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('itemDescription')}
                        </div>
                    </c:if>
                </div>
            </div>
                    
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="categoryId">Category</label>
                    <select id = "categoryId"
                            name = "categoryId"
                            class="${(empty formErrors.get('categoryId')) 
                                     ? 'form-control' 
                                     : 'form-control is-invalid'}"
                    >
                        <option value="">- Select category - </option>
                        <c:forEach var="item" items="${formData.getCategories()}">
                            <option value="${item.getId()}"
                                    ${(item.getId() == formData.getCategoryId()) 
                                      ? "selected" : 
                                      ""}
                                    label="${item.getDescription()}"
                            >
                                ${item.getDescription()}
                            </option>
                        </c:forEach>
                    </select>
                    <c:if test="${!empty formErrors.get('categoryId')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('categoryId')}
                        </div>
                    </c:if>
                </div>
            </div>
                    
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="status">Status</label>
                    <select id = "status"
                            name = "status"
                            class="${(empty formErrors.get('status')) 
                                     ? 'form-control' 
                                     : 'form-control is-invalid'}"
                    >
                        <option value="">- Select status - </option>
                        <c:forEach var="item" items="${formData.getStatuses()}">
                            <option value="${item}"
                                    ${(item == formData.getStatus()) 
                                      ? "selected" : 
                                      ""}
                                    label="${item}"
                            >
                                ${item}
                            </option>
                        </c:forEach>
                    </select>
                    <c:if test="${!empty formErrors.get('status')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('status')}
                        </div>
                    </c:if>
                </div>
            </div>
                    
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="url">URL</label>
                    <input type="text"
                           class="${(empty formErrors.get('url')) 
                                    ? 'form-control' 
                                    : 'form-control is-invalid'}"
                           id="url" 
                           name="url" 
                           value="${formData.getUrl()}">
                    <c:if test="${!empty formErrors.get('url')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('url')}
                        </div>
                    </c:if>
                </div>
            </div>
                    
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="startDate">Start Date</label>
                    <input type="date"
                           class="${(empty formErrors.get('startDate')) 
                                    ? 'form-control' 
                                    : 'form-control is-invalid'}"
                           id="startDate" 
                           name="startDate" 
                           value="${formData.getStartDate()}">
                    <c:if test="${!empty formErrors.get('startDate')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('startDate')}
                        </div>
                    </c:if>
                </div>
            </div>
                    
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="startDate">Completion Date</label>
                    <input type="date"
                           class="${(empty formErrors.get('completionDate')) 
                                    ? 'form-control' 
                                    : 'form-control is-invalid'}"
                           id="completionDate" 
                           name="completionDate" 
                           value="${formData.getCompletionDate()}">
                    <c:if test="${!empty formErrors.get('completionDate')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('completionDate')}
                        </div>
                    </c:if>
                </div>
            </div>
                    
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="costInCurrency">Cost in Currency</label>
                    <input type="number"
                           class="${(empty formErrors.get('costInCurrency')) 
                                    ? 'form-control' 
                                    : 'form-control is-invalid'}"
                           id="costInCurrency" 
                           name="costInCurrency"
                           min="0"
                           step="0.01"
                           value="${formData.getCostInCurrency()}">
                    <c:if test="${!empty formErrors.get('costInCurrency')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('costInCurrency')}
                        </div>
                    </c:if>
                </div>
            </div>
                    
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="currencyId">Currency</label>
                    <select id = "currencyId"
                            name = "currencyId"
                            class="${(empty formErrors.get('currencyId')) 
                                     ? 'form-control' 
                                     : 'form-control is-invalid'}"
                    >
                        <option value="">- Select currency - </option>
                        <c:forEach var="item" items="${formData.getCurrencies()}">
                            <option value="${item.getId()}"
                                    ${(item.getId() == formData.getCurrencyId()) 
                                      ? "selected" : 
                                      ""}
                                    label="${item.getName()}"
                            >
                                ${item.getName()}
                            </option>
                        </c:forEach>
                    </select>
                    <c:if test="${!empty formErrors.get('currencyId')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('currencyId')}
                        </div>
                    </c:if>
                </div>
            </div>
                    
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="exchangeRate">Exchange Rate</label>
                    <input type="number"
                           class="${(empty formErrors.get('exchangeRate')) 
                                    ? 'form-control' 
                                    : 'form-control is-invalid'}"
                           id="exchangeRate" 
                           name="exchangeRate"
                           min="0"
                           step="0.000001"
                           value="${formData.getExchangeRate()}">
                    <c:if test="${!empty formErrors.get('exchangeRate')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('exchangeRate')}
                        </div>
                    </c:if>
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
            <a class="btn btn-primary" href="budget-plan-items?budgetPlanId=${formData.getBudgetPlanId()}">Back to list</a>
        </form>
        <script>
            window.onload = function()
            {
                function getJSON(path, success, error)
                {
                    var xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function()
                    {
                        if (xhr.readyState === XMLHttpRequest.DONE) {
                            if (xhr.status === 200) {
                            if (success)
                                try {
                                    var data = JSON.parse(xhr.responseText);
                                    success(data);
                                } catch (err) {
                                    error("No exchange rate for this currency.");
                                }
                            } else {
                                if (error)
                                    error(xhr);
                            }
                        }
                    }
                    xhr.open("GET", path, true);
                    xhr.send();
                }
                
                var currencyIdSelect = document.getElementById('currencyId');
                currencyIdSelect.onchange = function()
                {
                    var currencyId = currencyIdSelect.options[
                        currencyIdSelect.selectedIndex
                    ].value;
                    
                    if (!currencyId) return;
                    
                    var url = 'exchange-rates?action=getRate&currencyId='
                            + currencyId;
                    
                    getJSON(url,
                        function(data) 
                        {
                            var exchangeRateText = 
                                    document.getElementById('exchangeRate');
                            exchangeRateText.value = data.currentExchangeRate;
                        },
                        function(error)
                        {
                            alert(error.toString());
                        });
                };
            };
        </script>
    </jsp:body>
</t:template>