create database sistema_fibras;

use sistema_fibras;

create table cabo(

	codigo int not null auto_increment,
	nome varchar(255) not null,
	ponta_a int,
	ponta_b int,
	constraint pk_cabo primary key(codigo)
);

create table elemento2(

	codigo int not null auto_increment,
	nome varchar(255),
	latitude real,
	longitude real,
	constraint pk_elemento2 primary key(codigo)
);


--insere elemento 
insert into elemento (codigo) values (default);

--select  o ultimo codigo
select max(codigo) as codigo from elemento;

--insere o ultimo na tabela de cobo
insert into cabo (nome, ponta_b)
values
('Cabo 1', 1  ),
('Cabo 1', 4  ),
('Cabo 1', 3  ),
('Cabo 1', 5  ),
('Cabo 1', 2  ),
('Cabo 1', 0  )
