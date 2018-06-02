/*******************************************************************************************************************************
	JAVA
*******************************************************************************************************************************/

//=============================================================================================================================
//LIVRO JSF & JPA
//=============================================================================================================================
//HIBERNATE
//=============================================================================================================================
//create-drop - as tabelas são criadas pelo próprio Hibernate e excluídas somente
//no final da execução do programa caso solicitado pelo desenvolvedor;
//=============================================================================================================================
//update - nada é excluído, apenas criado, ou seja, todos os dados são mantidos.
//Útil para aplicações que estão em produção, das quais nada pode ser apagado;
//=============================================================================================================================
//validate - não cria e nem exclui nada, apenas verifica se as entidades estão
//de acordo com as tabelas e, caso não esteja, uma exceção é lançada.
//=============================================================================================================================
//HOBERNATE IMPRIME NO CONSOLE OS COMANDOS EXECUTADOS
//=============================================================================================================================
<property name="hibernate.show_sql" value="true" />
<property name="hibernate.format_sql" value="true" />
//=============================================================================================================================
//CRIAR PASTA DENTRO SRC META-INF 
//CONFIGURACOES DE HIBERNATE
//=============================================================================================================================

<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.0"
	xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/persistence
	http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">

	<persistence-unit name="default">
		<properties>
			<property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/automovel" />
			<property name="javax.persistence.jdbc.user" value="root" />
			<property name="javax.persistence.jdbc.password" value="root" />

			<!-- Dirver -->
			<property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver" />

			<!-- Dialect para usar Banco Mysql -->
			<!--  property name="hibernate.dialect" value="org.hibernate.dialect.MySQLInnoDBDialect" /-->

			<!-- HBM2DLL -->
			<property name="hibernate.hbm2ddl.auto" value="update" />

			<!-- IMPRIME NO CONSOLE -->
			<property name="hibernate.show_sql" value="true" />
			<property name="hibernate.format_sql" value="true" />
			
		</properties>
	</persistence-unit>
</persistence>

//=============================================================================================================================
//CRIAR CONEXAO
//=============================================================================================================================

package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("livraria-pu");
	
	public static EntityManager getEntityManager(){
		return emf.createEntityManager();
	}
}

//=============================================================================================================================
//EXECUTR NO MAIN
//=============================================================================================================================
package teste;
import javax.persistence.EntityManager;
import model.Editora;
import util.JPAUtil;

public class Main {

	public static void main(String[] args) {
		EntityManager em = JPAUtil.getEntityManager();
		Editora ed = new Editora();
		ed.setNome("XX");
		em.getTransaction().begin();
		em.persist(ed);
		em.getTransaction().commit();
		em.close();
		JPAUtil.getEntityManager().close();
	}

}
//=============================================================================================================================
//lLISTAR DADOS
//=============================================================================================================================
package teste;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Editora;
import util.JPAUtil;

public class ListarEditora {

	public static void main(String[] args) {

		EntityManager em = JPAUtil.getEntityManager();
		Query query = em.createQuery("SELECT e FROM Editora e");
		List<Editora> editoras = query.getResultList();
		for(Editora a : editoras)
			System.out.println(a.getId()+"-"+a.getNome());
	}
}

//=============================================================================================================================
//EDITAR
//=============================================================================================================================
package teste;

import javax.persistence.EntityManager;
import model.Editora;
import util.JPAUtil;

public class AlterarEditora {
	public static void main(String[] args) {
		EntityManager manager = JPAUtil.getEntityManager();
		Long id = 1L;
		Editora e = manager.find(Editora.class, id);
		
		//novo nome
		e.setNome("Ednei");
		
		//relaiza trrnacacao
		manager.getTransaction().begin();
		manager.getTransaction().commit();
		manager.close();
	}
}

//=============================================================================================================================
//CRIAR ENTIDADES COM CHAVE ESTRANGEIRA
//=============================================================================================================================

package model;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String cpf;

	@Embedded
	private Endereco endereco;
	/*  GET END SETTINGS*/
}

//=============================================================================================================================

package model;
import javax.persistence.Embeddable;
@Embeddable//DIZ QUE ENDERECO SERA CHAVE ESTRANGEIRA DE CLIENTE
public class Endereco {

	private String pais;
	private String estado;
	private String cidade;
}

//=============================================================================================================================
//EXECUTAR 
//=============================================================================================================================

package teste;
import javax.persistence.EntityManager;
import model.Cliente;
import model.Endereco;
import util.JPAUtil;

public class InsertCleinte {

	public static void main(String[] args) {
		EntityManager manager = JPAUtil.getEntityManager();
		Cliente c = new Cliente();
		Endereco e = new Endereco();
		c.setNome("Ednei");
		c.setCpf("12345");
		e.setCidade("Rio de Janeiro");
		e.setEstado("RJ");
		e.setPais("Brasil");
		c.setEndereco(e);
		manager.getTransaction().begin();
		manager.persist(c);
		manager.getTransaction().commit();
		manager.close();
	}
}


//=============================================================================================================================
//CONSULTAS 
//=============================================================================================================================

package teste;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Revista;
import util.JPAUtil;

public class Consultas {

	public static void main(String[] args) {
		EntityManager manager = JPAUtil.getEntityManager();
		String jpql = "SELECT COUNT(x) FROM Revista x";
		TypedQuery<Long> consulta1 = manager.createQuery(jpql, Long.class);
		Long contador = consulta1.getSingleResult();
		
		System.out.println("HA "+ contador+" na Revista");
		
		TypedQuery<Revista> consultaRevista = manager.createQuery("SELECT  x FROM Revista x", Revista.class);
		List<Revista> revistas = consultaRevista.getResultList();
		
		for(Revista r : revistas)
			System.out.println(r.getId()+"\n"+r.getNome()+"\n"+r.getPreco());
	}
}

//=============================================================================================================================
//FIM HIBERNATE 
//=============================================================================================================================


//=============================================================================================================================
//JSF
//=============================================================================================================================
//CRIAR PROIJETO --> DYNAMICWEB
//CONFIGURATION [javaServe Faces V2.1]-->NEXT->NEXT->[MARQUE OPTION GENERATE WEB.XML]-> NEXT->
//JSF COMPATIBILIT [ TYPE : ALTERAR PARA DISABLED LIBRARY CONFIGURATION]
// URL MAPPING DIGITE[*.XHTML] -> FINISH
//=============================================================================================================================
//CRIAR CLASS BEAN
//==============================================================================================================================
package controller;

import javax.faces.bean.ManagedBean;


@ManagedBean
public class NumeroAleatorioBean {

	private int maximo;
	private int numeroAleatorio;
	
	public String gerarNumeroAleatorio(){
		this.numeroAleatorio = (int) (Math.random()*this.maximo);
		return "resposta";
	}

	//GETINS AND SETINGS
}
//==============================================================================================================================
//CONFIGURAR WEB.XML
//==============================================================================================================================
/*<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	id="WebApp_ID" version="3.0">
	<display-name>JSF</display-name>
	<welcome-file-list>
		<welcome-file>index.html</welcome-file>
		<welcome-file>index.htm</welcome-file>
		<welcome-file>index.jsp</welcome-file>
		<welcome-file>default.html</welcome-file>
		<welcome-file>default.htm</welcome-file>
		<welcome-file>default.jsp</welcome-file>
	</welcome-file-list>
	<servlet>
		<servlet-name>Faces Servlet</servlet-name>
		<servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>
	<servlet-mapping>
		<servlet-name>Faces Servlet</servlet-name>
		<url-pattern>*.xhtml</url-pattern>
	</servlet-mapping>

	<!--ADD CAMPO -->
	<context-param>
		<param-name>javax.faces.PROJECT_STAGE</param-name>
		<param-value>Development </param-value>
	</context-param>
</web-app>*/

//==============================================================================================================================
//CRIAR FORMULARIO
//==============================================================================================================================
/*<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core">

	<h:head>
		<title>K19 trinamento</title>
		<h:body>
			<h:form>
				<h:outputLabel value="Número Maximo: "></h:outputLabel>
				<h:inputText value="#{numeroAleatorioBean.numeroAleatorio}"></h:inputText>
				<h:commandButton value="Gerar numero" action="#{numeroAleatorioBean.gerarNumeroAleatorio}"></h:commandButton>
			</h:form>
		</h:body>
	</h:head>
</html>*/

//==============================================================================================================================
//ADD JAR [PRIMEFACES, JAVAXFACES, JSF-APU] NA PASTA LIB [WEB-INF/LIB]
//ORDAR PROJETOS
//==============================================================================================================================


//==============================================================================================================================
//EXIBIR DADOS DO FORMULARIO XHTML
//==============================================================================================================================
/*<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core">

	<h:head>
		<title>Usuario</title>
		<h:body>
			<h:form>
			
				<h:outputLabel value="Nome" for="nome"/>
				<h:inputText value="#{usuarioBean.nome}"/>
				
				<h:outputLabel value="idade" for="campo-idade"/>
				<h:inputText value="#{usuarioBean.idade}"/>
				
				<h:commandButton value="Enviar" />	
			</h:form>
			
			O nome do usuario e <b>#{usuarioBean.nome}</b>
		</h:body>
	</h:head>
</html>*/

//==============================================================================================================================
//ADICIONAR ELEMENTO DA LISTA E EXIBILAS
//==============================================================================================================================
//CLASS MODEL - CURSO
//==============================================================================================================================
package model;
public class Curso {

	private String nome;
	private String sigla;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	@Override
	public String toString() {
		return super.toString();
	}

}

//==============================================================================================================================
//CLASS BEAN - CURSOBEAN
//==============================================================================================================================

package controller;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Curso;

@ManagedBean
@SessionScoped
public class CursoBean {
	
	private List<Curso> cursos = new ArrayList<Curso>();
	private Curso curso = new Curso();
	
	public void adicionarCurso(){
		this.cursos.add(this.curso);
		this.curso = new Curso();
	}
	
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public List<Curso> getCursos() {
		return cursos;
	}
	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}
}


//==============================================================================================================================
//					TEMPLATES & MODULARIZACAO [UI]
//==============================================================================================================================
//CRIAR PASTA TEMPLATES EM [WEB-INF/TEMPLATE/NOME_ARQUIVO_TEMPLATE.XHTML]
//EX: TEMPLATE.XHTML
//==============================================================================================================================
/*<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core">

	<h:head>
		<title>K19 - Treinamento</title>
		<h:body>
			<div id="header"><img src="images/logo.png" width="60"/></div>
			<!-- INSERT CONTEUDO DO XHTML-->
			<ui:insert name="conteudo"></ui:insert>
		</h:body>
	</h:head>
</html>*/

