

//DELCLARACAO DE CONSTANTE
const CONSTANTE = 24;


valida cpf
var cpf = $(this).val();
var res = cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
//var btnLigar = document.getElementById("btnLigar");


//btnLigar.addEventListener('click',ligarLapada,false);

//var nome = prompt("Qual o seu nome");
//console.log(nome)


/********************************************************
	OPERADORES ESPECIAIS
*********************************************************/

//?--> OPERADOR TERNARIO
var idade = 6;
var resultado = (idade>=18)?"maior":"menor";
console.log(resultado);
//,--> SEARADOR DE EXPRECAO
//delete --> apaga operando
Pessoa={
	nome:"Ednei",
	tel:"22222"
}
delete Pessoa.nome
console.log(Pessoa.nome);//r3movido


//in--> verifica se existe
console.log("tel" in Pessoa);

//instanceof-->verifica se e um instacia do calss
var numero = new Number(3);
console.log("Numero e itancia Number" ,numero instanceof Number);
console.log("Numero e itancia String" ,numero instanceof String);

//typeOf--> retorn o tuipo do operando( caso false retiorna boolean)
console.log(typeof(true))


/*********************************************************
	FOR IN
*********************************************************/
console.log("=============================================================")
var array = new Array("um", "dois", "tres") ;
var Carro = {
	marca:"Nisam",
	medelo:"350",
	velocidade:"320km/h"
}
//form simples
for(var i=0;i<array.length;i++)
	console.log(array[i]);
console.log("=============================================================")
//for simplificado
for(i in Carro)
	console.log(Carro[i]);


/*************************************************************************
	TRATAMENTO DE EXCECOES
*************************************************************************/
console.log("===== THROWS ==============================================");
var numero = 0;//prompt("Informe seu numero menor que 10");
try{

	//se o numero for menor dez exiebe a menssagen de erro
	if(numero<10)
		throw "Numero maior que dez";
	/*var x =a;
	var b = x+10;
	console.log(b);*/

	//caso con trario exeibe a menssagem certa
	console.log(numero);

}catch(e){
	console.log(e.name+" - " +e.message);
}

/*============================
	STRUCTS
===============================*/
var novoCarro={
	marca:"Nissan",
	modelo:"357",

	Potencia:{
		cavalo:"350",
		velocidade:"320km"
	}
}

console.log("======STRUCTS =========================================")
console.log(novoCarro.Potencia.cavalo);

console.log("===== with =================================================");

//acessando deiretamento 
with(novoCarro.Potencia){
	console.log(cavalo);
	console.log(velocidade);
}

/********************************************************
	OBJETOS
*********************************************************/
console.log("===== OBJETOS ===============================");
var nome = "ednei";

console.log(nome.length);//total de carcter
console.log(nome.toUpperCase());//Maiusclo

var n = new Number("120.45");
console.log(Number.MIN_VALUE);

console.log("===== NUMBER ===============================");
console.log("toFixed: ",n.toFixed(1));//retira  ou acrentenca as casas decimais
console.log("toPrecision",n.toPrecision(1));
console.log("toExponencial",n.toExponential());


console.log("===== STRING ===============================");
var str = new String("texto dentro,  texto de, aspas");
console.log(str);

console.log("length: ",str.length);
console.log("charAt", str.charAt());

//methodo unicoide
console.log("charCodeAt", str.charCodeAt());

//concatena
console.log("concat", str.concat(" JAVA"));

//convertge valor uniciode para string(methode e statico precisa acessdaod dioreto da class)
console.log("fromCharCode", String.fromCharCode(115));

//primeira ocorenca da string
console.log("indexOf", str.indexOf("dentro"));

//ultima ocorenca da string
console.log("lastIndexOf", str.lastIndexOf("texto"));

var re =/[a-z]/;//todas as letrras de a ate e
console.log("match", str.match(re));


/******************************************************************************
	CONVERSAO E REPLACE 
*****************************************************************************/
//replace altera o texto

console.log("replace", str.replace("texto","Novo texto"));

var n = 10;
//alert(parseFloat(n));
alert(n.toFixed(2).replace(".",","));

//=============================================
//RECORTAR STRING
//============================================

