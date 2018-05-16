

-------------------------------------------------
--1 DROP INDEX CEP
---------------------------------------------------

use dbotrabalho
go

--REMOVE INDEX
alter table cliente drop constraint fk_cep_cliente;
alter table cep drop constraint pk_CEP;

--ADD INDEX CEP
alter table cep add constraint pk_CEP primary key(CEP)
alter table cliente add constraint fk_cep_cliente foreign key(CEP_FK) references CEP(CEP)


select * from cep;


----------------------------------------------------
--2 DROP INDEX PAIS
---------------------------------------------------


--DROP FK
alter table estado drop constraint fk_pais_f;
alter table cliente drop constraint fk_pais;
--DROP PK
alter table pais drop constraint pk_pais;

--ADD PK
alter table pais add constraint pk_pais primary key(sigla)
--ADD FK
alter table estado add constraint fk_pais_f foreign key(pais_FK) references pais(sigla)
alter table cliente add constraint fk_pais foreign key(pais_FK) references pais(sigla)

select * from pais;


-------------------------------------------------
--3 DROP INDEX ASSOCIADO
---------------------------------------------------

--DROP FK
alter table corrida drop constraint fk_codigo_associado_fk;
alter table carro   drop constraint fk_codigo_associado;
--DROP PK
alter table associado drop constraint pk_associado;

--ADD PK
alter table associado add constraint pk_associado primary key( codigo)
--ADD FK
alter table corrida  add constraint fk_codigo_associado_fk foreign key(codigo_associado_fk) references associado(codigo)
alter table carro add constraint fk_codigo_associado foreign key(codigo_associado) references associado(codigo)

select * from associado


-------------------------------------------------
--4 DROP INDEX ESTADO
---------------------------------------------------
use dbotrabalho
go

--DROP FK
alter table cliente drop constraint fk_estado;
--DROP PK
alter table estado drop constraint pk_estado;

--ADD PK
alter table estado add constraint pk_estado primary key(sigla)
--ADD FK
alter table cliente  add constraint fk_estado foreign key(estado_FK) references estado (sigla)

select * from estado



-------------------------------------------------
--5 DROP INDEX CLIENTE
---------------------------------------------------
use dbotrabalho
go

--DROP KF
alter table cartao drop constraint fk_codigo_cliente;
alter table corrida drop constraint fk_codigo_cliente_corrida
--DROP PK
alter table cliente drop constraint pk_cliente;

--ADD PK
alter table cliente  add constraint pk_cliente primary key(codigo)
--ADD FK
alter table cartao add constraint fk_codigo_cliente foreign key(codigo_cliente) references cliente(codigo)
alter table corrida add constraint fk_codigo_cliente_corrida foreign key(codigo_cliente_fk) references cliente(codigo)

select * from cliente 



-------------------------------------------------
--6 DROP INDEX CARTAO
---------------------------------------------------

--DROP FK
alter table tipo_do_pagamento drop constraint pk_numero_cartao;
--DROP PK
alter table cartao drop constraint pk_cartao;

--ADD PK
alter table cartao add constraint pk_cartao primary key(numero)
--ADD FK
alter table tipo_do_pagamento  add constraint pk_numero_cartao foreign key(numero_cartao) references cartao(numero)

select * from cartao


-------------------------------------------------
--7 DROP INDEX TIPO DE PAGAMENTO
---------------------------------------------------
use dbotrabalho
go

--DROP FK
alter table corrida drop constraint fk_codigo_tipo_pagamento_fk;
--DROP PK
alter table tipo_do_pagamento drop constraint pk_tipo_do_pg;

--ADD PK
alter table tipo_do_pagamento  add constraint pk_tipo_do_pg primary key(codigo)
--ADD FK
alter table corrida add constraint fk_codigo_tipo_pagamento_fk 
foreign key(codigo_tipo_pagamento_fk) references tipo_do_pagamento( codigo)

select * from tipo_do_pagamento 

 -------------------------------------------------
--8 DROP INDEX CARRO
---------------------------------------------------
use dbotrabalho
go

--DROP FK
alter table corrida drop constraint fk_codigo_renavam_fk;
--DROP PK
alter table carro drop constraint pk_carro;

--ADD PK
alter table carro add constraint pk_carro primary key(codigo_renavam)
--ADD FK
alter table corrida add constraint fk_codigo_renavam_fk foreign key(codigo_renavam) references  carro(codigo_renavam)

select * from carro


-------------------------------------------------
--9 DROP INDEX CORRIDA
---------------------------------------------------
use dbotrabalho
go

--DROP PK
alter table corrida drop constraint pk_corrida;
--ADD PK
alter table corrida  add constraint pk_corrida primary key(codigo)

select * from corrida