//==============================================================================================================================
//ARQUIVO XHTML COM OS DADOS A SEREM INSERIDOS NO TEMPLATE
//==============================================================================================================================


/*
ui:composition template="/WEB-INF/templates/templates.xhtml-->DEFINE O TEMPLETE DE INDE ESSE CONTEUDO SERA INSERIDO
<ui:composition template="/WEB-INF/templates/templates.xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core">
	
	<ui:define name="conteudo">
		<h:outputLabel value="Nome: " for="campo-nome"/>
		<h:inputText id="campo-nome"/>
		<h:commandButton value="Enviar"/>
	</ui:define>
</ui:composition>*/


//==============================================================================================================================
//XHTML - EXIBIR DADOS
//==============================================================================================================================

/*<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core">

<h:head>
	<title>Usuario</title>
	<h:body>
		<h:form>
			<h:panelGrid columns="2">
				<h:outputLabel value="Nome" for="campo-nome" />
				<h:inputText value="#{cursoBean.curso.nome}" id="campo-nome" />

				<h:outputLabel value="Sigla" for="campo-sigla" />
				<h:inputText value="#{cursoBean.curso.sigla}" id="campo-sigla" />

				<h:commandButton value="Adicionr"
					action="#{cursoBean.adicionarCurso}" />
			</h:panelGrid>

			<h:dataTable value="#{cursoBean.cursos}" var="curso" rendered="#{not empty cursoBean.cursos}">
				<f:facet name="header" >Titulo da tabela</f:facet>
				<h:column>
					<f:facet name="header">Sigla</f:facet>
					#{curso.sigla}
				</h:column>
				
				<h:column>
					<f:facet name="header">Nome</f:facet>
					#{curso.nome}
				</h:column>
			</h:dataTable>
		</h:form>
	</h:body>
</h:head>
</html>*/


//==============================================================================================================================
//MODULARIZAÇÃO
//==============================================================================================================================
//TEMPELTE PRINCIPAL [CRIADO NA PASTA WEB-INF/TEMPLATES/TEMPLATE.XHTML]
//==============================================================================================================================
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core">

	<h:head>
		<title>K19 - Treinamento</title>
		<h:body>
			<div id="header">
				<h1>Link - Template</h1>
			</div>

			<h:link value="Compoente Visuais" outcome="componente-visuais"/> - 
			<h:link value="Templates e Modularizacao" outcome="templates-e-modularizacao"/> - 
			<h:link value="Navegacao" outcome="navegacao"/>
			<hr />

			<!-- INSER TODO O CONTEUDO DO TEMPLATE -->
			<ui:insert name="corpo-da-pagina"></ui:insert>
		</h:body>
	</h:head>
</html>

//==============================================================================================================================
//1º-PAGINA DE COMPOSICAO [ARQUIVO.XHTML]
//==============================================================================================================================
//<!-- DEFINE A COMPOSICAO A SER ADICIONADO NO TAMPLATE -->
<ui:composition template="/WEB-INF/templates/template-link.xhtml"
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core">

	<ui:define name="corpo-da-pagina">
		<h1>Componente Visuais</h1>
		<p>Lorem Ipsum é simplesmente uma simulação de texto da indústria
			tipográfica e de impressos, e vem sendo utilizado desde o século XVI,
			quando um impressor desconhecido pegou uma bandeja de tipos e os
			embaralhou para fazer um livro de modelos</p>
			
		<ul>
			<li>Core (hhttp://java.sun.com/jsf/core)</li>
			<li>Html (hhttp://java.sun.com/jsf/html)</li>
			<li>Facelets (hhttp://java.sun.com/jsf/facelets)</li>
		</ul>
	</ui:define>
</ui:composition>

//==============================================================================================================================
//2º-PAGINA DE COMPOSICAO [ARQUIVO.XHTML]
//==============================================================================================================================
<!-- DEFINE A COMPOSICAO A SER ADICIONADO NO TAMPLATE -->
<ui:composition template="/WEB-INF/templates/template-link.xhtml"
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core">

	<ui:define name="corpo-da-pagina">
		<h1>Navegacao</h1>
		<p>Lorem Ipsum é simplesmente uma simulação de texto da indústria
			tipográfica e de impressos, e vem sendo utilizado desde o século XVI,
			quando um impressor desconhecido pegou uma bandeja de tipos e os
			embaralhou para fazer um livro de modelos</p>
	</ui:define>
</ui:composition>

//==============================================================================================================================
//3º-PAGINA DE COMPOSICAO [ARQUIVO.XHTML]
//==============================================================================================================================
<!-- DEFINE A COMPOSICAO A SER ADICIONADO NO TAMPLATE -->
<ui:composition template="/WEB-INF/templates/template-link.xhtml"
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core">

	<ui:define name="corpo-da-pagina">
		<h1>Template e Modularizacao</h1>
		<p>Lorem Ipsum é simplesmente uma simulação de texto da indústria
			tipográfica e de impressos, e vem sendo utilizado desde o século XVI,
			quando um impressor desconhecido pegou uma bandeja de tipos e os
			embaralhou para fazer um livro de modelos</p>
			
	</ui:define>
</ui:composition>


//==============================================================================================================================
//NAVEGACAO DINAMICA
//==============================================================================================================================
package controller;
import javax.faces.bean.ManagedBean;
@ManagedBean
public class CaraOuCoroaBean {
	public String proxima(){
		if(Math.random()<0.5)
			return "cara";
		else
			return "coroa";
	}
}

//==============================================================================================================================
//ARQUIVO - PRINCIPAL XHTML
//==============================================================================================================================
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core">

	<h:head>
		<title>Usuario</title>
		<h:body>
			<h:form>
			<h:commandButton value="Lancar Moeda" action="#{caraOuCoroaBean.proxima}"/>
			</h:form>
		</h:body>
	</h:head>
</html>

//==============================================================================================================================
//ARQUIVO - PAGINA CARA
//==============================================================================================================================

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core">

	<h:head>
		<title>Usuario</title>
		<h:body>
			<h1>Deu Cara!</h1>
			<h:form>
			<h:commandButton value="Lancar Moeda" action="#{caraOuCoroaBean.proxima}"/>
			</h:form>
		</h:body>
	</h:head>
</html>


//==============================================================================================================================
//ARQUIVO - PAGINA COROA
//==============================================================================================================================

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core">

	<h:head>
		<h:body>
			<h1>Deu Cara!</h1>
			<h:form>
			<h:commandButton value="Lancar Moeda" action="#{caraOuCoroaBean.proxima}"/>
			</h:form>
		</h:body>
	</h:head>
</html>


//==============================================================================================================================
//SCOPO
//==============================================================================================================================
//REQUEST
//==============================================================================================================================
/*No escopo request, as instâncias dos managed beans são criadas durante o processamento de
uma requisição assim que forem necessárias e descartadas no final desse mesmo processamento.
Assim, os dados não são mantidos de uma requisição para outra.
A partir do JSF 2, os managed beans podem ser registrados através da anotação @ManagedBean.
O JSF utiliza o escopo request como padrão quando managed beans são registrados usando essa
anotação. Mesmo sendo o padrão, podemos deixar explícito a escolha do escopo request através da
anotação @RequestScoped.*/

//==============================================================================================================================
//VIEW
//==============================================================================================================================
/*O escopo view foi adicionado no JSF 2. A ideia é manter determinados dados enquanto o usuário
não mudar de tela. As instância dos managed beans em escopo view são eliminadas somente quando
há uma navegação entre telas.*/


//==============================================================================================================================
//Session
//==============================================================================================================================
/*
	Certas informações devem ser mantidas entre as requisições de um determinado usuário em um
determinado navegador. Por exemplo, considere uma aplicação que utiliza carrinho de compras.
Um usuário faz diversas requisições para escolher os produtos e colocá-los no seu carrinho. Durante
todo esse tempo, a aplicação deve manter a informação de quais produtos já foram escolhidos por
esse usuário.
Daí surge o escopo session. Cada usuário possui um espaço na memória do servidor que é chamado
de session. Tecnicamente, é possível existir duas ou mais sessions de um mesmo usuário, por
exemplo, se ele estiver utilizando dois ou mais navegadores.
As instâncias dos managed beans configurados com o escopo session são criadas quando necessárias
durante o processamento de uma requisição e armazenadas na session do usuário que fez a
requisição.
Essas instâncias são eliminadas basicamente em duas situações: a própria aplicação decide por
algum motivo específico apagar a session de um usuário (por exemplo, quando o usuário faz logout)
ou o servidor decide apagar a session de um usuário quando esse usuário não faz requisições por um
determinado período de tempo. Esse tempo pode ser configurado com o Web Container.
Para escolher o escopo session, devemos utilizar a anotação @SessionScoped ou a tag managed-bean-scope.
*/

package controller;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import model.Carro;


@ManagedBean
@SessionScoped
public class CarroBean {

	private List<Carro> carros = new ArrayList<Carro>();
	private Carro carro = new Carro();
	public List<Carro> getCarros() {
		return carros;
	}
	
	public void adicionarCarro(){
		this.carros.add(carro);
		this.carro = new Carro();
	}
	
	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}
	public Carro getCarro() {
		return carro;
	}
	public void setCarro(Carro carro) {
		this.carro = carro;
	}
}


//==============================================================================================================================
//APLOCATION
//==============================================================================================================================

/*
	As instâncias dos managed beans configurados com escopo application são criadas no primeiro
	momento em que elas são utilizadas e mantidas até a aplicação ser finalizada.
	Ao contrário do que ocorre com escopos discutidos anteriormente, as instâncias dos managed
	beans registrados com escopo application são compartilhadas por todos os usuários da aplicação. O
	JSF cria apenas uma instância de cada managed bean em escopo de application.
	Analogamente, para escolher o escopo application, devemos utilizar a anotação @ApplicationScoped
	ou a tag managed-bean-scope.
*/

//==============================================================================================================================
//ANOTACOES
//==============================================================================================================================
/*
Veja abaixo as anotações disponíveis e suas respectivas funções.
• @AssertFalse
Verifica se uma propriedade booleana possui valor false.
• @AssertTrue
Verifica se uma propriedade booleana possui valor true.
• @DecimalMax
Define o valor real máximo que uma propriedade pode armazenar.
• @DecimalMin
Define o valor real mínimo que uma propriedade pode assumir.
@Digits
Define a quantidade máxima de dígitos da parte inteira (através do atributo integer) ou da
parte fracionária (através do atributo fraction) de um número.
• @Future
Verifica se uma data é posterior ao instante atual.
• @Max
Define o valor inteiro máximo que uma propriedade pode assumir.
• @Min
Define o valor inteiro mínimo que uma propriedade pode armazenar.
• @NotNull
Verifica se o valor de uma propriedade não é null.
• @Null
Verifica se o valor de uma propriedade é null.
• @Past
Verifica se uma data é anterior ao instante atual.
• @Pattern
Verifica se o valor de uma propriedade respeita uma expressão regular.
• @Size
Define os tamanhos mínimo (através do atributo min) e máximo (através do atributo max) para
uma Collection, array ou String.*/

//==============================================================================================================================
public class Anotacoes{

	@NotNull
	@Min(value=0)
	@Max(value=20)
	private int num;
}

//=========================================================================================================================
//EVENTS - ACTIONEVENTS
//=========================================================================================================================
package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

@ManagedBean
public class BotaoBean {

	private int valor;
	public void sorteaBotao(ActionEvent event){
		
		UIComponent formulario = event.getComponent().getParent();
		
		//id campo button html
		UIComponent botaoJonas = formulario.findComponent("botao-jonas");
		UIComponent botaoMarcelo = formulario.findComponent("botao-marcelo");
		UIComponent botaoRafael = formulario.findComponent("botao-rafael");
		
		botaoJonas.getAttributes().put("disabled", true);
		botaoMarcelo.getAttributes().put("disabled", true);
		botaoRafael.getAttributes().put("disabled", true);
		
		double numero = Math.random();
		this.valor = (int) numero;
		if(numero< 1.0/3.0){
			botaoJonas.getAttributes().put("disabled", false);
		}else if(numero < 2.0/3.0){			
			botaoMarcelo.getAttributes().put("disabled", false);
		}else{
			botaoRafael.getAttributes().put("disabled", false);
		}
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}	
}
//=========================================================================================================================
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core">

	<h:head>
		<title>Boteos</title>
		<h:body>
			<h:form>
				<h:commandButton value="Jonas" id="botao-jonas" disabled="false" 
				actionListener="#{botaoBean.sorteaBotao}"/>
				
				<h:commandButton value="Marcelo" id="botao-marcelo" disabled="true" 
				actionListener="#{botaoBean.sorteaBotao}"/>
				
				<h:commandButton value="Rafael" id="botao-rafael" disabled="true" 
				actionListener="#{botaoBean.sorteaBotao}"/>
			</h:form>
			
			<h:outputText  value="#{botaoBean.valor}"/>
		</h:body>
	</h:head>
</html>

//=========================================================================================================================
//EVENTS - VALUCHANGEDEVENT
//=========================================================================================================================
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core">

	<h:head>
		<title>Preco</title>
		<h:body>
			<h:form>
				<h:outputLabel value="Preco: " />
				<h:inputText valueChangeListener="#{produtoBean.mudarPreco}" id="preco" />
			</h:form>
		</h:body>
	</h:head>
</html>
//=========================================================================================================================
package controller;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
public class ProdutoBean {

	public void mudarPreco(ValueChangeEvent ev){
		System.out.println("Preco antigo "+ev.getOldValue());
		System.out.println("Preco novo "+ev.getNewValue());
	}
}

//=========================================================================================================================
//AJAX
//=========================================================================================================================
/*

	@all refere-se a todos os componentes da tela.
	@none refere-se a nenhum componente.
	@this refere-se ao componente que disparou a requisição AJAX.
	@form refere-se aos componentes do formulário que contém o componente que disparou a requisição
	AJAX.

*/


//----------------------------------------------------------------------------------------------------------------
// 1- criar um formulario em um arquivo html
//----------------------------------------------------------------------------------------------------------------

 <form  action="CalcBasic" get="get" >
	<label>Valor A:</label>
    <input type="text" name="valorA" /><br>
    <label>Valor B:</label>
    <input type="text" name="valorB" /><br>
                
    <label>Operacao</label>
    <select name="operacao">
    <option value="+">+</option>
    <option value="-">-</option>
    <option value="*">*</option>
    <option value="/">/</option>
    </select><br>
                
    <input type="submit" value="Calcular" />
</form>




//----------------------------------------------------------------------------------------------------------------
// 2 - Cria um Servelet
//OBS: os methodos doGet sao usados para recuerar dados do formulario via name ex input name = xxx
//Para recuoperar os dadosd  via get e preciso usar:
// 'request' ex: request.getaparamneter("input[name]");
//urlPattern --> susado para navegacao via formulario ou link
//urlPatterns = {"/CalcBasic"}) --> o parametro utilizado eo "CalcBasic" dentro do action ou na tag a
//---------------------------------------------------------------------------------------------------------------


@WebServlet(name = "CalcBasic", urlPatterns = {"/CalcBasic"})


public class CalcBasic extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        //Trabalar com tipo de txt html
        response.setContentType("text/html;charset=UTF-8");
        //utilizar os methodo out
        PrintWriter out = response.getWriter();
        
        //pegar os dado input[name]
        String valorA = request.getParameter("valorA");
        String valorB = request.getParameter("valorB");
        String operacao = request.getParameter("operacao");
        out.println(valorA+" "+operacao+" "+ valorB);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}


//----------------------------------------------------------------------------------------------------------------
// 3 - Cria Conexao
//OBS: E preciso importar a lib mysql
//---------------------------------------------------------------------------------------------------------------

public class ConexaoFactory {
    
    private static final String DB_NAME =""
    private   static final String  URL = "jdbc:mysql://localhost:3306/"+DB_NAME;
    private static  final String  USER = "root";
    private static  final String  PASS = "";
    
    public static Connection conexao() throws SQLException, ClassNotFoundException{
        
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        return conn;
    }
    
}

//----------------------------------------------------------------------------------------------------------------
// 4 - Inseir dados
//OBS: E preciso importar a lib mysql
//---------------------------------------------------------------------------------------------------------------


//EVIA OS DADOS VIA HTML
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <form action="LoginServelet" method="get">
            <label>Login</label>
            <input type="text" name="login"/>
            <label>Senha</label>
            <input type="password" name="senha"/>
            <input type="submit" value="Entrar"/>
        </form>
    </body>
</html>


//CRIA A CLASS PARA INSERIR OS DADOS
public class UsuarioDao {

    private Connection conn ;
    
    public void cadastro(Usuario user) throws SQLException, ClassNotFoundException{
        
        //cria conexao
        conn = ConexaoFactory.conexao();
        //slq
        String sql = "INSERT  INTO usuario (login, senha) VALUES(?, ?)";
        //prepara o resulktado para execucao
        PreparedStatement ps = conn.prepareStatement(sql);
        //pega os paramestro passados dentro do value
        ps.setString(1, user.getLogin());
        ps.setString(2, user.getSenha());
        //executa a instrucao
        ps.execute();
    }
}

//inseir os dados na class Servelet
@Override

//@WebServlet(name = "LoginServelet", urlPatterns = {"/LoginServelet"}) --/> mapea a class para uso
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    processRequest(request, response);

    response.setContentType("text/html;charset=UTF-8");

    PrintWriter out = response.getWriter();

        //recuper os dados via get  formulario
    String login = request.getParameter("login");
    String senha = request.getParameter("senha");
    UsuarioDao dao = new UsuarioDao();

    try {
    	dao.cadastro(new Usuario(login, senha));
        //caso de sucesso envia os dados para a pagina index
         response.sendRedirect("login.html");
    } catch (Exception e) {
    	out.println(e.getMessage());
    }
}

