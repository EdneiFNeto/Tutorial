
/*-------------------------------------------------------------------
	EXECUTE PROCEDRES TEMPO 7Min
--------------------------------------------------------------------*/

-----------------------------------------------------------------------
--CEP
-----------------------------------------------------------------------
use dbotrabalho
go

declare @n int =0;
declare @cep varchar(9)
declare @logradouro numeric(4)

while @n<10000
begin
	set @cep = CAST(ROUND(RAND()*100000, -1) AS VARCHAR(9)) /*+'-'+ CAST(ROUND(RAND()*100000, -1) AS VARCHAR(9))*/
	set @logradouro = ROUND(RAND()*1000, -1)
	if @cep <> 0
	begin
		if not exists (select cep from cep where cep = @cep)
		begin
			exec CEP_Procedure @cep, @logradouro
			set @n = @n + 1
			print('----------CEP--------------------')
			print(@n)
			print('------------------------------------')
		end
	end
end
go

--SELECT count(*) AS TOTOAL_CEP FROM CEP
--select * from CEP
--truncate table CEP


-----------------------------------------------------------------------
--PAIS
-----------------------------------------------------------------------

use dbotrabalho
go

declare @num int = 0
declare @sigla varchar(9)
declare @nome varchar(40)
while @num < 10
begin
	if @num = 0
	begin
		set @sigla = 'BR'
		set @nome  = 'BRASIL'
		exec pais_Procedure @sigla, @nome
	end
	
	if @num = 1
	begin
		set @sigla = 'ARG'
		set @nome  = 'ARGENTINA'
		exec pais_Procedure @sigla, @nome
	end

	if @num = 2
	begin
		set @sigla = 'MEX'
		set @nome  = 'MEXICO'
		exec pais_Procedure @sigla, @nome
	end
	
	if @num = 3
	begin
		set @sigla = 'BOL'
		set @nome  = 'BOLIVIA'
		exec pais_Procedure @sigla, @nome
	end

	if @num = 4
	begin
		set @sigla = 'ESP'
		set @nome  = 'ESPANHA'
		exec pais_Procedure @sigla, @nome
	end

	if @num = 5
	begin
		set @sigla = 'RUS'
		set @nome  = 'RUSSIA'
		exec pais_Procedure @sigla, @nome
	end

	if @num = 6
	begin
		set @sigla = 'ITA'
		set @nome  = 'ITALIA'
		exec pais_Procedure @sigla, @nome
	end

	if @num = 7
	begin
		set @sigla = 'NED'
		set @nome  = 'HOLANDA'
		exec pais_Procedure @sigla, @nome
	end

	if @num = 8
	begin
		set @sigla = 'NGR'
		set @nome  = 'NIGERIA'
		exec pais_Procedure @sigla, @nome
	end

	if @num = 9
	begin
		set @sigla = 'EUA'
		set @nome  = 'EST UNIDS'
		exec pais_Procedure @sigla, @nome
	end

	if @num = 10
	begin
		set @sigla = 'COL'
		set @nome  = 'COLOMBIA'
		exec pais_Procedure @sigla, @nome
	end
	set @num = @num + 1
	print('----------PAIS--------------------')
	print(@num)
	print('------------------------------------')
end
go


--SELECT count(*) AS TOTOAL_PAIS  FROM PAIS
--SELECT * FROM pais
--truncate table pais

-----------------------------------------------------------------------
--ASSOCIODO - Total de dados 100.000 - 1:38min
-----------------------------------------------------------------------

use dbotrabalho
go
declare @codigo bigint
declare @nome_ass varchar(50)
declare @data_associacao date
declare @carteira_motorista numeric(12)
declare @num int = 0
declare @dia varchar(2)
declare @mes varchar(2)
declare @ano varchar(4)