console.log("substring" ,str.substring(5,8));

//inform a o paramestro do indice inical a os caracteres restantes
console.log("substr", str.substr(5,8));

//slice server para se o indice final for menor que o incial os valores nao serao invertido
console.log("slice", str.slice(5,8));

//corta todas as string que tem separador
console.log("split", str.split(",")[0]);

//mausuculka
console.log("toUpperCase", str.toUpperCase());

//MINUSCULA
console.log("toUpperCase", str.toLowerCase());


/********************************************************
	ARRAY
*********************************************************/
console.log("==== ARRAY =================================");
var paises =["Brasil", "Mexico", "India", "Russia","China", "Argentina"];
console.log(paises.lastIndexOf("Barsil"));

//ordenar
console.log(paises.sort());

//alterar o separador padrao
console.log(paises.join("/ "));

//retorna apartir de um index especifico o total e elementos
console.log(paises.slice(2, 2));


//remover elemtos do array
console.log(paises.splice(2, 2,"Abacate", "acerola","Bicilceta"));

//exibe os elemntos que sobraram
console.log(paises);

console.log("Filas==================================")
var pessoas =["ednei", "jose"];
console.log(pessoas)

console.log("Adiciona no ultimo",pessoas.push("Maria"));
console.log(pessoas)
//remove 
//remove o primeri elemento da fila
console.log("Remove primerio:",pessoas.shift())
console.log(pessoas)

console.log("Remove ultimo:",pessoas.pop());//remove o ultimo elemnto da fila
console.log(pessoas)

//add eleentos no inicio da fila
console.log("Adiciona em primeiro: ",pessoas.unshift("Novo elemto"));
console.log(pessoas)


//===================================================
//	 MATRIZ MULTIMENSIONAIS
//=================================================
console.log("MATRIZ ======================================");
var mat =[
		["Ednei", "M"],
		["Emilly", "F"],
	]
console.log(mat[0][0]);

//===================================================
//	 ARRAY  ASSOCIATIVO
//=================================================

console.log("ARRAY ASSOCIATOVO ======================================");
var arrayAsso = {
	nome:"endei",
	cidade:"RJ"
}

var frutas = ["banana", "caju","maca","ana"];
console.log(arrayAsso.nome);

//verifica se todos sao do tirpo string
function todos(elem, ind, obj){
	return (typeof elem == "string");
}

//retorta as palavas com a letra an
function filtro(elem, ind, obj){
	return (elem.indexOf("an") > 0);
}


function Maisuculas(elem, ind, obj){
return (elem = elem.toUpperCase());
}

///verifica se dentro de um array existe algum elemtos  e string
console.log(frutas.every(todos));

///verifica se dentro de um array existe algum elemtos  e numero
console.log(frutas.some(todos));

///verifica se dentro de um array existe algum elemtos  e numero
console.log(frutas.filter(filtro));

//alterar todos os elemtos do array
console.log(frutas.map(Maisuculas));



//============================================
//  DATAS
//=============================================
console.log("==== DATAS ====================================");
var data = new Date();

//retona
console.log(data.getHours()+":"+data.getMinutes());

//retorna o dia
console.log(data.getDay());

//============================================
//  MATH- operacoes matematicas
//=============================================
console.log("==== MATH ====================================");

//comprimeto de uma cricunferencia de 30 de raio
console.log(2*Math.PI*30);

//FUNCAO ABS VALOR ABSOLUTO DE UM NUMERO
console.log(Math.abs(3));

//funcao max numero de maior valor dentro de um conjuto
console.log(Math.max(1, 2, 3, 5));

//funcao max numero de maior valor dentro de um conjuto
console.log(Math.min(1, 2, 3, 5));

//funcao round aredonda para o inteiro mas proximo
console.log(Math.round(1.9));

//aredonda sempre para baixo
console.log(Math.floor(1.9));

//potencia
console.log(Math.pow(2, 3));

//raiz quadada
console.log(Math.sqrt(9));

//random 
console.log(Math.floor(Math.random() * 10)+1);

//============================================
//  REGEX --(expressao regular) DEFINE UM PADRA PARA PESQUISAS
//=============================================
console.log("==== REGEX ====================================");