//------------------------------------------------------------------------------------------------------------------------
//SESSAO
//------------------------------------------------------------------------------------------------------------------------

//1 FORMULARIO PARA SEREM PASSADOS OS DADOS VIAN IMPUT[NAME]

<form action="./SessaoServelet" method="get">
	<label>Nome</label>
	<input type="text" name="login" ><br>
		
	<label>Senha</label>
	<input type="text" name="senha" ><br>
		
	<input type="submit" value="Entrar" >
</form>

// 2 - CRAR SERVELET PARA INICIAR A SESSAO
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	HttpSession sessao = request.getSession();
	String login = request.getParameter("login");
	sessao.setAttribute("usuarioSession", login);
	response.sendRedirect("Logado");
}

// 3 - CRIAR SERVELET PARA RECUPERAR A SESSAO
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	response.setContentType("text/html");
	PrintWriter out =response.getWriter();	
	HttpSession sessao = request.getSession();
	String usuarioSession = (String)sessao.getAttribute("usuarioSession");	

	out.println("Usuario " + u suarioSession+ " Logado");	
}


//------------------------------------------------------------------------------------------------------------------------
//COOKIE
//------------------------------------------------------------------------------------------------------------------------

// 1 - Cria para receber os dados via form

<form action="./InitCookiesServlet" method="get">
	<label>Name:</label>
	<input type="text" name="login" /><br> 
	<label>Password:</label>
	<input type="password" name="password" /><br> 
	<input type="submit" value="login" />
</form>

// 2 - Criar o Cookies pegando o parametro passado via get

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
		
	request.getRequestDispatcher("link.html").include(request, response);

	// recebe parametros
	String login = request.getParameter("login");

	if (login.equals("ednei1")) {
		out.println("<p>Bem vindo " + login+"</p>");

		// cria o cookie
		Cookie c = new Cookie("login", login);
		c.setMaxAge(3600);

		response.addCookie(c);
	}else{
		response.sendRedirect("./index.html");
	}
}

// 3 - Exibe os cookie com os dados
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();	

	//recebe o cookie
	Cookie[] c= request.getCookies();
	out.println("Cookie "+ c[0].getValue());

	//no,e do cookie
	c[0].getName();
}

/*==================================================================
	ERROR DE SESSAO
==================================================================*/
200 ATE 299 BEM SUCEDIDO
300 ATE 399 INFORMACOES 
400 ATE 499 ERRO DE REQUISICAO
500 ATE 599 ERRO DE SERVIDOR 
-------------------------------------------------------------------
200 URL ENCONTRADA E TRANSMISAO COM SUCESSO
400 PROTOCOLO INEXISTENTE [BAD REQUEST]
404 NAO FOI ENCONTRADO URL [NOT FOUND]
405 SERVODR NAO SUPORTA O METODO SOLICITADO [METHOD NOT ALLOWED]
500 ERROR DESCONHECIDO DE SERVODOR [INTERNAL SERVER ERROR]
503 CAPACIDADE MAXIMA DE SERVODOR ALCANCADO 