while @num < 10000
begin
	set @codigo					= floor(rand()*100000) -- 5 num max
	set @nome_ass				= 'ASSOCIADO '+ cast(round(rand()*1000, -1) as varchar(50))
	set @carteira_motorista		= round(rand()*10000000, -1)

	--VERIFICA SE EXISTE A CHAVE PRIMARIA
	if not exists(select codigo from associado  where codigo = @codigo)
	begin
		set @dia =  CONVERT(varchar(2), 12)--cast(floor(rand()*10+1) as varchar(2))
		set @mes =  CONVERT(varchar(2), 12)--cast(floor(rand()*10+1) as varchar(2))
		set @ano =  CONVERT(varchar(2), 12)--cast(floor(rand()*10+1) as varchar(4))
		set @data_associacao	= @dia+'-'+@mes+'-20'+@ano
		exec associado_procedure @codigo, @nome_ass, @data_associacao, @carteira_motorista
		set @num = @num +1
		
		print('----------ASSOCIADO--------')
		print(@num)
		print('---------------------------')
	end
end
go

--SELECT COUNT(*) AS TOTOAL_ASSOCIADO from associado
--SELECT * FROM associado
--truncate table associado


-----------------------------------------------------------------------
--ESTADO
-----------------------------------------------------------------------

use dbotrabalho
go
declare @num int = 0
declare @newid uniqueidentifier
declare @codigo varchar(4)
declare @sigla varchar(9)
declare @nome varchar(40)
declare @pais_FK varchar(9)

while @num <= 10
begin
	--set @newid	=  NEWID()
	--set @codigo		= CAST(CONVERT(varchar(255), @newid) as varchar(4))

		if @num = 0
		begin
			set @sigla = 'RJ'
			set @nome  = 'RIO DE JANEIRO'
			set @pais_FK = 'BR'
			exec estado_procedure /*@codigo,*/ @sigla, @nome, @pais_FK
		end

		if @num = 1
		begin
			set @sigla = 'BS'
			set @nome  = 'BOENOS AIRES'
			set @pais_FK = 'ARG'
			exec estado_procedure /*@codigo,*/ @sigla, @nome, @pais_FK
		end

		if @num = 2
		begin
			set @sigla = 'MDR'
			set @nome  = 'MADRI'
			set @pais_FK = 'ESP'
			exec estado_procedure /*@codigo,*/ @sigla, @nome, @pais_FK
		end

		if @num = 3
		begin
			set @sigla = 'NY'
			set @nome  = 'NOVA IORQUE'
			set @pais_FK = 'EUA'
			exec estado_procedure /*@codigo,*/ @sigla, @nome, @pais_FK
		end

		if @num = 4
		begin
			set @sigla = 'ML'
			set @nome  = 'MILAO'
			set @pais_FK = 'ITA'
			exec estado_procedure /*@codigo,*/ @sigla, @nome, @pais_FK
		end

		if @num = 5
		begin
			set @sigla = 'SAN'
			set @nome  = 'SANTA CRUZ'
			set @pais_FK = 'MEX'
			exec estado_procedure /*@codigo,*/ @sigla, @nome, @pais_FK
		end

		if @num = 6
		begin
			set @sigla = 'MST'
			set @nome  = 'MAASTRICH'
			set @pais_FK = 'NED'
			exec estado_procedure /*@codigo,*/ @sigla, @nome, @pais_FK
		end

		if @num = 7
		begin
			set @sigla = 'MOS'
			set @nome  = 'MOSCOU'
			set @pais_FK = 'RUS'
			exec estado_procedure /*@codigo,*/ @sigla, @nome, @pais_FK
		end
		
		if @num = 8
		begin
			set @sigla = 'ABJ'
			set @nome  = 'ABUJA'
			set @pais_FK = 'NGR'
			exec estado_procedure /*@codigo,*/ @sigla, @nome, @pais_FK
		end

		if @num = 9
		begin
			set @sigla = 'STA'
			set @nome  = 'SANTA CRUZ'
			set @pais_FK = 'BOL'
			exec estado_procedure /*@codigo,*/ @sigla, @nome, @pais_FK
		end

		if @num = 10
		begin
			set @sigla = 'SP'
			set @nome  = 'SAO PAULO'
			set @pais_FK = 'BR'
			exec estado_procedure /*@codigo,*/ @sigla, @nome, @pais_FK
		end
	set @num = @num + 1
	print('----------ESTADO--------------------')
	print(@num)
	print('------------------------------------')
