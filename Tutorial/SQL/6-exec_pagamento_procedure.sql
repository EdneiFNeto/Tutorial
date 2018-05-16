------------------------------------------------
--EXECUITA
-----------------------------------------------
use dbotrabalho
go
	
declare @codigo_cliente_ varchar(4)
declare @nome_cliente_  varchar(20)
declare @endereco_ varchar(50)
declare @identidade_ varchar(20)
declare @CEP_FK_ varchar(9)
declare @estado_FK_ varchar(2)
declare @pais_FK_ varchar(9)
declare @codigo_tipo_pagamento_ bigint
declare @numero_cartao_ varchar(20)
declare @validade_ numeric(4)

set @codigo_cliente_	= '%)++w+*40'+(select top 1 codigo from cliente order by codigo desc)
set @nome_cliente_		= 'EDNEI'
set @endereco_ 			= 'RUA X'
set @identidade_ 		= 'Ednei'
set @CEP_FK_ 			= '100'
set @estado_FK_ 		= 'RJ'
set @pais_FK_ 			= 'BR'
set @numero_cartao_     = '_{++%+)-w'+(select top 1 numero from cartao order by numero desc)
set @codigo_tipo_pagamento_  = (select top 1 codigo from tipo_do_pagamento order by codigo desc)+1 --Evitar conflito de chave primaria
set @validade_		= 2015

exec pagamento_procedute @codigo_cliente_, @nome_cliente_, @endereco_, 
	@identidade_, @CEP_FK_, @estado_FK_, @pais_FK_,
	@numero_cartao_, @codigo_tipo_pagamento_, @validade_	
--select top 5 codigo from cliente  order by codigo asc
go