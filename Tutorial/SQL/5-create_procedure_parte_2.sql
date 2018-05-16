

use dbotrabalho
go	
DROP PROCEDURE pagamento_procedute
go 

use dbotrabalho
go	
--CREATE PAGAMENTO PROCEDURE
create procedure pagamento_procedute
	--Transacao 1
	@codigo_cliente varchar(4), --1
	@nome_cliente varchar(20),	--2
	@endereco varchar(50),		--3
	@identidade varchar(20),	--4
	@CEP_FK varchar(9),			--5
	@estado_FK varchar(2),		--6
	@pais_FK varchar(9),		--7

	--tabela cartao
	@numero_cartao varchar(20),
	@codigo_tipo_pagamento bigint,
	@validade numeric(4)
	--8
as
begin

	--VERIFICAR SE XISTE CLIENTE
	if exists (select codigo from cliente where codigo = @codigo_cliente)
	begin
		print('Cliente já cadastrado, insira um novo cliente. :-(');
	end

	else
	begin
		--inicia a 1º TRANSACAO
		begin transaction trans1;
			begin
				--inica a trancasao
				insert into cliente values (@codigo_cliente,@nome_cliente, @endereco, @identidade, @CEP_FK, @estado_FK, @pais_FK) 
			end
			
			if @@ERROR = 0
			begin
				print('Cliente '+@codigo_cliente+ ' cadastrado com sucesso.');
				commit transaction trans1;

				----------------------------------------------------------------------------
				--2º Transação
				----------------------------------------------------------------------------
				
				begin transaction trans2;
					
					--inseir cartao do cliente
					if not exists(select numero from cartao where numero = @numero_cartao)
					begin

						--SAVE POINT 
						save transaction save_point_cartao;
						--Cadastra numero do cartao do cliente	
						insert into cartao values(@numero_cartao, @validade, 33,'MATER', @codigo_cliente)
						
						--verificar se existe numero do cartao
						if exists(select numero from cartao where codigo_cliente = @codigo_cliente)
						begin
							--verifica A VALIDADE do cartao
							IF ((SELECT validade FROM cartao WHERE codigo_cliente = @codigo_cliente) < year(GETDATE()))
							begin
								print('Cartao expirou, entre em contato com a ADM do cartao.')
								rollback transaction trans2
								rollback transaction save_point_cartao;
							end

							else
							begin
								print('Cartao com Nº '+@numero_cartao+' está com a validade em dia.')
								commit transaction trans2
								----------------------------------------------------------------------------
								--3º Transação
								----------------------------------------------------------------------------
								begin transaction trans3
									
									declare @codigo_corrida bigint		
									declare @data_solicitacao date		
									declare @hora_inicio datetime		
									declare @hora_fim datetime			
									declare @posicao_geografica__inicio numeric(4,0) 
									declare @posicao_geografica__final numeric(4,0)  
									declare @qtde_kms numeric(4,0) 	
									declare @valor_kms numeric(4,0)	
									declare @efetivada varchar(20)	

									declare @fk bigint
									declare @comissao numeric(8,2)

									set @codigo_corrida 				= @codigo_tipo_pagamento
									set @data_solicitacao 				= '2017-11-21'
									set @hora_inicio 					= '09:00:00'
									set @hora_fim 						= '09:20:00'
									set @posicao_geografica__inicio 	= 1234
									set @posicao_geografica__final 		= 5678
									set @qtde_kms 						= 32
									set @valor_kms 						= 5
									set @efetivada						= 'SIM'
									set @fk    							= @codigo_tipo_pagamento
									
									SET @comissao = (@qtde_kms * @valor_kms) * 0.20

									--verifica se existe codigo no tipode pagamento
									if not exists(select codigo from tipo_do_pagamento where codigo = @codigo_tipo_pagamento)
									begin
										--verificar se existe numero do cartao na tabela de catao
										if exists (select numero from cartao where numero = @numero_cartao)
										begin

											--SAVE POINT
											save transaction save_point_tipo_pg;
											--cadastro o tipo de pagamento
											insert into tipo_do_pagamento values(@codigo_tipo_pagamento, 'CREDITO', @numero_cartao)

											print('O pagamento foi  aprovado  com sucesso.')
											
											declare @codigo_associado bigint
											declare @codigo_renavam bigint	

											set @codigo_associado	 = (select top 1 codigo from  associado)
											set @codigo_renavam		 = (select top 1 codigo_renavam from  carro)

											--SAVE POINT
											save transaction save_point_corrida;
											--cadastra a corrida
											INSERT INTO corrida 
										    VALUES (
										    	@codigo_corrida, 
										    	@data_solicitacao , 
										    	@hora_inicio , 
										    	@hora_fim , 
										    	@posicao_geografica__inicio, 
										    	@posicao_geografica__final, 
										    	@qtde_kms,
												@valor_kms, 
										    	@efetivada,
										    	@codigo_tipo_pagamento, 
										    	@codigo_associado, 
										    	@codigo_renavam,
										    	@codigo_cliente
										    )		
										end

										if @@ERROR = 0
										begin
											print('Corrida cadastra com sucesso.')
											print( 'O associado  '+CAST(@codigo_tipo_pagamento AS VARCHAR)+' recebeu a comissão de R$'+CAST(@comissao AS VARCHAR)+ '.')
											COMMIT transaction trans3;
										end--fim commit transaction - 3

										else
										begin
											print('Nao existe numero do cartao.')
											rollback transaction trans3;
											rollback Transaction save_point_tipo_pg;
											rollback transaction save_point_corrida;
										end
									end

									else
									begin
										print('Erro: Chave primaria tipo_do_pagamento duplicada.')
										rollback transaction trans3;
										rollback Transaction save_point_tipo_pg;
										rollback transaction save_point_corrida;
									end
								--FIM Transaction - 3
							end--fim commit transcation -2
						end--fim if valida cartao
					end

					else
					begin
						print('Numero do cartao já existe, insira outro numero do cartao.')
						rollback transaction trans2
					end
				--FIM TRANSACTION - 2
			end--fim commit transaction - 1

			else
			begin
				print('Erro: Nao foi possivel cadastrar cliente.');
				rollback transaction trans1;
				rollback transaction save_point_cartao;
			end
		--FIM TRANSACTION - 1
	end--fim  else principal
end
go