end
go


--SELECT COUNT(*) AS TOTOAL_ESTADO FROM estado
--SELECT * FROM estado
--TRUNCATE TABLE ESTADO


-----------------------------------------------------------------------
--CLIENTE
-----------------------------------------------------------------------

use dbotrabalho
go

declare @num int = 0
declare @newid uniqueidentifier
declare @codigo varchar(4)
declare @nome varchar(40)
declare @endereco varchar(50)
declare @identidade varchar(20)
declare @CEP_FK varchar(9)
declare @estado_FK varchar(2)
declare @pais_FK varchar(9) 

while @num < 10000
begin
	set @newid				=  NEWID()
	set @codigo				=  CAST(CONVERT(varchar(255), @newid) as varchar(5))

	--verfica se nao existe o codigo existe na tabela cliente
	if not exists(select codigo from cliente  where codigo = @codigo)
	begin
		set @nome       = 'CLIENTE '+ cast(round(rand()*1000, -1) as varchar(40))
		set @endereco   = 'Rua '+ cast(round(rand()*100, -1) as varchar(50))
		set @identidade = cast(rand()*10000 as varchar(20))

		--cria o cep aleatorio
		set @CEP_FK     = CAST(ROUND(RAND()*100000, -1) AS VARCHAR(9))
		
		--verificar se existe cep na tabela
		--evitar conflito na chave estrangeira
		if exists (select CEP from CEP where CEP = @CEP_FK)
		begin
			if @num <= 1000
			begin
				set @estado_FK = 'RJ'
				set @pais_FK   = 'BR'
				exec cliente_procedure  @codigo, @nome, @endereco, @identidade, @CEP_FK, @estado_FK, @pais_FK
			end

			if @num > 1000 and @num <=3000
			begin
				set @estado_FK = 'sp'
				set @pais_FK   = 'BR'
				exec cliente_procedure  @codigo, @nome, @endereco, @identidade, @CEP_FK, @estado_FK, @pais_FK
			end

			if @num > 3000 and @num <= 4000
			begin
				set @estado_FK = 'AB'
				set @pais_FK   = 'NGR'
				exec cliente_procedure  @codigo, @nome, @endereco, @identidade, @CEP_FK, @estado_FK, @pais_FK
			end

			if @num > 4000 and @num <= 5000
			begin
				set @estado_FK = 'BS'
				set @pais_FK   = 'ARG'
				exec cliente_procedure  @codigo, @nome, @endereco, @identidade, @CEP_FK, @estado_FK, @pais_FK
			end

			if @num > 5000 and @num <= 6000
			begin
				set @estado_FK = 'MD'
				set @pais_FK   = 'ESP'
				exec cliente_procedure  @codigo, @nome, @endereco, @identidade, @CEP_FK, @estado_FK, @pais_FK
			end

			if @num > 6000 and @num <= 7000
			begin
				set @estado_FK = 'ML'
				set @pais_FK   = 'ITA'
				exec cliente_procedure  @codigo, @nome, @endereco, @identidade, @CEP_FK, @estado_FK, @pais_FK
			end

			if @num > 7000 and @num <= 8000
			begin
				set @estado_FK = 'MO'
				set @pais_FK   = 'RUS'
				exec cliente_procedure  @codigo, @nome, @endereco, @identidade, @CEP_FK, @estado_FK, @pais_FK
			end

			if @num > 8000 and @num <= 9000
			begin
				set @estado_FK = 'MS'
				set @pais_FK   = 'NED'
				exec cliente_procedure  @codigo, @nome, @endereco, @identidade, @CEP_FK, @estado_FK, @pais_FK
			end

			if @num > 9000 and @num <=10000
			begin
				set @estado_FK = 'NY'
				set @pais_FK   = 'EUA'
				exec cliente_procedure  @codigo, @nome, @endereco, @identidade, @CEP_FK, @estado_FK, @pais_FK
			end

			set @num = @num + 1 --CONTADOR
			print('----------CLIENTE--------------------')
			print(@num)
			print('------------------------------------')
		end -- FIM/ VERIFICA SE EXISTE CEP
	end--FIM/ VERIFICA SE EXIST CODIGO
