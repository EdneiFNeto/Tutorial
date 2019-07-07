//=================================================================
//CRONOMETRO
//=================================================================
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Cronometro</title>
</head>

<body>
    <div id="tempo"></div>
    <div id="fim"></div>
</body>

<script>
    var countSecond = 0;

    window.onload = function() {
        loadDoc();
    }

    function loadDoc() {
        var xhttp = new XMLHttpRequest();
        xhttp.responseType = 'json';
        xhttp.onreadystatechange = function() {
            
            if (this.readyState == 4 && this.status == 200) {
                console.log(this.response[0]);
                var dataInicio = new Date(this.response[0].inicio);
                var day = dataInicio.getDay();
                var month = dataInicio.getMonth();
                var year = dataInicio.getFullYear();
                var hora = dataInicio.getHours();
                var minutos = dataInicio.getMinutes();
                var segundos = dataInicio.getSeconds();

                var dataFim = new Date(this.response[0].termina);
                var dayF = dataFim.getDay();
                var monthF = dataFim.getMonth();
                var yearF = dataFim.getFullYear();
                var horaF = dataFim.getHours();
                var minutosF = dataFim.getMinutes();
                var segundosF = dataFim.getSeconds();

                var temp = setInterval(() => {

                    document.querySelector("#fim").innerHTML = "Termina: " + horaF + ":" + minutosF + ":" + segundosF;

                    if ((segundos >= 0 && segundos < 10)) {
                        zero = "0";
                    } else {
                        zero = "";
                    }

                    if ((minutos >= 0 && minutos < 10)) {
                        zeroMin = "0";
                    } else {
                        zeroMin = ""
                    }

                    if ((hora >= 0 && hora < 10)) {
                        zeroHousrs = "0";
                    } else {
                        zeroHousrs = ""
                    }

                    document.querySelector("#tempo").innerHTML = "Inicio: " + zeroHousrs + "" + hora + ":" + zeroMin + "" + minutos + ":" + zero + "" + segundos;

                    segundos += 1;
                    if (segundos > 59) {
                        segundos = 0;
                        minutos += 1;
                        if (minutos > 59) {
                            minutos = 1;
                            hora += 1;

                            if (hora > 23)
                                hora = 0;
                        }
                    }

                    var newHours = new Date(day+"/"+month+"/"+year+" "+hora+":"+minutos+":"+segundos);
                    var oldHours = new Date(dayF+"/"+monthF+"/"+yearF+" "+ horaF+":"+minutosF+":"+segundosF);

                    //console.log(newHours+"\n"+oldHours);
                    if (newHours > oldHours) {
                        clearInterval(temp);
                    }
                }, 900)
            }
        };

        xhttp.open("GET", "http://192.168.0.15/android-api/hora.php", true);
        xhttp.send();
    }
</script>

</html>

//=================================================================
//FIM - CRONOMETRO
//=================================================================
window.onload = function(){
    apiAJAX('url', 'GET');
}

function ajax(){
        var xhttp = new XMLHttpRequest();
            xhttp.responseType = 'json';
            xhttp.onreadystatechange = function() {
                if(this.readyState == 4 && this.status == 200) {
                    console.log(this.response);
                }
            };

            xhttp.open("POST", "controller/selectControll.php", true);
            xhttp.send();
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
