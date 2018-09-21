//=======================================================
//==================================================================================
//									GIT
//==================================================================================

//==================================================================================
//1- inicio
//==================================================================================
//CONFIG
//git  config --global user.name "Ednei Freitas"

//==================================================================================
//EMAIL
//git config --global user.email "edneifneto@gmail.com"
//==================================================================================

//==================================================================================
//CRIAR REPOSITORIO
//git init
//==================================================================================

//==================================================================================
//STATUS 
//git status
//==================================================================================

//==================================================================================
//ADD PROJETOS
//git add nome do arquivo
//*.txt ou .[add todos os arquivos]
//apos add  digite  para preparar os gits para serem comitados git status
//==================================================================================

//==================================================================================
//ENVIAR PARA O REPOSITORIO DO GITHUB
//git commit -m "menssagem para saber o tipo da alteracao" 
//==================================================================================

//OBS: QUALQUER MUDANÇA QUE ACONTECER NO PROJETO AO VERIFICAR OS STATUS VAI SER MOSTRADO

//==================================================================================
//VERIFICAR LINHAS ALTERADOS 
//git diff
//OBS: aparece as linhas que foram adionadas e removidas
//==================================================================================

//==================================================================================
//LOG DOS COMMITS
//==================================================================================
//git log
//git log -p [ordena os logs]
//git log -p -1 [ordena os  e os numeros de commits no logs]
//DENTRO DO LOG APARECE AS CHAVES DE CONTENDO AS VERSOES DO ARQUIVO COMITADO
//NOME DO USUARIO & EMIAL
//==================================================================================


//==================================================================================
//ENVIAR  PROJETO PARA  GIT
//==================================================================================
//CRIAR UM PROJETO NO GIT
//==================================================================================
//DENTRO DA PASTA DO PROJETO
//==================================================================================
//$ git remote add origin https://github.com/EdneiFNeto/citacoes.git
//==================================================================================
//$ git push origin master
//==================================================================================
//SE TUDO OCORRER BEM VAI APARECER UM PAINEL DO GIT PARA SER 	
//so clicar na pasta do projeto e estara aparecendo tudo la
//==================================================================================

//==================================================================================
//CLONAR  PROJETO PARA  GIT
//==================================================================================
//git clone https://github.com/fulanodasilva/citacoes.git
//==================================================================================
//enviar --> git push origin master
//==================================================================================


//==================================================================================
//CONTROLAR VERSAO
//==================================================================================
//ENTRAR NA PASTA CONDE ESTA P PROJETO E CRIAR O DIRETORIO
//==================================================================================
//git init
//==================================================================================
//git status [VERIFICAR STATUS]
//==================================================================================
//git add index.html [ADD PARA RELIZAR COMMIT]
//==================================================================================
//$ git add . [add todos as arquivos para envio]
//==================================================================================
//git commit -m "Cmmit inicial" [apos add os arquivo relizar o commit]
//==================================================================================
//Verificando mudanças ainda não rastreadas
//Se quisermos revisar a modificação efetuada, verificando as diferenças entre
//o arquivo alterado e o que foi comitado anteriormente, podemos usar o
//==================================================================================
//comando: $ git diff
//==================================================================================
//É possível mostrar as diferenças entre os arquivos na área de stage e a
//última versão que foi comitada utilizando a opção --staged:
//$ git diff --staged
//==================================================================================
//Desfazendo mudanças
//==================================================================================
//git checkout -- index.html
//==================================================================================
//E se apagarmos algum arquivo sem querer? Medo! Desespero!
//Vamos dizer que apagamos o arquivo index.html.
//Ao executarmos o comando git status, teríamos:
//==================================================================================
//Comando: git checkout -- index.html
//==================================================================================
//Repositório remoto
//==================================================================================
//git init --bare moveis-ecologicos.git



//=============================================================================================
//TUTORIAL GIT
//=============================================================================================
//GITIGNORE
//CRIAR UM ARQUIVO .gitingore inserir dentro do 
//arquivo os arquivos a serem ignorados
//ex: nome_arquivo.txt
//=============================================================================================
//commit e add os dados ao mesmo tempo
$ git commit -a -m "Inteirindo titulo"
//=============================================================================================
//LOG dos Commits
$ git log
//=============================================================================================
//Se quisermos mostrar apenas os dois últimos commits devemos utilizar a opção -n:
$ git log -n 2
//=============================================================================================
//Se quisermos um resumo bem conciso dos commits do nosso projeto
$ git log --oneline
//=============================================================================================
//Podemos mostrar um resumo dos arquivos alterados, com o número de 
//linhas adicionadas e removidas.
$ git log --stat

//=============================================================================================
//Para sairmos do resultados do git log ou diff, devemos apertar a tecla 'Q'
//=============================================================================================
//Verificando mudanças ainda não rastreadas
//Se quisermos revisar a modificação efetuada, verificando as diferenças entre
//o arquivo alterado e o que foi comitado anteriormente, podemos usar o comando:
$ git diff
//=============================================================================================
//É possível mostrar as diferenças entre os arquivos na área de stage e a
//última versão que foi comitada utilizando a opção:
//primeiro precisa add o arquivo ao estaio:
//ex: git add index.html
//logo apos executar o diff --staged
$ git diff --staged
//=============================================================================================
//Verificando mudanças rastreadas e não rastreadas ao mesmo tempo
//verificar o codigo do ultimo commit
//faz a alteracao --> git status
$ git log -n 1 --oneline
//vai aparecer um id
//$ git diff id_do_log
//=============================================================================================
//Verificando mudanças já comitadas
//=============================================================================================
//1- add e commitar os arquivos: git commit -a -m "texto"
//Então, vamos mostrar os três últimos commits de maneira concisa através
//do comando
$ git log -n 3 --oneline.
//vai aparecer o ID
$ git diff ID ~ 2 // número depois do ~ indica quantos
//=============================================================================================
//número depois do ~ indica quantos
//=============================================================================================
//Removendo arquivos do repositório
//=============================================================================================
//apos fazer o commit :
//1- git add produto.html
//2- git commit -m "produto"
//3- git rm produto.html
//=============================================================================================
//Renomeando e movendo arquivos
//=============================================================================================
$ git mv estilos.css principal.css
//=============================================================================================
// Desfazendo mudanças
//=============================================================================================
//git chechout -- nome do arquivo[Nao pode fazer o commit]
//=============================================================================================
//Desfazendo mudanças já rastreadas[cimmitadas]
//1- git status
//2- git add nome_arquvo
//3- git commit -m "commit"
//4- git log -n -2 --oneline [ultimos dois commit]
//5- git reset --hard id_gerado_log
//=============================================================================================
// git log --oneline [ pega todos os logs]
$ git reset --hard f4bd2e8//[id_log]

//=============================================================================================
//REPOSITORIO REMOTO
//=============================================================================================
 git init --bare nome_repo.git
//=============================================================================================
//Adicionando o repositório remoto
//=============================================================================================
/*
	Agora que o repositório remoto já foi criado no servidor, é possível enviar os
commits efetuados no nosso repositório local para o repositório remoto. Mas
antes disso precisamos, de alguma maneira, indicar ao Git onde está localizado
o repositório remoto.
*/

















