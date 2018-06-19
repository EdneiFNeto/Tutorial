create database SQLDB;

	create table cliente(

		codigo int not null auto_increment,
		nome varchar(255) not null,
		idade int ,
		sexo char(1),
		cod_cidade int,
		constraint pk_cliente primary key(codigo),
		constraint cliente_sexo check(sexo ='F' or sexo = 'M'),
		constraint fk_cliente foreign key(cod_cidade)
		references cidade (codigo)
	);

	drop table cliente;
	drop table cidade;

	create table cidade(

		codigo int not null,
		cidade char(2),
		constraint pk_cidade primary key(codigo)
	);

--TABELA NORMALIZADA

insert into cliente
	values
	(1, 'Ednei'		,34	, 'M'	,1),
	(2, 'Ana'		,34	, 'F'	,2),
	(3, 'Marcia'	,24	, 'F'	,2),
	(4, 'Joao'		,14	, 'M'	,2),
	(5, 'Roberto'	,54	, 'M'	,3),
	(6, 'Jose'		,44	, 'M'	,3),
	(7, 'Maria'		,4	, 'F'	,4),
	(8, 'Viviany'	,21	, 'F'	,4),
	(9, 'Emilly'	,41	, 'F'	,1),
	(10,'Claudia'	,13	, 'F'	,1);

	insert into cidade
		values
		(1,'RJ'),
		(2,'SP'),
		(3,'MG'),
		(4,'BH'),
		(5,'RC');


--select os clientes de rj
select nome, cidade, idade from cliente inner join cidade
on cliente.cod_cidade = cidade.codigo;

-- ===================================================
-- COUNT TABELA NORMALIZADA
-- ===================================================

--total de cliantes do SP
select cidade, count(*) as total from cliente inner join cidade
on cliente.cod_cidade = cidade.codigo
where cidade ='sp';

-- ===================================================
-- GROUP BY  TABELA NORMALIZADA
-- ===================================================

-- total de clietes que nao mora em sao apulo
select cidade, count(*) as total from cliente inner join cidade
on cliente.cod_cidade = cidade.codigo
group by cidade having cidade not in('sp') order by nome;

-- todos de clietes que  nao mora em sao paulo e sao maiores de 30
select nome, idade, cidade,count(*) as total from 
cliente inner join cidade
on cliente.cod_cidade = cidade.codigo
group by nome having idade >=25 and cidade not in('sp') order by nome;

truncate cliente;

-- ===================================================
-- DELETE COM CHAVE ESTRANGEIRA TABELA NORMALIZADA
-- ===================================================
--deletar os clietnes que moram em sao paulo

delete from cliente where cod_cidade  in
	(select codigo from cidade where cidade  in ('rj'));


	select nome, cidade from cliente,cidade
	where cliente.cod_cidade=cidade.codigo
	and codigo = 'RJ';

-- ===================================================
-- UPDATE COM CHAVE ESTRANGEIRA TABELA NORMALIZADA
-- ===================================================
update cliente set nome = 'Outro nome' where codigo=1;

	select nome,cidade from cliente ,cidade
	where cliente.cod_cidade = cidade.codigo
	and cidade='rj';

--trocar o nome do clientes do RJ
update cliente set nome = 'Novo Ednei' where nome='Ednei' and cod_cidade
	in (select codigo from cidade where cidade = 'rj');


-- ===================================================
-- COUNT
-- ===================================================

--total de cliente no banco
select count(*) as total from cliente;

--total de cliente com idade maior que 30
select count(*) as total from cliente
where idade > 30;

--verificar nome dos cleintes que nao mora no RJ
select nome,cidade from cliente where cidade not in ('RJ');

-- ===================================================
-- GROUP BY 
-- ===================================================

--agrupa os clientes por idade
select idade , count(idade) as total from cliente
group by idade;


-- ======================================================
-- HAVING - PARA UTILIZALO E PRECISO QUE SEJA QUE TENHA
-- 			DELACRADO NO SELECT
-- ======================================================
--agrupa os clientes por idade e todos tem que ser maiores que 30
select idade , count(idade) as total from cliente
group by idade having idade > 30;


--verificar quantos cleintes nao mora no RJ
select nome,cidade, count(*) as total from cliente 
group by nome having cidade not in ('RJ');

--selecionar nome, idade, cidade dos clientes 
--que nao mora no RJ e sao menor que 25 anos
select idade,cidade, count(*) as total from cliente
group by nome having idade <= 25 and cidade  not in('RJ');