//cria um cookie
Cookie c = new Cookie("cookie", cookie);

//add o cookie
response.addCookie(c);

//recuoparar o cookie
Cookie[] c = request.getCookies();
c[i].getName();

//criar uma sessao
HttpSession sesao = reuqest.getSession();
//add
sessao.setAttribute("sessao", sessao);

response.sendReditect("./Servlet");


//recuprar sessao
HttpSession sesao = request.getSession();
String s = (String)sessao.getAttribute("login");

if(s!=null){
	//ACESSA
}
else{
	response.sendError(404);
}

<error-page>
	<error-code>404</error-code>
	<location>error.html</location>
</error-page>

//==============================================================

//usar o HTML PARA 
* Javac e o nome do compilador
* extensao do arquivo termina com .javaç
* Arquivo preduzido  peli compilador java contem os bytecodes que sao 
* Executados pela java virtual Machine
 /*--------------------------------------------------------------------
* JAVA SE serve para criacao de janelas 
* JAVA EE BANCO DE DADOS 
* JAVA ME PAR APP MOBILE
 --------------------------------------------------------------------*/


//==============================================================
//Variáveis locais
//==============================================================

/*Chamamos de locais as variáveis declaradas dentro de métodos ou construtores.
O ciclo de vida de uma variável local vai do ponto onde ela foi declarada até o fim do bloco onde ela foi declarada.
Mas o que é um bloco --> Podemos entender como bloco um trecho decódigo entre chaves.*/

//inicio dop bloco de methodo
public void m1()
{
	/*
		temos uma variável x, que é declarada no
		começo do método. Ela pode ser utilizada durante todo o corpo do método.
		Dentro do if,
	*/
	int x = 10;//vartiavel local
		
	//inicio do blico if
	if(x>=10)
	{
		/*
			declaramos a variável y. y só pode ser utilizada dentro do
			corpo do if,
		*/
		int y= 50;//variavel, local
		System.out.println(y);
	}

	/*
		Tome cuidado especial com loops for. As variáveis declaradas na área
		de inicialização do loop só podem ser usadas no corpo do loop:
	*/

	for(int i=0;j=0; i>10; i++)
		j++;
	System.out.println(j);
}


/*============================================================================
	COENXAO COM BANCO JDBC
==============================================================================*/
public class ConnectionFactory {
	
	private static final String stringConexao = "jdbc:mysql://localhost:3306/livraria";
	private static final String usuario = "root";
	private static final String pass = "";
	
	public static Connection createConnection() throws SQLException{	
		Connection conn = DriverManager.getConnection(stringConexao, usuario, pass );
		return conn;
	}
}

/*============================================================================
	INSERIR  COM BANCO JDBC
==============================================================================*/
try {
			
	//cria a coenxao com o banco
	//a class nao precisa ser instan ciada por que ela esta definida como estatica
	Connection conexao = ConnectionFactory.createConnection();

	System.out.println("Crinado tabelas....");
	Editora e = new Editora(1, "Maria", "maria@gmail.com");

	System.out.println(e.getNome()+" "+e.getEmail());
	String sql = "INSERT INTO Editora(nome, email) VALUES(?, ?)";	

	PreparedStatement cmd = conexao.prepareStatement(sql);

	cmd.setString(1, e.getNome());
	cmd.setString(2, e.getEmail());	
	cmd.execute();
	cmd.close();	

	System.out.println("Sucesso!");		
} catch (Exception e) {
	System.out.println("Error: "+e.getMessage());
}

/*============================================================================
	UPDATE  COM BANCO JDBC
==============================================================================*/

//cria a coenxao com o banco
//a class nao precisa ser instan ciada por que ela esta definida como estatica
Connection conexao = ConnectionFactory.createConnection();
			
Editora e = new Editora();
e.setId(1);
e.setNome("xxxx");
e.setEmail("email@gmail");
			
System.out.println("update dados....");
String sql = "UPDATE Editora SET nome = ?, email = ? WHERE id = ?";
			
PreparedStatement cmd = conexao.prepareStatement(sql);
			
cmd.setString(1, e.getNome());
cmd.setString(2, e.getEmail());
cmd.setInt(3, e.getId());
			
cmd.execute();
cmd.close();


/*============================================================================
	DELETE  COM BANCO JDBC
==============================================================================*/

//cria a coenxao com o banco
//a class nao precisa ser instan ciada por que ela esta definida como estatica
Connection conexao = ConnectionFactory.createConnection();
			
Editora e = new Editora();
e.setId(1);
			
System.out.println("DELETE dados....");
String sql = "DELETE FROM Editora WHERE id = ?";
			
PreparedStatement cmd = conexao.prepareStatement(sql);
cmd.setInt(1, e.getId());
			
cmd.execute();
cmd.close();

/*============================================================================
	LISTAR  COM BANCO JDBC
==============================================================================*/
//cria a coenxao com o banco
//a class nao precisa ser instan ciada por que ela esta definida como estatica
Connection conexao = ConnectionFactory.createConnection();
			
String sql = "SELECT * FROM editora";
PreparedStatement cmd = conexao.prepareStatement(sql);
			
ResultSet res = cmd.executeQuery();
ArrayList<Editora>lista = new ArrayList<Editora>();
			
while(res.next()){				
	//cria editora
	Editora e = new Editora();
	e.setNome(res.getString("nome"));
	e.setEmail(res.getString("email"));				
	//add a lista
	lista.add(e);
}
			
//exibe a lista
for(Editora editora: lista){
	System.out.println("Nome: "+editora.getNome());
	System.out.println("Email: "+editora.getEmail());
	System.out.println("-------------------------------");
}
			
cmd.close();


/*============================================================================
	HIBERNATE
============================================================================*/
	/*Para configurar o Hibernate em uma aplicação, devemos criar um arquivo chamado persistence.
	xml.
	Propriedade 
	--none: As tabelas não serão criadas nem apagadas pelo provedor JPA
	--create: Na inicialização da unidade de persistência, se as tabelas não existirem na base de dados
	elas serão criadas pelo provedor JPA.
	--drop-and-create: Na inicialização da unidade de persistência, se as tabelas não existirem na base de
	dados elas serão criadas pelo provedor JPA.
	--drop: Na inicialização da unidade de persistência, se as tabelas existiremna base de dados elas serão
	apagadas pelo provedor JPA.*/

	/*
	Mapeamento
	@Entity É a principal anotação do JPA. Ela deve aparecer antes do nome de uma classe e deve ser
	definida em todas as classes que terão objetos persistidos no banco de dados.
	Ex: 
	@Entity
	public class Editora{}*/

	@Id Utilizada para indicar qual atributo de uma classe anotada com @Entity será mapeado para a
	chave primária da tabela correspondente à classe. Geralmente o atributo anotado com @Id é
	do tipo Long.
	@Entity
	public class Editora{
		@id
		private long id;
	}

	@GeneratedValue Geralmente vem acompanhado da anotação @Id. Serve para indicar que o atributo
	é gerado pelo banco, no momento em que um novo registro é inserido.
	@Entity
	public class Editora{
		@id
		@GeneratedValue
		private long id;
	}

	@Table Utilizada para alterar o nome padrão da tabela. Ela recebe o parâmetro name para indicar
	qual nome deve ser utilizado na tabela. Veja o exemplo:

	@Table(name = 'minhaTabela')
	@Entity
	public class Editora{
		@id
		@GeneratedValue
		private long id;
	}

	@Column Utilizado para alterar o nome da coluna que será usado na tabela.

	@Entity
	public class Editora{
		@Column (name= "nome", nullable=null, lenght=30)
		private String nome;
	}

	Podemos utilizar o atributo columnDefinition se houver a necessidade de definir o tipo e as
	restrições da coluna diretamente em SQL.

	@Entity
	public class Editora{
		@Column (name= "nome",columnDefinition= "VARCHAR(255)")
		private String nome;
	}


	@Transient Serve para indicar que um atributo não deve ser persistido, ou seja, os atributos anotados
	com @Transient não são mapeados para colunas.
	
	@Entity
	public class Editora{
		@id
		@GeneratedValue
		@Transient
		priavate int idade
	}
/*============================================================================
//Criando umManaged Bean
/*============================================================================*/

/*Um managed bean pode ser definido de duas maneiras. A primeira maneira é criar uma classe
Java e registrá-la no arquivo faces-config.xml. Veja o exemplo abaixo.*/

//faces.config.xml
<managed-bean>
	<managed-bean-name>testeBean </ managed -bean - name >
	<managed-bean-class>br.com .k19 .TesteBean </ managed -bean - class >
	<managed-bean-scope>request </ managed -bean - scope >
</ managed-bean >

/*A segunda forma é criar uma classe Java com a anotação @ManagedBean do pacote javax.faces.bean.
Essa anotação só pode ser utilizada a partir da versão 2 do JSF.*/

import javax.faces.bean.ManagedBean;/*import da anotacao bean*/
@ManagedBean
public class NumeroAleatorioBean {
	private int maximo;
	private int numeroAleatorio;
	
	public int getMaximo() {return maximo;}
	public void setMaximo(int maximo) {this.maximo = maximo;}
	public int getNumeroAleatorio() {return numeroAleatorio;}
	public void setNumeroAleatorio(int numeroAleatorio) {this.numeroAleatorio = numeroAleatorio;}

	public String NumeroAleatorioBean(){
		this.numeroAleatorio = (int) (Math.random()* this.maximo);
		return "respostas";
	}
}

/*Com os métodos de acesso já implementados, podemos exibir o valor do atributo numero utilizando
expression language (#{}). Veja o exemplo a seguir.*/

<h:form>
	<h:outputLabel value="Numero maximo:" /><br/>

	<!-- recebe o valor do input -->
	<h:inputText value="#{numeroAleatorioBean.maximo}" /><br/>
		
	<!-- executa a funcao -->
	<h:commandButton value="Gerar numero aleatorio" action="#{numeroAleatorioBean.gearNumAlatorio}" /><br/><br/>
		
	<!-- exibe o valor  -->
	<h:outputLabel value="Numero gerado: " /><h:outputText value="#{numeroAleatorioBean.numeroAleatorio}"/>
</h:form>
/*Todo managed bean possui um nome único que é utilizado para acessá-lo dentro
dos trechos escritos com expression language. Quando utilizamos a anotação
@ManagedBean, por padrão, o JSF assume que o nome do managed bean é o nome da classe
com a primeira letra em caixa baixa. Porém, podemos alterar esse nome acrescentado umargumento
na anotação.*/

