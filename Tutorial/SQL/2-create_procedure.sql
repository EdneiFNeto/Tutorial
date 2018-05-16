
---------------------------------------------------
--PROCEDURE CEP
---------------------------------------------------
use dbotrabalho
go

create procedure CEP_Procedure 
	@cep varchar(9),
	@logradouro numeric(4)
as
begin 
insert into cep values(@cep, @logradouro)
end

print('---------------------------------')
print('PROCEDURE CEP CRIADA COM SUCESSO')
print('---------------------------------')
go
---------------------------------------------------
--PROCEDURE PAIS
---------------------------------------------------

use dbotrabalho
go

create procedure pais_Procedure
	@sigla varchar(9),
	@nome varchar(40)
as
begin insert into pais values(@sigla, @nome) end


print('---------------------------------')
print('PROCEDURE PAIS CRIADA COM SUCESSO')
print('---------------------------------')
go
---------------------------------------------------
--PROCEDURE ASSOCIODO
---------------------------------------------------

use dbotrabalho
go

create procedure associado_procedure
	@codigo bigint,
	@nome   varchar(50),
	@data_associacao date,
	@carteira_motorista numeric(12)
as
begin insert into associado values (@codigo, @nome, @data_associacao, @carteira_motorista) end

print('---------------------------------')
print('PROCEDURE ASSOCIADO CRIADA COM SUCESSO')
print('---------------------------------')
go

---------------------------------------------------
--PROCEDURE TIPO_PAGAMENTO
---------------------------------------------------
use dbotrabalho
go

create procedure tipo_pagamento_procedure
	@codigo bigint,
	@descricao   varchar(50),
	@numero_cartao varchar(20)
as
begin insert into tipo_do_pagamento values (@codigo, @descricao, @numero_cartao) end

print('---------------------------------')
print('PROCEDURE TIPO DO PG CRIADA COM SUCESSO')
print('---------------------------------')
go
---------------------------------------------------
--PROCEDURE ESTADOS
---------------------------------------------------

use dbotrabalho
go

create procedure estado_procedure

	@sigla varchar(2) ,
	@nome varchar(20),
	@pais_FK varchar(9)
as
begin insert into estado values ( @sigla, @nome, @pais_FK) end

print('---------------------------------')
print('PROCEDURE ESTADO CRIADA COM SUCESSO')
print('---------------------------------')
go
---------------------------------------------------
--PROCEDURE		CLIENTE
---------------------------------------------------

use dbotrabalho
go

create procedure cliente_procedure
	@codigo varchar(4),
	@nome varchar(40),
	@endereco varchar(50),
	@identidade varchar(20),
	@CEP_FK varchar(9),
	@estado_FK varchar(2),
	@pais_FK varchar(9) 
as 
begin insert into cliente values (@codigo, @nome, @endereco, @identidade, @CEP_FK, @estado_FK, @pais_FK) end

print('---------------------------------')
print('PROCEDURE CLIENTE CRIADA COM SUCESSO')
print('---------------------------------')
go
---------------------------------------------------
--PROCEDURE CARTAO
---------------------------------------------------

use dbotrabalho
go

create procedure cartao_procedure
	@numero varchar(20),
	@validade numeric(4) ,
	@cvv numeric(4) ,
	@adiminstradora varchar(20),
	@codigo_cliente varchar(4)
as

begin insert into cartao values(@numero, @validade, @cvv, @adiminstradora, @codigo_cliente) end

print('---------------------------------')
print('PROCEDURE CARTAO CRIADA COM SUCESSO')
print('---------------------------------')
go
---------------------------------------------------
--PROCEDURE CARRO
---------------------------------------------------
use dbotrabalho
go

create procedure carro_procedure

	@codigo_renavam bigint,
	@placa varchar(10),
	@categoria varchar(4),
	@codigo_associado bigint
as

begin insert into carro values(@codigo_renavam, @placa, @categoria, @codigo_associado) end
print('---------------------------------')
print('PROCEDURE CARRO CRIADA COM SUCESSO')
print('---------------------------------')
go
---------------------------------------------------
--PROCEDURE CORRIDA
---------------------------------------------------

	use dbotrabalho
	go

create procedure corrida_procedure
	@codigo bigint, --1
	@data_solicitacao date,--2
	@data_inicio date,--3
	@data_hora_fim varchar(20), --4
	@posicao_geografica__inicio numeric(4),--5
	@posicao_geografica__final numeric(4),--6
	@qtde_kms numeric(4),--7
	@valor_km numeric(4),--8
	@efetivada varchar(20),--9
	@codigo_tipo_pagamento_fk bigint,--10
	@codigo_associado_fk bigint,--11
	@codigo_renavam bigint,--12
	@codigo_cliente_fk varchar(4)--13
as

begin 
	insert into corrida values(
		@codigo,
		@data_solicitacao ,
		@data_inicio ,
		@data_hora_fim ,
		@posicao_geografica__inicio,
		@posicao_geografica__final,
		@qtde_kms,
		@valor_km,
		@efetivada,
		@codigo_tipo_pagamento_fk,
		@codigo_associado_fk,
		@codigo_renavam,
		@codigo_cliente_fk
	)
end

print('---------------------------------')
print('PROCEDURE CORRIDA CRIADA COM SUCESSO')
print('---------------------------------')
go 


---------------------------------------
--DELETE PROCEDURE
---------------------------------------------------
/*

use dbotrabalho
go

DROP PROCEDURE corrida_procedure 
DROP PROCEDURE carro_procedure
DROP PROCEDURE cartao_procedure 
DROP PROCEDURE cliente_procedure 
DROP PROCEDURE estado_procedure 
DROP PROCEDURE tipo_pagamento_procedure 
DROP PROCEDURE associado_procedure 
DROP PROCEDURE pais_Procedure 
DROP PROCEDURE CEP_Procedure    
go

*/

