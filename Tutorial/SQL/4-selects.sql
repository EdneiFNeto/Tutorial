


-------------------------------------------------------------------------------------
--A. Listar todos clientes que fizeram corridas no período de um mês
-------------------------------------------------------------------------------------
use dbotrabalho
go
select 
	cliente.nome,
	cartao.numero,
	tipo_do_pagamento.descricao,
	corrida.data_inicio
from cliente inner join cartao
on cliente.codigo = cartao.codigo_cliente
inner join tipo_do_pagamento
on  tipo_do_pagamento.numero_cartao = cartao.numero
inner join corrida 
on tipo_do_pagamento.codigo = corrida.codigo_tipo_pagamento_fk
where MONTH(corrida.data_inicio) = '02' and YEAR(corrida.data_inicio)='2017';
go


-------------------------------------------------------------------------------------
--B.Listar todos os clientes, carros, associados e corridas, tipo de pagamento desde o início do funcionamento da companhia
-------------------------------------------------------------------------------------
use dbotrabalho
go
set statistics time on;
go
select distinct 
	a.nome,
	b.codigo_renavam,
	c.nome,
	d.codigo,
	d.codigo_tipo_pagamento_fk 
from cliente a ,carro b ,associado c ,corrida d,tipo_do_pagamento e, cartao f
where e.codigo = d.codigo_tipo_pagamento_fk 
and d.codigo_cliente_fk  = a.codigo
and d.codigo_associado_fk = c.codigo 
and b.codigo_renavam = d.codigo_renavam and
d.data_solicitacao = (select TOP 1 data_solicitacao from corrida order by data_solicitacao asc)
go
set statistics time off;
go

-------------------------------------------------------------------------------------
--C. Listar todas corridas não finalizadas e os associados que executaram essa corrida
------------------------------------------------------------------------------------- 
use dbotrabalho
go
set statistics time on;
go
select 
	a.codigo,
	a.codigo_associado_fk 
from corrida a ,associado b 
where a.codigo_associado_fk = b.codigo and data_hora_fim = null
go
set statistics time off;
go

-------------------------------------------------------------------------------------
--D. Listar em ordem decrescente todos os estados com maior quantidade de corridas realizadas
-------------------------------------------------------------------------------------
use dbotrabalho
go
set statistics time on;
go
select distinct 
	a.nome 
from estado a,corrida b,cliente c 
where b.codigo_cliente_fk=c.codigo 
and c.estado_FK= a.sigla order by a.nome desc
go
set statistics time off;
go
-------------------------------------------------------------------------------------
--F. Listar os carros com maior quantidade de kms percorridos em corridas efetuadas
-------------------------------------------------------------------------------------
use dbotrabalho
go
select   
	SUM(a.qtde_kms) as TotalKM,
	a.codigo_renavam,
b.placa from corrida a,carro b
where a.codigo_renavam = b.codigo_renavam 
group by a.codigo_renavam,b.placa
go

-------------------------------------------------------------------------------------
--G. Listar os associados, indicando o estado e o país, que tiveram a maior receita no período de um ano
-------------------------------------------------------------------------------------
use dbotrabalho
go
select  
	a.codigo,
	a.nome,
	c.estado_FK,
	c.pais_FK 
from associado a,corrida b,cliente c
where a.codigo = b.codigo_associado_fk 
and b.codigo_cliente_fk = c.codigo 
and data_hora_fim<= getdate()
and data_hora_fim>=(select dateadd(YEAR, -1, getdate()))
order by (qtde_kms * valor_km) desc
go

-------------------------------------------------------------------------------------	
--H. Listar os associados, indicando o estado e o país, que tiveram a menor receita no período de um ano
-------------------------------------------------------------------------------------
use dbotrabalho
go
select  
	a.codigo,
	a.nome,
	c.estado_FK,
	c.pais_FK 
from associado a,corrida b,cliente c
where a.codigo = b.codigo_associado_fk and b.codigo_cliente_fk = c.codigo 
and data_hora_fim<= getdate()
and data_hora_fim>=(select dateadd(YEAR, -1, getdate()))
order by (qtde_kms * valor_km) asc
go