@ManagedBean ( name =" teste ")

/*==================================================================*/
//TEMPLETE
/*==================================================================*/

/*
/----------------------------------------------------------------------
1 - cria um templete --> new arquivo.xhtml
/----------------------------------------------------------------------


2 - Inseirri os import para as blibiotecas
/----------------------------------------------------------------------

<html xmlns="http://www.w3.org/1999/xhtml"
xmlns:h="http://java.sun.com/jsf/html" Import html primefaces
xmlns:f="http://java.sun.com/jsp/jstl/core" Import Primefaces
xmlns:ui="http://java.sun.com/jsf/facelets" Import blibiotecxa do facesles
xmlns:p="http://primefaces.org/ui"> Import Primefaces

<h:head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Drogaria</title>
</h:head>

[OBS: A BLIBIOTECA EUSADA PARA INSEIR OS LAYOUTS OUY INCLUIR]

<h:body>
	<!-- inserir component layout- -->
	<p:layout fullPage="true">
		<!-- deixa o layout responsivo -->
		<p:layoutUnit position="north">
			<!-- futuramente sera inserido um menu -->
			<ui:insert name="menu" />
		</p:layoutUnit>
		<p:layoutUnit position="center">
		<!-- futuramente sera inserido um menu -->
		<ui:insert name="conteudo"/>
		</p:layoutUnit>
	</p:layout>
</h:body>
</html>

/----------------------------------------------------------------------
3- cria a pagina EX:princial.xhtml
/----------------------------------------------------------------------

<!-- cria o menu para ser inserido -->

*/

/******************************************************************
	JAVA
*******************************************************************/

* Javac e o nome do compilador
* extensao do arquivo termina com .java
* Arquivo preduzido  peli compilador java contem os bytecodes que sao 
* Executados pela java virtual Machine
 /*--------------------------------------------------------------------
* JAVA SE serve para criacao de janelas 
* JAVA EE BANCO DE DADOS 
* JAVA ME PAR APP MOBILE
 --------------------------------------------------------------------*/


/*##############################################################################################################################
	Variáveis locais
##############################################################################################################################*/

/*Chamamos de locais as variáveis declaradas dentro de métodos ou construtores.
O ciclo de vida de uma variável local vai do ponto onde ela foi declarada até o fim do bloco onde ela foi declarada.
Mas o que é um bloco --> Podemos entender como bloco um trecho decódigo entre chaves.*/

//inicio dop bloco de methodo
public void m1()
{
	/*
		temos uma variável x, que é declarada no
		começo do método. Ela pode ser utilizada durante todo o corpo do método.
		Dentro do if,
	*/
	int x = 10;//vartiavel local
		
	//inicio do blico if
	if(x>=10)
	{
		/*
			declaramos a variável y. y só pode ser utilizada dentro do
			corpo do if,
		*/
		int y= 50;//variavel, local
		System.out.println(y);
	}

	/*
		Tome cuidado especial com loops for. As variáveis declaradas na área
		de inicialização do loop só podem ser usadas no corpo do loop:
	*/

	for(int i=0;j=0; i>10; i++)
		j++;
	System.out.println(j);
}


/*============================================================================
	COENXAO COM BANCO JDBC
==============================================================================*/
public class ConnectionFactory {
	
	private static final String stringConexao = "jdbc:mysql://localhost:3306/livraria";
	private static final String usuario = "root";
	private static final String pass = "";
	
	public static Connection createConnection() throws SQLException{	
		Connection conn = DriverManager.getConnection(stringConexao, usuario, pass );
		return conn;
	}
}

/*============================================================================
	INSERIR  COM BANCO JDBC
==============================================================================*/
try {
			
	//cria a coenxao com o banco
	//a class nao precisa ser instan ciada por que ela esta definida como estatica
	Connection conexao = ConnectionFactory.createConnection();

	System.out.println("Crinado tabelas....");
	Editora e = new Editora(1, "Maria", "maria@gmail.com");

	System.out.println(e.getNome()+" "+e.getEmail());
	String sql = "INSERT INTO Editora(nome, email) VALUES(?, ?)";	

	PreparedStatement cmd = conexao.prepareStatement(sql);

	cmd.setString(1, e.getNome());
	cmd.setString(2, e.getEmail());	
	cmd.execute();
	cmd.close();	

	System.out.println("Sucesso!");		
} catch (Exception e) {
	System.out.println("Error: "+e.getMessage());
}

/*============================================================================
	UPDATE  COM BANCO JDBC
==============================================================================*/

//cria a coenxao com o banco
//a class nao precisa ser instan ciada por que ela esta definida como estatica
Connection conexao = ConnectionFactory.createConnection();
			
Editora e = new Editora();
e.setId(1);
e.setNome("xxxx");
e.setEmail("email@gmail");
			
System.out.println("update dados....");
String sql = "UPDATE Editora SET nome = ?, email = ? WHERE id = ?";
			
PreparedStatement cmd = conexao.prepareStatement(sql);
			
cmd.setString(1, e.getNome());
cmd.setString(2, e.getEmail());
cmd.setInt(3, e.getId());
			
cmd.execute();
cmd.close();


/*============================================================================
	DELETE  COM BANCO JDBC
==============================================================================*/

//cria a coenxao com o banco
//a class nao precisa ser instan ciada por que ela esta definida como estatica
Connection conexao = ConnectionFactory.createConnection();
			
Editora e = new Editora();
e.setId(1);
			
System.out.println("DELETE dados....");
String sql = "DELETE FROM Editora WHERE id = ?";
			
PreparedStatement cmd = conexao.prepareStatement(sql);
cmd.setInt(1, e.getId());
			
cmd.execute();
cmd.close();

/*============================================================================
	LISTAR  COM BANCO JDBC
==============================================================================*/
//cria a coenxao com o banco
//a class nao precisa ser instan ciada por que ela esta definida como estatica
Connection conexao = ConnectionFactory.createConnection();
			
String sql = "SELECT * FROM editora";
PreparedStatement cmd = conexao.prepareStatement(sql);
			
ResultSet res = cmd.executeQuery();
ArrayList<Editora>lista = new ArrayList<Editora>();
			
while(res.next()){				
	//cria editora
	Editora e = new Editora();
	e.setNome(res.getString("nome"));
	e.setEmail(res.getString("email"));				
	//add a lista
	lista.add(e);
}
			
//exibe a lista
for(Editora editora: lista){
	System.out.println("Nome: "+editora.getNome());
	System.out.println("Email: "+editora.getEmail());
	System.out.println("-------------------------------");
}
			
cmd.close();


/*============================================================================
	HIBERNATE
============================================================================*/
	/*Para configurar o Hibernate em uma aplicação, devemos criar um arquivo chamado persistence.
	xml.
	Propriedade 
	--none: As tabelas não serão criadas nem apagadas pelo provedor JPA
	--create: Na inicialização da unidade de persistência, se as tabelas não existirem na base de dados
	elas serão criadas pelo provedor JPA.
	--drop-and-create: Na inicialização da unidade de persistência, se as tabelas não existirem na base de
	dados elas serão criadas pelo provedor JPA.
	--drop: Na inicialização da unidade de persistência, se as tabelas existiremna base de dados elas serão
	apagadas pelo provedor JPA.*/

	/*
	Mapeamento
	@Entity É a principal anotação do JPA. Ela deve aparecer antes do nome de uma classe e deve ser
	definida em todas as classes que terão objetos persistidos no banco de dados.
	Ex: 
	@Entity
	public class Editora{}*/

	@Id Utilizada para indicar qual atributo de uma classe anotada com @Entity será mapeado para a
	chave primária da tabela correspondente à classe. Geralmente o atributo anotado com @Id é
	do tipo Long.
	@Entity
	public class Editora{
		@id
		private long id;
	}

	@GeneratedValue Geralmente vem acompanhado da anotação @Id. Serve para indicar que o atributo
	é gerado pelo banco, no momento em que um novo registro é inserido.
	@Entity
	public class Editora{
		@id
		@GeneratedValue
		private long id;
	}

	@Table Utilizada para alterar o nome padrão da tabela. Ela recebe o parâmetro name para indicar
	qual nome deve ser utilizado na tabela. Veja o exemplo:

	@Table(name = 'minhaTabela')
	@Entity
	public class Editora{
		@id
		@GeneratedValue
		private long id;
	}

	@Column Utilizado para alterar o nome da coluna que será usado na tabela.

	@Entity
	public class Editora{
		@Column (name= "nome", nullable=null, lenght=30)
		private String nome;
	}

	Podemos utilizar o atributo columnDefinition se houver a necessidade de definir o tipo e as
	restrições da coluna diretamente em SQL.

	@Entity
	public class Editora{
		@Column (name= "nome",columnDefinition= "VARCHAR(255)")
		private String nome;
	}


	@Transient Serve para indicar que um atributo não deve ser persistido, ou seja, os atributos anotados
	com @Transient não são mapeados para colunas.
	
	@Entity
	public class Editora{
		@id
		@GeneratedValue
		@Transient
		priavate int idade
	}
/*============================================================================
//Criando umManaged Bean
/*============================================================================*/

/*Um managed bean pode ser definido de duas maneiras. A primeira maneira é criar uma classe
Java e registrá-la no arquivo faces-config.xml. Veja o exemplo abaixo.*/

//faces.config.xml
<managed-bean>
	<managed-bean-name>testeBean </ managed -bean - name >
	<managed-bean-class>br.com .k19 .TesteBean </ managed -bean - class >
	<managed-bean-scope>request </ managed -bean - scope >
</ managed-bean >

/*A segunda forma é criar uma classe Java com a anotação @ManagedBean do pacote javax.faces.bean.
Essa anotação só pode ser utilizada a partir da versão 2 do JSF.*/

import javax.faces.bean.ManagedBean;/*import da anotacao bean*/
@ManagedBean
public class NumeroAleatorioBean {
	private int maximo;
	private int numeroAleatorio;
	
	public int getMaximo() {return maximo;}
	public void setMaximo(int maximo) {this.maximo = maximo;}
	public int getNumeroAleatorio() {return numeroAleatorio;}
	public void setNumeroAleatorio(int numeroAleatorio) {this.numeroAleatorio = numeroAleatorio;}

	public String NumeroAleatorioBean(){
		this.numeroAleatorio = (int) (Math.random()* this.maximo);
		return "respostas";
	}
}