//var regex = new RegExp("JavaScript", "i");
//modificador i--> ignora as letras minusculas

var regex =/javascript/i;
var string = "JavaScript";

console.log(regex.test(string));

// outra forma
console.log(/javascript/i.test("javascript"));

//funcao exec reotra a primeria ocirrencia de string
var str= "Qaul o Doce mas doce que o doce";
var regex2=/doce/ig.exec(str);
console.log(regex2);

console.log(str.match(regex2))

/* META CARTACTERES*/
console.log("==== META CARACTERES ====================================");

//meta caracter . encontra qualeur caracter 
console.log(/./.test("ednei"));

//meta caracter 'w'
//pruca por qualquer caracter de a - z, 0-9 e anderline
console.log(/\w/.test("Ednei 2"));

//metaca caracter 's' procura por espaco dentro do texto
console.log(/\s/.test("Ednei 2"));

//meta caracter 'd ' verifica se existe ocorrencia de digitpos
console.log(/\d/.test("Ednei 2"));

//op meta caracter '^' indica a ocirrencia  no inicio da string
console.log(/\^21/.test("Ednei 2"));


//op meta caracter '$' indica a ocirrencia  no final da string
console.log(/\$21/.test("Ednei 2"));

//verifica r a ocorrencia no final e no incio da string
console.log(/\d\d$/.test("Ednei21"));

//padrao para verificar ceps
console.log(/^\d\d\d\d\d-\d\d\d$/.test("20950-091"));

/*ANALISAR OS QUANTIFICADOES*/

console.log(/\d/.test("Ednei 2"));


//validar CEP
console.log(/^\d{5}-\d{3}$/.test("20950-091"));

//validar data variacao no finla no mino 2 digitos ou 4 digites
console.log(/^\d{2}\/\d{2}\/\d{2,4}$/.test("12/12/1980"));

//validar um email
//w+ avakia um ou m as carcteres
console.log(/\w+@\w+\.\w{2,3}/.test("rh@exemplo.com.br"));

/*caracteres opcionais*/
console.log(/c[ae]u/.test("cau"));

//oceorencia de digitos( encontra digots ou virgulas)
console.log(/\d[\d\,]*/.test("23, 45"));

/************************************************************
	BUSCA
************************************************************/
var str = "Qual odoce mas doce que o doce";

//encontra a palavra doce
console.log(str.match(/doce/ig));
//executa a troca
console.log(str.replace(/doce/ig,"KKKKK"));

var str2 = "O rato roeu a rota de rei de roma";
//recebe as palavras que comecam com r e vai ate z
console.log(str2.match(/r[a-z]/ig));
//substitui por outra palavra
console.log(str2.replace(/r[a-z]/ig,"xxxx"));


/**
	URL
**/
var url = "www.xti.com.br/cliente-2011.html";

//ENCONTRAR A EXPRESSAO REGULAR
var re = /www.xti.com.br\/\w{2,}-\d{4}\.html/;

//substituir
console.log(re.test(url));
//quando marca com parenteses vc reutilizar usando replace
re = /(www.xti.com.br)\/(\w{2,})-(\d{4})\.html/;

console.log(url.replace(re,"http://$1/$3/$2.jsp"));


/*********************************************************
	BROUSER MODEL
*********************************************************/

	//window.open("teste.html", "Titulo" ,"width=300, height=500");
	window.onload=function(){
		window.resizeTo(300,300);
		window.moveTo(300,300);
	}

/********************************************************
	EVENTOS
*********************************************************/
function ligarLapada(){
	document.getElementById("imgLanpada").src="images/lampada-acesa.jpg";
}



//======================== PHP ======================
$message[] = array('message'=>'Dados inseridos com sucesso');
echo json_encode($message);

//======================== js ======================
setInterval(function(){
    var lat = $('#lat').text();
    var lng = $('#lng').text();

    $.ajax({

      url:'http://wiki.moebius.com.br/tests/teste.php?lat='+lat+'&lng='+lng,
      type:'get',
      dataType:'json',

      beforeSend:function(){
        console.log('carregando..')
      },
      success: function(data){
        console.log(data[0].lat)
      }
    })
  }, 3000);
