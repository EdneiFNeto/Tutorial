
window.onload = function(){
    apiAJAX('url', 'GET');
}

function apiAJAX(url, type) {

    var param;
    var name;
    var city;

    if (type === "GET" || type === "get")
        get(url, param);
    else
        post(url, name, city);
}

function get(url, param) {
    $.get(url + "?param=" + param, function (data, status) {
        console.log("Data: " + data + "\nStatus: " + status);
    });
}

function post(url, name, city) {
    $.post(url, { name: name, city: city }, function (data, status) {
        console.log("Data: " + data + "\nStatus: " + status);
    });
}