/*Com os métodos de acesso já implementados, podemos exibir o valor do atributo numero utilizando
expression language (#{}). Veja o exemplo a seguir.*/

<h:form>
	<h:outputLabel value="Numero maximo:" /><br/>

	<!-- recebe o valor do input -->
	<h:inputText value="#{numeroAleatorioBean.maximo}" /><br/>
		
	<!-- executa a funcao -->
	<h:commandButton value="Gerar numero aleatorio" action="#{numeroAleatorioBean.gearNumAlatorio}" /><br/><br/>
		
	<!-- exibe o valor  -->
	<h:outputLabel value="Numero gerado: " /><h:outputText value="#{numeroAleatorioBean.numeroAleatorio}"/>
</h:form>
/*Todo managed bean possui um nome único que é utilizado para acessá-lo dentro
dos trechos escritos com expression language. Quando utilizamos a anotação
@ManagedBean, por padrão, o JSF assume que o nome do managed bean é o nome da classe
com a primeira letra em caixa baixa. Porém, podemos alterar esse nome acrescentado umargumento
na anotação.*/

@ManagedBean ( name =" teste ")

/*==================================================================*/
//TEMPLETE
/*==================================================================*/

/*
/----------------------------------------------------------------------
1 - cria um templete --> new arquivo.xhtml
/----------------------------------------------------------------------


2 - Inseirri os import para as blibiotecas
/----------------------------------------------------------------------
*/

/*==================================================================*/
//HEADER JSF
/*==================================================================*/


<html xmlns="http://www.w3.org/1999/xhtml"
xmlns:h="http://java.sun.com/jsf/html" Import html primefaces
xmlns:f="http://java.sun.com/jsp/jstl/core" Import Primefaces
xmlns:ui="http://java.sun.com/jsf/facelets" Import blibiotecxa do facesles
xmlns:p="http://primefaces.org/ui"> Import Primefaces

<h:head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Drogaria</title>
</h:head>

/*
[OBS: A BLIBIOTECA EUSADA PARA INSEIR OS LAYOUTS OUY INCLUIR]

<h:body>
	<!-- inserir component layout- -->
	<p:layout fullPage="true">
		<!-- deixa o layout responsivo -->
		<p:layoutUnit position="north">
			<!-- futuramente sera inserido um menu -->
			<ui:insert name="menu" />
		</p:layoutUnit>
		<p:layoutUnit position="center">
		<!-- futuramente sera inserido um menu -->
		<ui:insert name="conteudo"/>
		</p:layoutUnit>
	</p:layout>
</h:body>
</html>

/----------------------------------------------------------------------
3- cria a pagina EX:princial.xhtml
/----------------------------------------------------------------------

<!-- cria o menu para ser inserido -->

*/
// 3 - Configurar pom.xml

<project xmlns="http://maven.apache.org/POM/4.0.0" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.br.mavemproject</groupId>
  <artifactId>mavemProject</artifactId>
  <version>1.0</version>
  <packaging>war</packaging>
  <name>ManvenProject</name>
  
  <!-- Codificação dos caracteres -->
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>
	
	<!-- Parâmetros de execução -->
	<build>
		<!-- Nome do projeto empacotado -->
		<finalName>Drogaria</finalName>

		<!-- Plugins -->
		<plugins>
			<!-- Compilador -->
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.3</version>
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
				</configuration>
			</plugin>
		</plugins>
	</build>
</project>

//Update Project

// 4- criar web.xml
// src -> main-> webapp->CRIAR PASTA[WEB-INF] criar arquivo dentro web.xml da pasta

 <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://xmlns.jcp.org/xml/ns/javaee"
	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
	id="WebApp_ID" version="3.1">

	<!-- Nome da Aplicação -->
	<display-name>Drogaria</display-name>
</web-app>

//update project

//gerar executavel
	//1 maven celan 
	//2 maven install

/*==================================================================*/
//HIBERNATE
/*==================================================================*/
//dentro do arquivo pom

</build>
	
	<!-- Dependencias -->
		<dependencies>
		<!-- hibernate core -->
		<!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-core -->
			<dependency>
			    <groupId>org.hibernate</groupId>
			    <artifactId>hibernate-core</artifactId>
			    <version>5.2.11.Final</version>
			</dependency>
		</dependencies>
</project>

/*==================================================================*/
//JUNIT
/*==================================================================*/

//Arquivo pom.xml

<!-- https://mvnrepository.com/artifact/junit/junit -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>

//class em pruducao
src/main/java/[pacote util]
	CalssUtil