end--FIM/ WHILE
go


--SELECT COUNT(*) AS TOTOAL_CLIENTE FROM CLIENTE
--SELECT * FROM cliente
--TRUNCATE TABLE cliente


-----------------------------------------------------------------------
--CARTAO
-----------------------------------------------------------------------

use dbotrabalho
go

declare @num int = 0
declare @newid    uniqueidentifier
declare @numero   varchar(20)
declare @validade numeric(4)
declare @cvv      numeric(4)
declare @adiminstradora varchar(20)
declare @codigo_cliente varchar(4)

while @num < 10000
begin
	set @newid				=  NEWID()
	set @numero				=  CAST(CONVERT(varchar(255), @newid) as varchar(4)) --CAMPO MODIFICADO

	--VERIFICA SE EXIST NUMERO DO CARTAO
	if not exists(select numero from cartao where numero = @numero)
	begin
		set @codigo_cliente = cast(convert(varchar(255), @newid) as varchar(4))

		--VERIFICAR SE EXISTE CODIGO DO CLIENTE
		--EVITAR CONFLITO  DE CHAVE ESTRANGEIRA
		if exists (select codigo from cliente where codigo = @codigo_cliente)
		begin
			if @num <= 1000
			begin
				set @validade = 2011
				set @cvv      = round(rand()*100, -1)
				set @adiminstradora = 'VISA'
				exec cartao_procedure @numero, @validade, @cvv, @adiminstradora, @codigo_cliente
			end
			
			if @num > 1000 and @num <= 3000
			begin
				set @validade = 2012
				set @cvv      = round(rand()*100, -1)
				set @adiminstradora = 'MASTER CARD'
				exec cartao_procedure @numero, @validade, @cvv, @adiminstradora, @codigo_cliente
			end

			if @num > 3000 and @num <= 6000
			begin
				set @validade = 2013
				set @cvv      = round(rand()*100, -1)
				set @adiminstradora = 'GOLD'
				exec cartao_procedure @numero, @validade, @cvv, @adiminstradora, @codigo_cliente
			end

			if @num > 6000 
			begin
				set @validade = 2015
				set @cvv      = round(rand()*100, -1)
				set @adiminstradora = 'MASTER CARD X '
				exec cartao_procedure @numero, @validade, @cvv, @adiminstradora, @codigo_cliente
			end

			--EVITA LOOP INFINTO
			set @num = @num +1
			print('----------CARTAO--------------------')
			print(@num)
			print('------------------------------------')
		end
	end
end
go


--SELECT COUNT(*) AS TOTOAL_CARTAO FROM CARTAO
--SELECT * FROM CARTAO
--TRUNCATE TABLE CARTAO


-----------------------------------------------------------------------
--TIPO DE PAGAMENTO * - Total de dados 100.000
-----------------------------------------------------------------------

use dbotrabalho
go
declare @newid uniqueidentifier
declare @codigo bigint
declare @descricao varchar(10)
declare @numero_cartao varchar(20)
declare @num int = 0

