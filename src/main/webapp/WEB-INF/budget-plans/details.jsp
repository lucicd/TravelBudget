<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="${(action == 'delete') 
                            ? 'Do you really want to delete the budget plan?' 
                            : 'Budget Plan Details'}"/>
<t:template title="${title}">
    <jsp:body>
        <h1>${title}</h1>
        <dl class="row">
            <dt class="col-sm-3">Travel Destination</dt>
            <dd class="col-sm-9">
                <button class="btn btn-outline-info" id="weatherBtn" data-toggle="modal" data-target="#myModal">
                    ${formData.getTravelDestination()}
                </button>
            </dd>
            <dt class="col-sm-3">Travel Date</dt>
            <dd class="col-sm-9">${formData.getTravelDate()}</dd>
            <dt class="col-sm-3">Available Budget</dt>
            <dd class="col-sm-9">
                ${formData.getFormattedAvailableBudget()}
                ${formData.getCurrencyName()}
            </dd>
            <dt class="col-sm-3">Allocated Budget</dt>
            <dd class="col-sm-9">
                ${formData.getFormattedAllocatedBudget()}
                ${formData.getCurrencyName()}
            </dd>
            <dt class="col-sm-3">Comments</dt>
            <dd class="col-sm-9"><pre>${formData.getComments()}</pre></dd>
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
                function makeItem(description, value, icon)
                {
                    var list = '<dt class="col-sm-6">';
                    list += description; 
                    list += '</dt>';
                    list += '<dd class="col-sm-6">';
                    list += value;
                    if (icon)
                    {
                        list += '<span id="icon"><img id="wicon" src="http://openweathermap.org/img/w/'
                            + icon + '.png"'
                            + ' alt="Weather icon"></span>';
                    }
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
                
                function getWindDirection(degrees)
                {
                    if (degrees <= 22.5) {
                        return "N";
                    } else if (degrees <= 67.5) {
                        return "NE";
                    } else if (degrees <= 112.5) {
                        return "E";
                    } else if (degrees <= 157.5) {
                        return "SE";
                    } else if (degrees <= 202.5) {
                        return "S";
                    } else if (degrees <= 247.5) {
                        return "SW";
                    } else if (degrees <= 292.5) {
                        return "W";
                    } else if (degrees <= 337.5) {
                        return "NW";
                    } else {
                        return "N";
                    }
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
                            list += makeItem('Humidity:', data.main.humidity + ' %');
                            list += makeItem('Pressure:', data.main.pressure + ' hPa');
                            list += makeItem('Temperature:', data.main.temp.toFixed(0) + ' &deg;C');
                            list += makeItem('Max Temperature:', data.main.temp_max.toFixed(0) + ' &deg;C');
                            list += makeItem('Min Temperature:', data.main.temp_min.toFixed(0) + ' &deg;C');
                            list += makeItem('Wind Speed:', data.wind.speed.toFixed(1) + ' m/s');
                            if (data.wind.deg) {
                                list += makeItem('Wind Direction:', getWindDirection(data.wind.deg));
                            }
                            if (data.weather && data.weather.length > 0)
                            {
                                for (var i = 0; i < data.weather.length; i++)
                                {
                                    list += makeItem(data.weather[i].main + ':', 
                                        data.weather[i].description, 
                                        data.weather[i].icon);
                                }
                            }
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