//parta teste usado para teste de class em java
src/teste/java/[pacote util]
	ClassUtilTeste 
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class UtilTeste {

	@Test
	public void Conectar(){
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javaweb", "root", "");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

//para rodar botao direita junit rum a
/*==================================================================*/
//JSF
/*==================================================================*/

https://docs.oracle.com/javaee/7/tutorial/webapp003.htm

<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://xmlns.jcp.org/xml/ns/javaee"
	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
	id="WebApp_ID" version="3.1">

	<!-- Nome da Aplicação -->
	<display-name>MavenProject</display-name>
	
	<context-param>
        <param-name>javax.faces.PROJECT_STAGE</param-name>
        <param-value>Development</param-value>
    </context-param>

    <!-- Servlet -->
	<servlet>
		<servlet-name>Faces Servlet</servlet-name>
		<servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>
	
	<servlet-mapping>
		<servlet-name>Faces Servlet</servlet-name>
		<!-- tipo arquivo usado -->
		<url-pattern>*.xhtml</url-pattern>
	</servlet-mapping>
</web-app>

//inseor a pagina dentro do webapp

//HEADER DA PGNA XTML
 

<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
xmlns:h="http://java.sun.com/jsf/html"
xmlns:f="http://java.sun.com/jsp/jstl/core"
xmlns:ui="http://java.sun.com/jsf/facelets"
xmlns:p="http://primefaces.org/ui">
	
<h:head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</h:head>
<h:body>
	
</h:body>
</html>

//Add projeto ao toncat

//====================================================================
//CURSO JAVA MAVEN
//====================================================================
//1 - Mavem Project
//2 - marcar opcao criar proijeto simples
//next 
//grouo ID [Pacote do projeto]
//artefact ID [Nome do Progeto]
//versao [1.0]
//Package [tipo do produto final]
//finish
//====================================================================
//Pom.xml [CONFIGURAR ARQUIVO POM]
//====================================================================

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>br.com.cursomaven</groupId>
	<artifactId>CursoMane</artifactId>
	<version>1.0</version>
	<packaging>war</packaging>

	<!-- Codificação dos caracteres -->
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>

	<!-- Parâmetros de execução -->
	<build>
		<!-- Nome do projeto empacotado -->
		<finalName>CursoMaven</finalName>

		<!-- Plugins -->
		<plugins>
			<!-- Compilador -->
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.3</version>

				<!-- versao compilador java -->
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
				</configuration>
			</plugin>
		</plugins>
	</build>

	<!-- Dependências -->
	<dependencies>
		<!-- Hibernate Core -->
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-core</artifactId>
			<version>4.3.11.Final</version>
		</dependency>
		
		<!-- MySQL Connector -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.36</version>
		</dependency>
	</dependencies>
</project>


//====================================================================
//CRIAR WEB.XML
//====================================================================
//main/webapp/WEB-INF/Web.xml
//====================================================================

<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://xmlns.jcp.org/xml/ns/javaee"
	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
	id="WebApp_ID" version="3.1">

	<!-- Nome da Aplicação -->
	<display-name>Drogaria</display-name>
</web-app>

//====================================================================
// CRIAR ARQUIVO HIBENATE.CFG.XML 
//====================================================================
//Dentro do pacote java Resouece/src/main/recoureces
//CONFIG HIBERNATE
//====================================================================
//COLOCAR ESSE ARQUIVO DENTRO DA PASTA/ SRC/MAIN/RESOURCE/[NOME_ARQUIVO.XML = hibernate.cfg.xml]
//====================================================================
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>

	<session-factory>

		<!-- Configurações de Conexão com o Banco de Dados -->
		<property name="connection.driver_class">com.mysql.jdbc.Driver</property>
		<property name="connection.url">jdbc:mysql://127.0.0.1:3306/maven</property>
		<property name="connection.username">root</property>
		<property name="connection.password">root</property>

		<!-- Pool de Conexões -->
		<property name="connection.pool_size">1</property>

		<!-- SQL dialect -->
		<property name="dialect">org.hibernate.dialect.MySQL5InnoDBDialect</property>

		<!-- Gerenciamento do Contexto das Sessões -->
		<property name="current_session_context_class">thread</property>

		<!-- Cache de Segundo Nível -->
		<property name="cache.provider_class">org.hibernate.cache.internal.NoCacheProvider</property>

		<!-- Mostra as SQLs Geradas -->
		<property name="show_sql">true</property>

		<!-- Cria as tabelas do banco de dados -->
		<property name="hbm2ddl.auto">create</property>
		
	</session-factory>
</hibernate-configuration>

//====================================================================
//CONEXAO COM O BANCO
//====================================================================
// HIBERNATE UTIL 
//====================================================================
//class criada para conectar ao banco
//====================================================================

package br.com.cursimaven.util;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HIbernateUtil {

	private static SessionFactory fabricaDeSessoes = criarFabricaDeSessoes();

	public static SessionFactory getFabricaDeSessoes() {
		return fabricaDeSessoes;
	}

	private static SessionFactory criarFabricaDeSessoes() {
		try {
			Configuration configuracao = new Configuration().configure();
			
			ServiceRegistry registro = new StandardServiceRegistryBuilder()
				.applySettings(configuracao.getProperties()).build();
			
			SessionFactory fabrica = configuracao.buildSessionFactory(registro);
			
			return fabrica;
		} catch (Throwable ex) {
			System.err.println("A fábrica de sessões não pode ser criada." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
}

//====================================================================
//TESTANDO
//====================================================================
package br.com.maven.main;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import br.com.maven.uitl.HibernateUtil;

public class HibernateUtilTeste {

	public static void main(String[] args) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		HibernateUtil.getFabricaDeSessoes().close();
	}
}


//====================================================================
// JUNIT [os pacotes precisam ser igaul, para realizar op teste]
//====================================================================
//instalar depencia pom.xml
//====================================================================

<!-- https://mvnrepository.com/artifact/junit/junit -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>

//====================================================================
//CRIAR CLASS JUNIT
//OS PACOTES PRECISAM SER DO MESMO NOME DA CLASSE A SER TESTADA
//====================================================================
//src/teste/java/[pacote util ex: br.com.nomeprojeto.util[mesmo nome do pacote onde contem a classe a ser testada]]
//====================================================================
package br.com.maven.util;
import org.hibernate.Session;
import org.junit.Test;
import br.com.cursimaven.util.HIbernateUtil;

public class HibernateUtilTeste {

	@Test
	public void conectar(){
		Session sessao = HIbernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
	}
}

//====================================================================
//PARA FUNCIONAR TUDO PRECISA FICAR VERDE
//BOTO DIREIRTO NA CLASS-->RUN AS --> JUNIT TESTE[DENTRO DA CLASS]
//VERDE OK
//VERMELHO ERROR
//VAI ABRIR UMA ABINHA COM JUNIT
//====================================================================

//====================================================================
// ENTIDADES [TABELAS DO BANCO]
//====================================================================
//Dentro do cacote/ src/main/entidade/classJava[GenericDomain]
//pojo --> class com atributos privados  e methodos get e setins 
//com anotacao do Hiber nate
//====================================================================

package br.com.maven.model;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass //not mapp table
public class GenericDomain implements Serializable{
	
	/**
	 * CPF do produto para corrigir wiarning
	 */
	private static final long serialVersionUID = 1L;
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.AUTO) //auto increment
	private Long codigo;
	
	public Long getCodigo(){
		return this.codigo;
	}
	
	public void setCodigo(Long codigo){
		this.codigo = codigo;
	}
}

//====================================================================
//CRIAR OUTRAS ENTIDADES
//====================================================================
package br.com.maven.model;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity //Criar uma tabela com nome Estado[nome da class]
public class Estado extends GenericDomain /*herda primary key*/{
	
	@Column(lenght = 2)//tmanho da coluna
	@Column(nullable=false)//campo obrigatorio[NOT NULL]

	private String sigla;

	@Column(lenght = 2)
	@Column(nullable=false)//campo obrigatorio[NOT NULL]
	private String nome;
	
	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}

//====================================================================
//DEBTRO DO AQUIVO HIBERNATE.CFG.XML ADD CAMPO PARA MAPEAR TABELA
//====================================================================

    <session-factory>    
        //=========== NOVO CAMPPO ADICIONADO ========================
        <!-- MAPEAMENTO CLASS COM @ENTITY-->
        <mapping  class="br.com.maven.model.Estado"/>
    </session-factory>
</hibernate-configuration>

//====================================================================
//EXECUTA TERSTE PARA CRIAR TABELA
//====================================================================

package br.com.maven.util;

import org.hibernate.Session;
import org.junit.Test;
import br.com.maven.uitl.HibernateUtil;

public class HiIberneteUtilTeste {

	@Test
	public void conectar(){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		HibernateUtil.getFabricaDeSessoes().close();
	}
}


//====================================================================
//DAO MAVEN
//====================================================================
//GENERIC DAO [MAPEA QUALQUER ENTIDADE E SALVA NO BANCO]
//====================================================================
package br.com.maven.dao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.maven.uitl.HibernateUtil;

public class GenericDao<Entidade> {
	
	public void salvar(Entidade entidade){
		//getConnection
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transaction = null;
		
		try{
			transaction = sessao.beginTransaction();
			sessao.save(entidade);
			transaction.commit();
			
		}catch(RuntimeException e){
			
			if(transaction!=null){
				transaction.rollback();//desfaz a transacao
			}
			throw e;			
		}
		finally{
			sessao.close();
			HibernateUtil.getFabricaDeSessoes().close();
		}
	}
}
//====================================================================
//EXECUTAR ENTIDADE  ESTADODAO
//====================================================================

package br.com.maven.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import br.com.maven.uitl.HibernateUtil;

public class GenericDao<Entidade> {
	
	public void salvar(Entidade entidade){
		
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transaction = null;
		
		try{
			transaction = sessao.beginTransaction();
			sessao.save(entidade);
			transaction.commit();
			
		}catch(RuntimeException e){
			
			if(transaction!=null){
				transaction.rollback();//desfaz a transacao
			}
			throw e;			
		}
		finally{
			sessao.close();
			HibernateUtil.getFabricaDeSessoes().close();
		}
	}
}
//====================================================================
package br.com.maven.dao;
import br.com.maven.model.Estado;
public class EstadoDao extends GenericDao<Estado> {
	
}
//====================================================================
//CRIA CLASSE PARA EXECUTAR TESTE PARA SALVAR
//====================================================================
//EXECUTA TESTE PARA SALVAR
//====================================================================
package br.com.maven.dao;
import org.junit.Test;
import br.com.maven.model.Estado;

public class EstadoDaoTeste {
	@Test
	public void salvar(){
		
		Estado estado = new Estado();
		estado.setNome("Sao paulo");
		estado.setSigla("SP");
		EstadoDao dao = new EstadoDao();
		dao.salvar(estado);
	}
}

//====================================================================
//criar um class tipo enum --> file--> java--> Enum
//====================================================================
public enum DiaDaSemana {
	SEGUNDA, TERCA, QUERTA, QUINTA, SEXTA, SABADO, DOMINGO;
}

public void imprimeDiaSemana( DiaDaSemana  dia){

	switch(dia){
		case SEGUNDA:
			System.out.println("Segunda Freita");
			break;
		case TERCA:
			System.out.println("Terca Freita");
			break;
	}
}

public static void main(String[] args) {
		
		DiaDaSemana dia = DiaDaSemana.DOMINGO;
		//e.imprimeDiaSemana(dia);
		
		DiaDaSemana[] dias = DiaDaSemana.values();
		for(int i=0;i<dias.length;i++){
			System.out.println(dias[i]);
		}
		
		DiaDaSemana dia1 = Enum.valueOf(DiaDaSemana.class, "SABADO");
		
		//obter o valor expecifico
		System.out.println(dia1);		
}



/------------------------------------------------------------------------------------
	ENUM COM CONSTRUTOR E METHODO ABSTRADO
-------------------------------------------------------------------------------------/

public enum Oporacao {
	SOMAR("+") {
		@Override
		public double executarOperacao(double x, double y) {
			return x+y;
		}
	}, SUBTRAIR("-") {
		@Override
		public double executarOperacao(double x, double y) {
			return x-y;
		}
	}, MULTIPLICAR("*") {
		@Override
		public double executarOperacao(double x, double y) {
			return x*y;
		}
	}, DIVIDIR("/") {
		@Override
		public double executarOperacao(double x, double y) {
			return x/y;
		}
	};
	
	private String simbolo;
	
	Oporacao(String simbolo) {
		 this.simbolo = simbolo;
	}
	
	@Override
	public String toString() {
		return simbolo;
	}
	
	public abstract double executarOperacao( double x, double y);
}

//MAIN
double x = 2.0;
double y = 7.3;
		
for(Oporacao op : Oporacao.values()){
	System.out.print(x +" ");
	System.out.print(op.toString()+ " ");
	System.out.print(y+ " ");
	System.out.println("= "+ op.executarOperacao(x, y));
}


/------------------------------------------------------------------------------------
	WRAPPERS --> CLASS COM VARIAVEOS COM TIPOS PRIMITIVOS
-------------------------------------------------------------------------------------/

Short short1 = new Short((short) 1);
Byte byte1 = new Byte((byte)10);
Long long1  = new Long(1000);
Double double1 = new Double(3.555);
Boolean flag = new Boolean(true);
Character character = new Character('q');
Integer integer = new Integer(1231);


/------------------------------------------------------------------------------------------------
	AuntoBoxingUnboxing -->; trnasfornar um tipo priitivo em um objeto
	nada mas e que convercao de tipos primitivos de dado para variaveis simples
---------------------------------------------------------------------------------------------------/

	Short short1 = 1;//new Short((short) 1);
		Byte byte1 = 2;//new Byte((byte)10);
		Long long1  = 3l;//new Long(1000);
		Double double1 =23.3;// new Double(3.555);
		Boolean flag = false;//new Boolean(true);
		Character character = 'a';//new Character('q');
		Integer integer = 12;// new Integer(1231);
		
		///auto un-box -- trnanforna int em Integer
		int xx = integer;
		
		//autoboxing
		xx++;
		
		//auto un-boxing
		Integer nws = xx/integer;

/-----------------------------------------------------------------------------------------------------------
	STATIC IMPORT 
------------------------------------------------------------------------------------------------------------/

//Usando import statico (OBS: mas utilizado)
import static java.lang.Math.pow;

//ou  (OBS: nao e muito utilizado)
import static java.lang.Math.*;


public class TesteStaticImport {

	public static void main(String[] args) {

		double a = 1;
		double b = 2;
		double c = 3;
		
		//todosos methodos da calss Math sao estaticos
		System.out.print(Math.pow(a, b));
		System.out.print(pow(a,b));//basta colocar apenas o Pow ao inves de Math.pow
		
	}
}


/-----------------------------------------------------------------------------------------------------------
	VARARGS --> NOME DE RECUROS ONDE PASSA INFINITOS PARAMNTROS SEM SER DELCARADOS
------------------------------------------------------------------------------------------------------------/

//pode ser utilizados varios parametrs sem ser delcarados
public class Varagrs {

	public static void main(String[] args) {
		
		System.out.print(soma(2,3));
		
		int[] vet ={1,2,3};
		System.out.print(somaVet(vet));
		
		//methodos varas
		System.out.print(somaVarargs(1,2,3,4));
	}
	
	public static int soma(int a, int b){
		return 1;
	}
	
	//Methodo do tipo Varargs [obs: caso queiora passar mas paramtros e opreciso colocar ants do varargs)
	//EX: public static int somaVarargs(int a, int b, Integer... integer)
	//System.out.print(somaVarargs('1','2' -- nao sera consoderado ,3,4));
	//isso e utuizado qunado os parametros nao forem passados
	
	public static int somaVarargs(Integer... integer){
		
		int total = 0;
		
		for(int i=0;i<integer.length;i++){
			total+=integer[i];
		}
		
		return total;
	}
	
	public static int somaVet(int[] vetor){
		int total = 0;
		
		for(int i=0;i<vetor.length;i++){
			total+=vetor[i];
		}
		
		return total;
	}
}


/-----------------------------------------------------------------------------------------------------------
	CLASS INTERNA E EXTERNA
------------------------------------------------------------------------------------------------------------/


public class Externa {

	private String texto= "Texto externo";

	public class Interna{
		private String texto ="texto Interno";
		public void ImprimeTexto(){
			System.out.println(texto);
		}
	}
}

O TEXTO QUE SERA EXIBIDO E DA CLASS INTERNA
AS CALSS INTERNAS SAO SAO UTILIZADAS DENTRO DA CLASS INTERNA


/-----------------------------------------------------------------------------------------------------------
	ANOTACOES 
------------------------------------------------------------------------------------------------------------/

@Override --> sobrecrever os methodos

criar uma class
@interface Anotacao {

	//informaceos sobre o autor
	String autor() default "Ednei de Freitas";
	int aulaNumero() default 12;
	String email() default"edneifneto@gmail.com";
	String blog() default "nomedosite";
	String site() default "xxx";
}

//inserir uma anotacao
@Anotacao
public static void main(String[] args) {
}


/-----------------------------------------------------------------------------------------------------------
	THREADS 
------------------------------------------------------------------------------------------------------------/



/-----------------------------------------------------------------------------------------------------------
	JSP 
------------------------------------------------------------------------------------------------------------/

//-------------------------------------------------------------------------------------------------------------
//CLASS ALUNO - USAR GET E SETTINGS PARA MODELAR BEANS
//-------------------------------------------------------------------------------------------------------------

public class Aluno {
    
    private int matricula;
    private String nome;
    private String email;

    public Aluno(int matricula, String nome, String email) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
    }
    
    public Aluno(){}

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    } 
}


//-------------------------------------------------------------------------------------------------------------
//CONEXAO COM O BANCO
//-------------------------------------------------------------------------------------------------------------

public class ConnectionFActory {
    
    private static final String URL= "jdbc:mysql://localhost/javaweb";
    private static final String USER="root";
    private static final String PASS="";
   
    public static Connection conectar() throws ClassNotFoundException, SQLException{
        
        Class.forName("com.mysql.jdbc.Drirver");
        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        return conn;
    }
}

//-------------------------------------------------------------------------------------------------------------
//CAMADA DAO - INSERT, UPATE, SELECT
//-------------------------------------------------------------------------------------------------------------


public class Dao {
    
    
    private Connection conn;
    
    public void insert(Aluno a) throws ClassNotFoundException, SQLException{
        
        //cria coenxao
        conn = ConnectionFActory.conectar();
        
        StringBuilder sql = new StringBuilder();
        sql.append("INSER INTO aluno");
        sql.append("VALUES (?, ?, ?)");
        
        PreparedStatement ps = conn.prepareStatement(sql.toString());
        ps.executeUpdate();
        
        ps.close();
        conn.close();
    }
    
    
    public List<Aluno> lista(Aluno a) throws SQLException, ClassNotFoundException{
        
        ///cria a conexao
        conn = ConnectionFActory.conectar();
        
        List<Aluno> alunos = new ArrayList<>();
        
        String sql = "SELECT matricula, nome, email FROM aluno";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, Integer.toString(a.getMatricula()));
        ps.setString(2, a.getNome());
        ps.setString(3, a.getEmail());
        
        ResultSet res = ps.executeQuery();
        
        while(res.next()){
            
            Aluno aluno = new Aluno();
            aluno.setMatricula(res.getInt("matricula"));
            aluno.setNome(res.getString("nome"));
            aluno.setEmail(res.getString("email"));
            
            alunos.add(aluno);
        }
        
        return alunos;   
    }
    
}