while @num < 10000
begin
	set @newid				= NEWID()
	set @codigo				= floor(rand()*100000) -- 5 num max
	

	--VERIFICA SE EXISTE O CODIGO NA TABELA
	if not exists(select codigo from tipo_do_pagamento  where codigo = @codigo)
	begin	
		--VERIFICA SE EXISTE CODIGO ASSOCIADO
		if exists(select codigo from  associado where codigo = @codigo)
		begin
			set @numero_cartao		= CAST(CONVERT(varchar(255), @newid) as varchar(4)) --CAMPO MODIFICADO
		--VERIFICA SE EXISTE NUMERO DO CARTAO NA TABELA CARTAO
			if exists (select numero from cartao where numero = @numero_cartao)
			begin
				--QUANDO FOR PAR INSERE DEBITO
				if @num % 2 = 0 
					set @descricao = 'DEBITO'
				else
					set @descricao = 'CREDITO'

				exec tipo_pagamento_procedure  @codigo, @descricao, @numero_cartao
				set @num = @num +1
				print('----------TIPO DE PAGAMENTO--------')
				print(@num)
				print('------------------------------------')
			end
		end
	end
end
go

--SELECT COUNT(*) AS TOTOAL_TIPO_PG FROM tipo_do_pagamento
--SELECT * FROM	tipo_do_pagamento
--truncate table tipo_do_pagamento

-----------------------------------------------------------------------
--CARRO
-----------------------------------------------------------------------

use dbotrabalho
go
declare @num			  int = 0
declare @codigo_renavam	  bigint
declare @placa			  varchar(10)
declare @categoria		  varchar(4)
declare @codigo_associado bigint

while @num < 10000
begin
	set @codigo_renavam		=  floor(rand()*100000) -- 5 num max

	--VERIFICA DE EXIST CODIGO NA TANELA
	if not exists(select codigo_renavam from carro where codigo_renavam = @codigo_renavam)
	begin
		--CRIA O CODIGO ASSOSIADO ALEATORIO
		set @codigo_associado = floor(rand()*100000)

		--VERIFICA SE O CODIGO GERADO ALEATORIAMENTE EXISTE NA TABELA ASSOCIADO
		if exists (select  codigo from associado where codigo = @codigo_associado)
		begin
			--set @placa = cast(convert(varchar(255), @newid) as varchar(4))+'1252'
			set @placa = cast(convert(varchar(255), newid()) as varchar(4)) +' '+ convert(varchar(255), floor(rand()*10000))
			if @num <=1000
			begin	
				set @categoria = 'A'
				exec carro_procedure @codigo_renavam, @placa, @categoria, @codigo_associado
			end

			if @num > 1000 and @num <= 4000
			begin
				set @categoria = 'B'
				exec carro_procedure @codigo_renavam, @placa, @categoria, @codigo_associado
			end

			if @num > 4000
			begin
				set @categoria = 'C'
				exec carro_procedure @codigo_renavam, @placa, @categoria, @codigo_associado
			end

			--EVITA LOOP INFINITO
			set @num = @num + 1
			print('---------- CARRO--------------------')
			print(@num)
			print('------------------------------------')
		end 
	end
end
go

--SELECT COUNT(*) AS TOTAL_CARRO FROM CARRO
--SELECT * FROM CARRO
--TRUNCATE TABLE carro

-----------------------------------------------------------------------
--CORRIDA 
-----------------------------------------------------------------------
use dbotrabalho
go

declare @num    int = 0
declare @codigo bigint
declare @data_solicitacao date
declare @data_inicio date
declare @data_hora_fim varchar(20)
declare @posicao_geografica__inicio numeric(4)
declare @posicao_geografica__final numeric(4)
declare @qtde_kms numeric(4)
declare @valor_km numeric(4)
declare @efetivada varchar(20)
declare @codigo_cliente_fk varchar(4)
declare @newid uniqueidentifier

--DEFINE A CHAVE ESTRANGEIRA PARA OS CAMPOS
declare @fk bigint