insert into cliente
	values
	(1, 'Ednei'		,34	, 'M'	,'RJ'),
	(2, 'Marcia'	,18	, 'F'	,'SP'),
	(3, 'Ana'		,19	, 'F'	,'MG'),
	(4, 'Viviany'	,31	, 'F'	,'BH'),
	(5, 'Emilly'	,54	, 'F'	,'RJ'),
	(6, 'Joao'		,34	, 'M'	,'RJ'),
	(7, 'Joao'		,34	, 'M'	,'RJ'),
	(8, 'Ednei'		,34	, 'M'	,'RJ'),
	(9, 'Marcia'	,18	, 'F'	,'SP'),
	(10, 'Ana'		,19	, 'F'	,'MG'),
	(11, 'Viviany'	,31	, 'F'	,'BH'),
	(12, 'Emilly'	,54	, 'F'	,'RJ'),
	(13, 'Joao'		,34	, 'M'	,'RJ'),
	(14, 'Ednei'	,34	, 'M'	,'RJ'),
	(15, 'Marcia'	,18	, 'F'	,'SP'),
	(16, 'Ana'		,19	, 'F'	,'MG'),
	(17, 'Viviany'	,31	, 'F'	,'BH'),
	(18, 'Emilly'	,54	, 'F'	,'RJ'),
	(19, 'Joao'		,34	, 'M'	,'RJ');


insert into fibra (id,  lat, lng)
values
(1, -22.905352524637358, -43.241801261901855),
(2, -22.90175511692273, -43.25184345245361),
(3, -22.90472002030512, -43.27321529388428),
(4, -22.904680488686385, -43.28793525695801),
(5, -22.90349453476612, -43.29686164855957),
(6, -22.906538462308486, -43.2996940612793)

--select o dia na tabela
SELECT data_registro as data
from elemento_otico2 where nome ='Novo-Usuario' 
and extract(Day FROM  data_registro) = 5
order by data asc

SELECT 
codigo, 
data_registro, 
latitude, 
longitude, 
tarefa, 
nome 
FROM elemento_otico2 
WHERE nome = 'Novo-Usuario' 
and extract(Day FROM  data_registro)    = 5 
and extract(MONTH FROM  data_registro)  = 2 
and extract(YEAR FROM  data_registro)   = 2018
ORDER BY codigo ASC

--GROUP BY & ORDER BY
select codigo, nome, latitude, longitude, data_registro from elemento_otico2
group by codigo, nome, latitude, longitude, data_registro having nome = 'Ednei' order by codigo asc 

--SELECT ANO ATUAL
SELECT date_trunc('month',current_date);

--SELECT DATA ATUAL
SELECT current_date ;

--ultimo dia do mes
SELECT date_trunc('month',current_date/*data atual*/) + INTERVAL'1 month' - INTERVAL'1 day' 

--select dia antes
SELECT  date_trunc('day',current_date) - interval '1 day';

--AGRUPA OS DADOS POR DATA
 SELECT nome, CAST (data_registro AS DATE) as data from elemento_otico2 
 group by data, nome

--SELECT REGISTRO ATUAL
 SELECT nome, latitude, longitude, bateria, CAST (data_registro AS DATE) as data 
 from elemento_otico2 where CAST (data_registro AS DATE) = current_date

--SELECT DIA DE ONTEM
select  codigo, nome, latitude, longitude,  cast(data_registro as date) as data, bateria from elemento_otico2  
where cast(data_registro as date) = cast(CAST(current_date as date) - interval '1 day' as date)
order by codigo asc


--SELEC DATA ATUAL
select * from elemento_otico2 where cast(data_registro as date) = cast(current_date as date)


 SELECT  
	c.codigo,
	r.latitude, 
	r.longitude,
	count(*) as total 
FROM rota_cabo2 as r, cabos2  as c
where c.codigo = r.cod_cabo
and r.grupo =  'Casa SC'
and c.nome  = 'Rede Aerea'
and c.rota  = 'Casa SC'
group by c.codigo, r.latitude, r.longitude
order by c.codigo

SELECT
	c.codigo,
	c.nome, 
	r.cod_cabo, 
	r.latitude, 
	r.longitude, 
	c.rota 
FROM cabos2 as c, rota_cabo2 as r
WHERE c.codigo = r.cod_cabo
AND c.codigo = 241

SELECT 
	c.codigo, 
	count(*) as total, 
	c.rota, 
	c.nome  
FROM cabos2 as c, rota_cabo2 as r
WHERE c.codigo = r.cod_cabo
GROUP BY c.rota, c.codigo, c.nome
HAVING  c.nome = 'Rede Aerea'
ORDER BY c.codigo ASC