-------------------------------------------------------------------------------------
--I. Listar os valores pagos em cada tipo de pagamento em todas as corridas	
-------------------------------------------------------------------------------------
use dbotrabalho
go
select  
	b.descricao,
	SUM(a.qtde_kms * a.valor_km)'Valor Pago' 
from corrida a,tipo_do_pagamento b
where a.codigo_tipo_pagamento_fk = b.codigo
group by b.descricao
go
-------------------------------------------------------------------------------------
--J. Listar o país que fez a maior quantidade de pagamentos em um tipo de pagamento que não seja cartão de crédito
-------------------------------------------------------------------------------------	
use dbotrabalho
go
select 
	a.nome,
	COUNT(*) 
from pais a,corrida b,cliente c,tipo_do_pagamento d
where a.sigla = c.pais_FK 
and b.codigo_cliente_fk = c.codigo 
and d.codigo = b.codigo_tipo_pagamento_fk 
and d.descricao <>'CREDITO'
group by a.nome
go


-------------------------------------------------------------------------------------
--K. Listar a corrida que demorou a maior quantidade de tempo para ser finalizada
-------------------------------------------------------------------------------------
use dbotrabalho
go
select TOP 1 * from corrida order by data_hora_fim desc
go

-------------------------------------------------------------------------------------------
--L. Listar o faturamento bruto da organização em cada categoria de carro (X, Special,Luxe)
-------------------------------------------------------------------------------------------
use dbotrabalho
go
select 
	a.categoria,
	SUM(qtde_kms * valor_km) as Faturamento 
from carro a,corrida b 
where a.codigo_renavam = b.codigo_renavam
group by a.categoria
go

-------------------------------------------------------------------------------------
--M. Listar os associados (5 primeiros) que mais usaram cada tipo de categoria
-------------------------------------------------------------------------------------
use dbotrabalho
go
-- Categoria A
select  
	b.categoria,
	a.codigo,
	a.nome,
	SUM(a.codigo)'soma' 
from associado a,carro b
where a.codigo = b.codigo_associado --and b.categoria='A'
group by a.nome,a.codigo, b.categoria
order by soma desc


-- Categoria B
select  TOP 5 
	a.codigo,
	a.nome,
	SUM(a.codigo)'soma' from associado a,carro b
where a.codigo = b.codigo_associado and b.categoria='B'
group by a.nome,a.codigo
order by soma desc


-- Categoria C
select  TOP 5 
	a.codigo,
	a.nome,
	SUM(a.codigo)'soma' 
from associado a,carro b
where a.codigo = b.codigo_associado and b.categoria='C'
group by a.nome,a.codigo
order by soma desc
go

-------------------------------------------------------------------------------------
--N. Listar as 5 corridas que demoraram a maior quantidade de tempo para ser iniciada
-------------------------------------------------------------------------------------
use dbotrabalho
go
select  
	TOP 5
	codigo,
	data_solicitacao,
	data_inicio,
	DATEDIFF(DAY,data_solicitacao,data_inicio ) as DiferencaDias 
from corrida 
order by DiferencaDias desc
go


-------------------------------------------------------------------------------------
--O. Listar as 5 corridas com o maior percentual de lucro para a companhia
-- o associado ganha 0.20 e a empresa 0.80,já é retornado o valor que a empresa ganhou na corrida,
--sem a parte do associado
-------------------------------------------------------------------------------------
use dbotrabalho
go
select TOP 5 
	codigo,
	((qtde_kms * valor_km) *0.80)'Percentual Empresa' 
from corrida 
order by [Percentual Empresa] desc 
go

-------------------------------------------------------------------------------------
--P. Listar a maior distância percorrida em uma corrida retornando o cliente, carro e
--associado da corrida
-------------------------------------------------------------------------------------
use dbotrabalho
go	
select  TOP 1 
	codigo_cliente_fk,
	codigo_renavam,
	codigo_associado_fk,
	qtde_kms 
from corrida
order by qtde_kms desc
go

-------------------------------------------------
--1 UPDATE COLUMN CARRO
---------------------------------------------------

alter table carro  alter column categoria varchar(20);
update carro set categoria = 'X' where categoria = 'A';
update carro set categoria = 'Special' where categoria = 'B';
update carro set categoria = 'Luxe' where categoria = 'C'; 