//-------------------------------------------------------------------------------------------------------------
//CRONTROOLER PARA MANIPULAR OS DADOS - GERALMENTE O CONTROLLER SERA UM SERVLET
//-------------------------------------------------------------------------------------------------------------


public class CodastroController extends HttpServlet {

    private Dao dao;
    private Aluno aluno;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            //recebe os dados via get
            String matricula = request.getParameter("matricula");
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");

            aluno = new Aluno();
            aluno.setMatricula(matricula);
            aluno.setNome(nome);
            aluno.setEmail(email);
            dao = new Dao();
            
            try {
                dao.insert(aluno);
                response.sendRedirect("Lista.jsp");
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println(ex.getMessage());
                out.println(ex.getMessage());
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}


//-------------------------------------------------------------------------------------------------------------
//JSP VINCULADO COM JAVA 
//-------------------------------------------------------------------------------------------------------------

//FORULARIO PARA O CADASTRO
//-------------------------------------------------------------------------------------------------------------
<%-- 
    Document   : index
    Created on : 25/10/2017, 10:49:11
    Author     : Ednei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- cria o bean para usar as class--->
<!DOCTYPE html>
<html>
    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>Cadastro</title>
    </head>
    <body>

        <div class="container" >
            <h1>Cadastro de clientes</h1>
            <hr />

            <form action="./CodastroController"> 
                <div class="form-group col-sm-12">
                    <label for="email">Matricula</label>
                    <input type="text " class="form-control" id="email" name="matricula">
                </div>
                <div class="form-group col-sm-12">
                    <label for="email">Nome:</label>
                    <input type="text" class="form-control" id="email" name="nome">
                </div>
                <div class="form-group col-sm-12">
                    <label for="email">E-mail</label>
                    <input type="text" class="form-control" id="email"  name="email" >
                </div>
                
                <div class="form-group col-sm-12">
                     <button type="submit" class="btn btn-default">Cadastrar</button>
                </div>
               
            </form>
        </div>
    </body>
</html>


//LISTAR OS DADOS
//----------------------------------------------------------------------------------------------
<%-- 
    Document   : Lista
    Created on : 25/10/2017, 11:24:04
    Author     : Ednei
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


// ID = USADO PARA PEGAR OS ARTIBUTOS, CLASS = IMPORTA A CLASS E SESU METODOS
//----------------------------------------------------------------------------------------------
//${aluno.matricula} --> Recupera os dados via get
//${dao.listar()}--> TEM ACESSO AO METHODO DENTRO DA CLASS


//------------FOR-------------------------------------/ 

//<c:forEach var="aluno" items="${dao.lista()}" >
//${aluno.matricula}</td>
//${aluno.nome}</td>
//${aluno.email}</td>




</c:forEach>

<jsp:useBean id="aluno" class="model.Aluno"/>
<jsp:useBean id="dao" class="dao.Dao"/>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>Lista de Usuario</title>
    </head>

    <body>

        <div class="container">
            <h1>Lista de Usuario</h1>
            <table class="table">
                <tr>
                    <td>Matricula</td>
                    <td>Nome</td>
                    <td>E-mail</td>
                </tr>
                <c:forEach var="aluno" items="${dao.lista()}" >
                    <tr>
                        <td>${aluno.matricula}</td>
                        <td>${aluno.nome}</td>
                        <td>${aluno.email}</td>
                    </tr>
                </c:forEach>
            </table>
    </body>
</div>

</html>

//===================================================================================
//CRUD
//===================================================================================
//MODEL
//===================================================================================

package util.test.model;

public class UsuarioUtil {
    private int codigo;
    private String nome;
    private String email;

    //GETTER E SETTING
    @Override
    public String toString() {
        return String.format("%s, %s, %s", getCodigo(), getNome(), getEmail()); //To change body of generated methods, choose Tools | Templates.
    }
}

//===================================================================================
//CONNECTIONFACOTRY
//===================================================================================
package util.test.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryUtil {

    public static Connection conexao() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/javadb", "root", "");
        return conn;
    }
}

//===================================================================================
//INTERFACES GENERICAS
//===================================================================================
package util.teste.interfaces;

import java.util.List;

public interface EntiadadesDAOUtil<T> {
    
    public void insert(T t);
    public void update(T t);
    public void delete(T t);
    public List<T> select();
}

//===================================================================================
//IMPLEMENTACAO DA INTERFACES
//===================================================================================
package util.test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.test.factory.ConnectionFactoryUtil;
import util.test.model.UsuarioUtil;
import util.teste.interfaces.EntiadadesDAOUtil;

public class UsuarioDaoUtil implements EntiadadesDAOUtil<UsuarioUtil> {

    private PreparedStatement ps;
    private Connection conn;

    @Override
    public void insert(UsuarioUtil user) {

        try {
            //create connection
            conn = ConnectionFactoryUtil.conexao();
            String sql = "INSERT INTO usuario (nome, email) VALUES(?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getNome());
            ps.setString(2, user.getEmail());
            ps.execute();

            ps.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDaoUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDaoUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(UsuarioUtil user) {

        try {
            //create connection
            conn = ConnectionFactoryUtil.conexao();
            String sql = "UPDATE usuario SET nome = ?, email= ? WHERE codigo = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getNome());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getCodigo());
            ps.execute();

            ps.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDaoUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDaoUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(UsuarioUtil user) {
        try {
            //create connection
            conn = ConnectionFactoryUtil.conexao();
            String sql = "DELETE FROM usuario  WHERE codigo = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getCodigo());
            ps.execute();

            ps.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDaoUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDaoUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<UsuarioUtil> select() {

        List<UsuarioUtil> usuarios = new ArrayList<>();
        try {
            conn = ConnectionFactoryUtil.conexao();
            String sql = "SELECT * FROM usuario ";
            ps = conn.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            
            while (res.next()) {
                UsuarioUtil user = new UsuarioUtil();
                user.setCodigo(res.getInt("codigo"));
                user.setNome(res.getString("nome"));
                user.setEmail(res.getString("email"));
                usuarios.add(user);
            }

            ps.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDaoUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDaoUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return usuarios;
    }

}


//===================================================================================
//SERVLET PARA EXECUTAR
//===================================================================================
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.test.dao.UsuarioDaoUtil;
import util.test.factory.ConnectionFactoryUtil;
import util.test.model.UsuarioUtil;

@WebServlet(name = "Test", urlPatterns = {"/Test"})
public class TestServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            Connection conn = ConnectionFactoryUtil.conexao();
            UsuarioUtil user = new UsuarioUtil();
            UsuarioDaoUtil dao = new UsuarioDaoUtil();

            user.setCodigo(1);
            user.setNome("Mario jose");
            user.setEmail("edneifneto@gmail.com");
            //dao.update(user);
            dao.delete(user);
            List<UsuarioUtil> usuarios = dao.select();
            
            out.print("<ul>");
            for (int i = 0; i < usuarios.size(); i++) {
                out.print("<li>"+usuarios.get(i).getCodigo()+"-"+ usuarios.get(i).getNome() +" - " +usuarios.get(i).getEmail()+"</li>");
            }
            out.print("</ul>");

        } catch (SQLException ex) {
            Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
            out.println("SQLException " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            out.println("ClassNotFoundException " + ex.getMessage());
            Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
            out.println("rodando");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}






