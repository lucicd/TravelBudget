<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="${(action == 'delete') 
                            ? 'Do you really want to delete the budget plan?' 
                            : 'Budget Plan Details'}"/>
<t:template title="${title}">
    <jsp:body>
        <h1>${title}</h1>
        <dl>
            <dt>Travel Destination</dt>
            <dd>
                <button class="btn btn-outline-info" id="weatherBtn" data-toggle="modal" data-target="#myModal">
                    ${formData.getTravelDestination()}
                </button>
            </dd>
            <dt>Travel Date</dt>
            <dd>${formData.getTravelDate()}</dd>
            <dt>Available Budget</dt>
            <dd>
                ${formData.getFormattedAvailableBudget()}
                ${formData.getCurrencyName()}
            </dd>
            <dt>Comments</dt>
            <dd>${formData.getComments()}</dd>
        </dl>
        <form method="post"
              action="${(action == 'delete') 
              ? './budget-plans?action=delete' 
              : './budget-plans?action=edit'}"
        >
            <input type="hidden" name="id" value="${formData.getId()}">
                           
            <c:if test="${action == 'delete'}">
                <button type="submit" class="btn btn-danger">Delete</button>
            </c:if>
            <c:if test="${action != 'delete'}">
                <button type="submit" class="btn btn-primary">Edit</button>
            </c:if>
            <a class="btn btn-primary" href="./budget-plans">Back to list</a>
        </form>
        
        <div class="modal fade" id="myModal">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">

                    <div class="modal-header">
                        <h4 class="modal-title" id="modalTitle"></h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>

                    <div class="modal-body">
                        <ul id="weatherList" class="row"></ul>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
            
        <script>
            window.onload = function()
            {
                function makeItem(description, value)
                {
                    var list = '<dt class="col-sm-6">';
                    list += description; 
                    list += '</dt>';
                    list += '<dd class="col-sm-6">';
                    list += value; 
                    list += '</dd>';
                    return list;
                }
                
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
                                    error("No weather data for this city.");
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
                
                var myBtn = document.getElementById('weatherBtn');
                myBtn.onclick = function()
                {
                    var url = 'weather?city=${formData.getTravelDestination()}';
                    
                    getJSON(url,
                        function(data) 
                        {
                            console.log(data);
                            var myElm = document.getElementById('modalTitle');
                            myElm.innerHTML = 'Weather in ${formData.getTravelDestination()}';
                            var list = '';
                            if (data.clouds.all) {
                                makeItem('Cloudness:', data.clouds.all + '%');
                            }
                            list += makeItem('Humidity:', data.main.humidity + '%');
                            list += makeItem('Pressure:', data.main.pressure + 'hPa');
                            list += makeItem('Temperature:', data.main.temp.toFixed(0) + '&deg;C');
                            list += makeItem('Max Temperature:', data.main.temp_max.toFixed(0) + '&deg;C');
                            list += makeItem('Min Temperature:', data.main.temp_min.toFixed(0) + '&deg;C');
                            list += makeItem('Wind Speed:', data.wind.speed.toFixed(1) + 'm/s');
                            if (data.wind.deg) {
                                list += makeItem('Wind Direction:', data.wind.deg.toFixed(0) + '&deg;');
                            }
                            //for (var i = 0; i < data.weather.length; i++)
                            //{
                            //    
                            //}
                            myElm = document.getElementById('weatherList');
                            myElm.innerHTML = list;
                        },
                        function(error)
                        {
                            var myElm = document.getElementById('modalTitle');
                            myElm.innerHTML = 'Weather for ${formData.getTravelDestination()}';
                            var list = makeItem('Error: ', error);
                            myElm = document.getElementById('weatherList');
                            myElm.innerHTML = list;
                        });
                };
            };
        </script>
    </jsp:body>
</t:template>