/*
	REALIZA CONSULTA PARA VERIFICAR TOTAL DE CHAVES ESTRANGEIRAS NAS TABELA:
	-ASSOCIADO, -TIPO_DO_PAGAMETO, -CARRO
*/

declare @numQuery  int

/*
CONSULTA ANTIGA
set @numQuery = (SELECT COUNT(*) FROM associado, tipo_do_pagamento, carro where associado.codigo = tipo_do_pagamento.codigo and tipo_do_pagamento.codigo = carro.codigo_renavam)
*/

/*Nova consulta */

set @numQuery = (select COUNT(*) from tipo_do_pagamento, associado, carro where tipo_do_pagamento.codigo = associado.codigo and carro.codigo_associado = associado.codigo)


while @num < @numQuery
begin
	set @codigo	= floor(rand()*100000)
	
	--VERIFICAR SE EXISTE CODIGO NA TABELA
	if not exists(select codigo from corrida where codigo = @codigo)
	begin
		--GERA A CHAVE ESTRANGEIRA ALEATORIO
		set @fk  = floor(rand()*100000)

		--VERIFICAR SE EXISTE CHAVE ESTRANGEIRA ENTRE AS TABELAS
		if exists( 
			SELECT associado.codigo, tipo_do_pagamento.codigo, carro.codigo_renavam FROM associado, tipo_do_pagamento, carro
			where associado.codigo = tipo_do_pagamento.codigo and tipo_do_pagamento.codigo = carro.codigo_renavam
			and associado.codigo = @fk)
		begin

			set @newid			= NEWID()
			set @codigo_cliente_fk = CAST(CONVERT(varchar(255), @newid) as varchar(4))

			--VERIFICAR SE EXISTE CODIGO CLEINTE 
			if exists(select codigo from cliente where codigo = @codigo_cliente_fk)
			begin
				set @data_solicitacao	= convert(varchar(2),floor(rand()*10+1))+'-'+convert(varchar(2),floor(rand()*10+1))+'-20'+convert(varchar(2),floor(rand()*10+12))
				set @data_inicio		= convert(varchar(2),floor(rand()*10+1))+'-'+convert(varchar(2),floor(rand()*10+1))+'-20'+convert(varchar(2),floor(rand()*10+12))
				set @data_hora_fim		= convert(varchar(2),floor(rand()*10+1))+'-'+convert(varchar(2),floor(rand()*10+1))+'-20'+convert(varchar(2),floor(rand()*10+12))+
											' '+  convert(varchar(2),floor(rand()*10+1))+':'+convert(varchar(2),floor(rand()*10+1))+':'+convert(varchar(2),floor(rand()*10+1))
				set @posicao_geografica__inicio = floor(rand()*10+2)
				set @posicao_geografica__final	= floor(rand()*10+2)
				set @qtde_kms					= ROUND(RAND()*1000, -1)
				set @valor_km					= ROUND(RAND()*1000, -1)

				if @num % 2=0
					set @efetivada	= 'SIM'
				else
					set @efetivada  = 'NAO'

				exec corrida_procedure 
						@codigo, 
						@data_solicitacao, 
						@data_inicio , 
						@data_hora_fim , 
						@posicao_geografica__inicio,
						@posicao_geografica__final, 
						@qtde_kms,
						@valor_km, 
						@efetivada, 
						@fk, @fk, @fk, 
						@codigo_cliente_fk
					
				--EVITA LOOP INFINITO
				set @num = @num + 1
				print('---------- CORRIDA--------------------')
				print(@num)
				print('------------------------------------')
			end--FIM / VERIFICA CHAVE ESTRANGEIRA
		end--FIM/ IF CODIGO DENTRO DA TABELA CORRIDA
	end
end --FIM/ WHILE
go

--SELECT COUNT(*) FROM CORRIDA
select * from corrida
-----------------------------------------------------------------------------------------------------------
--FIM
-----------------------------------------------------------------------------------------------------------
