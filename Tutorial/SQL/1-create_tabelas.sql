
--drop database dbotrabalho

create database dbotrabalho
go
---------------------------------------------------
--1 CEP
---------------------------------------------------
use dbotrabalho
go

create table CEP(
	CEP varchar(9) not null,
	Logradouro numeric(4) not null,
	constraint pk_CEP primary key(CEP)
);
print('---------------------------------')
print('TABELA CEP CRIADA COM SUCESSO')
print('---------------------------------')
go
---------------------------------------------------
--2 PAIS
---------------------------------------------------
use dbotrabalho
go
create table pais(
	sigla varchar(9) not null,
	nome varchar(40) not null,
	constraint pk_pais primary key(sigla)
);
print('---------------------------------')
print('TABELA PAIS CRIADA COM SUCESSO')
print('---------------------------------')
go
---------------------------------------------------
--3 ASSOCIADO
---------------------------------------------------

use dbotrabalho
go
create table associado(

	codigo bigint not null,
	nome  varchar(50) not null,
	data_associacao date not null,
	carteira_motorista numeric(12) not null,
	constraint pk_associado primary key( codigo)
);
print('---------------------------------')
print('TABELA CEP ASSOCIADO COM SUCESSO')
print('---------------------------------')
go


---------------------------------------------------
--5 ESTADO
---------------------------------------------------

use dbotrabalho
go

create table estado(
	
	sigla varchar(2) not null,
	nome varchar(20) not null,
	pais_FK varchar(9) not null,
	constraint pk_estado primary key(sigla),
	constraint fk_pais_f foreign key(pais_FK) references pais(sigla)
);
print('---------------------------------')
print('TABELA ESTADO CRIADA COM SUCESSO')
print('---------------------------------')
go
---------------------------------------------------
--6 CLIENTE
---------------------------------------------------

use dbotrabalho
go

create table cliente(
	codigo varchar(4) not null,
	nome varchar(40) not null,
	endereco varchar(50) not null,
	identidade varchar(20) not null,
	CEP_FK varchar(9) not null,
	estado_FK varchar(2) not null,
	pais_FK varchar(9) not null,
	constraint pk_cliente primary key(codigo),
	constraint fk_cep_cliente foreign key(CEP_FK) references CEP(CEP),
	constraint fk_estado foreign key(estado_FK) references estado (sigla),
	constraint fk_pais foreign key(pais_FK) references pais(sigla)
);
print('---------------------------------')
print('TABELA CLEINTE CRIADA COM SUCESSO')
print('---------------------------------')
go

---------------------------------------------------
--7 CARTAO
---------------------------------------------------

use dbotrabalho
go

create table cartao(
	numero varchar(20) not null,
	validade numeric(4) not null,
	cvv numeric(4) not null,
	adiminstradora varchar(20) not null,
	codigo_cliente varchar(4) not null,
	constraint pk_cartao primary key(numero),
	constraint fk_codigo_cliente foreign key(codigo_cliente) references cliente(codigo)
);
print('---------------------------------')
print('TABELA CARTAO CRIADA COM SUCESSO')
print('---------------------------------')
go

select numero from cartao where numero = '12345611';
---------------------------------------------------
--4 TIPO DE PAGAMENTO
---------------------------------------------------

use dbotrabalho
go

create table tipo_do_pagamento(

	codigo bigint not null,
	descricao varchar(10) not null,
	numero_cartao varchar(20) not null,
	constraint pk_tipo_do_pg primary key(codigo), 
	constraint pk_numero_cartao foreign key(numero_cartao) references cartao(numero)
);
print('---------------------------------')
print('TABELA TIPO PG CRIADA COM SUCESSO')
print('---------------------------------')
go
---------------------------------------------------
--8 CARRO
---------------------------------------------------

use dbotrabalho
go

create table carro(

	codigo_renavam bigint not null,
	placa varchar(10) not null,
	categoria varchar(4) not null,
	codigo_associado bigint not null,
	constraint pk_carro primary key(codigo_renavam),
	constraint fk_codigo_associado foreign key(codigo_associado) references associado(codigo)
);

print('---------------------------------')
print('TABELA CARRO CRIADA COM SUCESSO')
print('---------------------------------')
go


---------------------------------------------------
--9 CORRIDA
---------------------------------------------------

use dbotrabalho
go
create table corrida(

	codigo bigint not null,
	data_solicitacao date not null,
	data_inicio date not null,
	data_hora_fim datetime not null,
	posicao_geografica__inicio numeric(4) not null,
	posicao_geografica__final numeric(4) not null,
	qtde_kms numeric(4) not null,
	valor_km numeric(4) not null,
	efetivada varchar(20)  not null,
	codigo_tipo_pagamento_fk bigint not null,
	codigo_associado_fk bigint not null,
	codigo_renavam bigint not null,
	codigo_cliente_fk varchar(4) not null,

	constraint pk_corrida primary key(codigo),
	constraint fk_codigo_tipo_pagamento_fk foreign key(codigo_tipo_pagamento_fk) references tipo_do_pagamento( codigo),
	constraint fk_codigo_associado_fk foreign key(codigo_associado_fk) references associado(codigo),
	constraint fk_codigo_renavam_fk foreign key(codigo_renavam) references  carro(codigo_renavam),
	constraint fk_codigo_cliente_corrida foreign key(codigo_cliente_fk) references cliente(codigo)
);

print('---------------------------------')
print('TABELA CORRIDA CRIADA COM SUCESSO')
print('---------------------------------')

go

------------------------------------------------------------------------
--DROP TABLE
------------------------------------------------------------------------


/*

drop table corrida
drop table carro
drop table cartao
drop table cliente
drop table estado
drop table tipo_do_pagamento
drop table associado
drop table pais
drop table CEP


*/


