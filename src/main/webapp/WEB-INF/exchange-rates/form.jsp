<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="${(empty formData.id) ? 'Create Exchange Rate' : 'Edit Exchange Rate'}"/>
<t:template title="${title}">
    <jsp:body>
        <h1>${title}</h1>
        <form method="post" id="myForm">
            <input type="hidden" name="id" value="${formData.getId()}">
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="name">Currency</label>
                    <select id = "currencyId"
                            name = "currencyId"
                            class="${(empty formErrors.get('currencyId')) ? 'form-control' : 'form-control is-invalid'}">
                        <c:forEach var="item" items="${currencies}">
                            <option value="${item.getId()}"
                                    ${(item.getId() == formData.getCurrencyId()) ? "selected" : ""}>
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
                    <label for="textValue">Current Exchange Rate</label>
                    <div class="input-group">
                        <input type="text"
                               class="${(empty formErrors.get('currentExchangeRate')) ? 'form-control' : 'form-control is-invalid'}"
                               id="currentExchangeRate" 
                               name="currentExchangeRate" 
                               value="${formData.getCurrentExchangeRate()}">
                        <span class="input-group-btn">
                            <button id="getRateBtn" class="btn btn-warning">Get Rate</button>
                        </span>
                    </div>
                    <c:if test="${!empty formErrors.get('currentExchangeRate')}">
                        <div class="invalid-feedback">
                            ${formErrors.get('currentExchangeRate')}
                        </div>
                    </c:if>
                </div>
            </div>
                    
            <button id="saveBtn" class="btn btn-primary">Submit</button>
            <a class="btn btn-primary" href="./exchange-rates">Back to list</a>
        </form>
        <script>
            window.onload = function() {
                function save()
                {
                    var myForm = document.getElementById('myForm');
                    myForm.action = './exchange-rates?action=save';
                    myForm.submit();
                }
                
                function getRate()
                {
                    var myForm = document.getElementById('myForm');
                    myForm.action = './exchange-rates?action=rate-api';
                    myForm.submit();
                }
                
                var myBtn = document.getElementById('saveBtn');
                myBtn.onclick = save;
                myBtn = document.getElementById('getRateBtn');
                myBtn.onclick = getRate;
            };
        </script>
    </jsp:body>
</t:template>