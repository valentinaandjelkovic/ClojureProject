var iconTable = {
    'clear-day': 'wi-day-sunny',
    'clear-night': 'wi-night-clear',
    'rain': 'wi-rain',
    'snow': 'wi-snow',
    'sleet': 'wi-rain-mix',
    'wind': 'wi-cloudy-gusts',
    'fog': 'wi-fog',
    'cloudy': 'wi-cloudy',
    'partly-cloudy-day': 'wi-day-cloudy',
    'partly-cloudy-night': 'wi-night-alt-cloudy',
    'hail': 'wi-hail',
    'thunderstorm': 'wi-thunderstorm',
    'tornado': 'wi-tornado'
};

var daysName = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];


var map;
var marker = false;

function initMap() {
    var options = {
        center: new google.maps.LatLng(44.64341002034012, 20.547695138175186),
        zoom: 7
    };

    map = new google.maps.Map(document.getElementById('map'), options);

    google.maps.event.addListener(map, 'click', function (event) {
        var clickedLocation = event.latLng;
        if (marker === false) {
            marker = new google.maps.Marker({
                position: clickedLocation,
                map: map,
                draggable: true
            });
            google.maps.event.addListener(marker, 'dragend', function (event) {
                markerLocation();
            });
        } else {
            marker.setPosition(clickedLocation);
        }
        markerLocation();
    });
}

function markerLocation() {
    var currentLocation = marker.getPosition();
    $.ajax({
        type: "GET",
        url: "/weatherData",
        data: {lat: currentLocation.lat(), lng: currentLocation.lng()},
        dataType: "json",
        success: function (weather) {
            if (weather) {
                $("#weather_div").empty();
                processData(weather);
            }
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function processData(weatherData) {
    var currentWeather = weatherData.currently;
    var hourly = weatherData.hourly;
    var wrapper = document.getElementById("weather_div");

    var currentWeatherDiv = document.createElement("div");
    var icon = currentWeather ? currentWeather.icon : hourly.icon;
    var iconClass = iconTable[icon];
    var icon = document.createElement("span");
    icon.className = 'big-icon wi ' + iconClass;
    currentWeatherDiv.appendChild(icon);

    var summary = document.createElement("div");
    summary.className = "summary";
    summary.innerHTML = hourly.summary;

    wrapper.appendChild(currentWeatherDiv);
    wrapper.appendChild(summary);

    var table = document.createElement("table");
    var days = [];
    hourly.data.map(function (item) {


        var row = document.createElement("tr");
        row.className = "forecast-row";

        var date = new Date(item.time * 1000);
        var hour = date.getHours();
        var hourTextSpan = document.createElement("span");
        hourTextSpan.className = "forecast-hour"
        hourTextSpan.innerHTML = (hour < 10 ? "0" + hour : hour) + ":00";

        var iconClass = iconTable[item.icon];
        var icon = document.createElement("span");
        icon.className = 'wi weathericon ' + iconClass;

        var precipPossibility = document.createElement("span");
        precipPossibility.innerHTML = "N/A"
        if (item.precipProbability != null) {
            precipPossibility.innerHTML = parseInt(item.precipProbability * 100) + "%";
        }
        precipPossibility.className = "precipitation"

        var temperature = document.createElement("span");
        temperature.innerHTML = Math.round(item.temperature) + "&deg";
        temperature.className = "temp";

        var wind = document.createElement("span");
        wind.innerHTML = item.windSpeed + "m/s";
        wind.className = "wind";

        var humidity = document.createElement("span");
        humidity.innerHTML = parseInt(item.humidity * 100) + "%";
        humidity.className = "humidity";


        var rowDay = document.createElement("tr");
        rowDay.className = "forecast-row";
        var daySpan = document.createElement("span");

        //day
        if (days.indexOf(daysName[date.getDay()]) == -1) {
            daySpan.innerHTML = daysName[date.getDay()];
            days.push(daysName[date.getDay()]);
            rowDay.appendChild(daySpan);
            if (days.length == 1) {
                var tableInfo = document.createElement("table");

                var probabilityInfo = document.createElement("tr");
                probabilityInfo.innerHTML = "N/A"
                if (item.precipProbability != null) {
                    probabilityInfo.innerHTML = "Probability of precipitation: " + parseInt(item.precipProbability * 100) + "%";
                }
                probabilityInfo.className = "precipitation"

                var temeperatureInfo = document.createElement("tr");
                temeperatureInfo.innerHTML = "Temperature: " + Math.round(item.temperature) + "&deg ";
                temeperatureInfo.class = "temp";

                var apperentTemperatureInfo = document.createElement("tr");
                apperentTemperatureInfo.innerHTML = "Feels like: " + Math.round(item.apparentTemperature) + "&deg ";
                apperentTemperatureInfo.class = "temp";

                var windInfo = document.createElement("tr");
                windInfo.innerHTML = "Wind speed: " + item.windSpeed + "m/s";
                windInfo.className = "wind";

                var humidityInfo = document.createElement("tr");
                humidityInfo.innerHTML = "Humidity: " + parseInt(item.humidity * 100) + "%";
                humidityInfo.className = "humidity";

                tableInfo.appendChild(temeperatureInfo);
                tableInfo.appendChild(apperentTemperatureInfo);
                tableInfo.appendChild(probabilityInfo);
                tableInfo.appendChild(windInfo);
                tableInfo.appendChild(humidityInfo);

                table.appendChild(tableInfo);

            }
            table.appendChild(rowDay);
        }

        row.appendChild(icon);
        row.appendChild(hourTextSpan);
        row.appendChild(temperature);
        row.appendChild(precipPossibility);
        row.appendChild(wind);
        row.appendChild(humidity);
        table.appendChild(row);
    });

    wrapper.appendChild(table);
}

google.maps.event.addDomListener(window, 'load', initMap);
