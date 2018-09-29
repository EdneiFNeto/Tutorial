//================================================================
//=================== CURSO ANGULAR 5 ============================
//================================================================

//================================================================
//=================== MODULO TYPE SCRIPT =========================
//================================================================

//================================================================
//grar auquivo js puro: tsc interface.ts[cmd]
//================================================================
//informar ao vs code que e um projeto typescript:
//criar arquivo tsconfig.json
//================================================================
{

    "compilerOptions":{
        "target": "es5",
        "module": "commonjs",
        "sourceMap": true,
        
    }
}
//================================================================
//Tarefa para deixar vs code compilar type script:
//ctrl+shift+p -->configure task-->compilar ts.json
//ctrl+shift+b monitorar arquivo
//OBS: AUTOMATIZA O COMPILADOR DO PROJETO
//================================================================
//SOURCE MAP 
//[DEBUGA LINHA E ARQUIVO ONDE ESTA O ERRO, INFORMA ONDE ESTA O ERRO NO ARQUIVO]
//================================================================
//OCULTAR ARQUIVO APOS A COMPILACAO
//PREFERENCES-->SETTINGS: 
//no canto direoto digite 
//================================================================
{
    "files.exclude": {
        "**/.git": true,
        "**/.svn": true,
        "**/.hg": true,
        "**/CVS": true,
        "**/.DS_Store": true,
        "**/*.js":{
            "when": "$(basename).ts" //esconde apenas arquinos com mesmo nome
        },
        "**/*.js.map":true
    },
    "workbench.iconTheme": "vscode-icons",
    "vsicons.projectDetection.autoReload": true,
    "window.zoomLevel": 0,
    "editor.minimap.enabled": false
}
//================================================================
//========= CLASSES E HERANCES E SOBRESCRITAS DE METHODO =========
//================================================================

//export diz qe pode ser usada em qualquer lugar
export class Pessoa{

    private name:string;
    
    constructor(name:string){
        this.name = name;
    }
}
//================================================================
//================== INSTANCIA ===================================
//================================================================
import { Pessoa } from './Pessoa';

//instancia da classe
let pessoa = new Pessoa("Ednei");
//compilar :ctrl+shift+b --> compilar arquivo
//EXECUTAR NODE NOMEARQUIVO.TS
//================================================================
//====================== HERANCA =================================
//================================================================
import { Pessoa } from './Pessoa';

export class Student extends Pessoa{

    constructor(name:string){
        super(name);
    }

    //sobrescrita de methodo
    public showIdade(age:number):void{
        console.log("Sua idade");
        super.showIdade(25);
    }

}

//================================================================
//======================= INERFACES ==============================
//================================================================

//================================================================
//CRIAR A INGERFACE
//================================================================
export interface DaoInerface{
    tableName:string;
    insert(object:any):boolean;
    update(object:any):boolean;
    delete(id:number):boolean;
    find(id:number):any;
    findAll():[any];//return array de qualquer tipo
}

//================================================================
//CLASS RESPONSAVEL PELA A IMLEMENTACAO DA INTERFACE
//================================================================
import { Pessoa } from './../classes/Pessoa';
import { DaoInerface } from "./dao.interfaces";

export class PessoaDao implements DaoInerface {

    tableName: string;
    insert(pessoa: Pessoa): boolean {
        return true;
    }

    update(pessao: Pessoa): boolean {
        return true;
    }

    delete(id: number): boolean {
        return true
    }

    find(id: number): Pessoa {
        return null;
    }

    findAll(): [Pessoa] {
        return [new Pessoa('ednei')];
    }
}

//================================================================
//CLASS EXECUTAR [MAIN]
//================================================================
import { Pessoa } from './../classes/Pessoa';
import { PessoaDao } from './PessaoDao';
import { DaoInerface } from './dao.interfaces';

let pessoaDao:DaoInerface = new PessoaDao();
let pessoa:Pessoa = new Pessoa('Ednei');

pessoaDao.insert(pessoa);
pessoaDao.update(new Pessoa('ednei'));
pessoaDao.delete(1);
pessoaDao.find(1);

//================================================================
//=======================  FIM ===================================
//================================================================

//================================================================
//=================== GENERICS [ REUSO] ==========================
//================================================================

export interface GenericInterfaces<T>{
    
    tableName:string;

    insert(object:T):boolean;
    update(object:T):boolean;
    delete(id:number):boolean;
    find(id:number):any;
    findAll():[T];//return array de qualquer tipo
}

//================================================================
//CLASS QUE IMPLEMENTA A INTERFACE
//================================================================
import { Pessoa } from "../classes/Pessoa";
import { GenericInterfaces } from "./GenericsInterfaces";


export class Dao<T> implements GenericInterfaces<T>{

    tableName: string;    
    
    insert(object: T): boolean {
        return true;
    }

    update(object: T): boolean {
        return true;
    }

    delete(id: number): boolean {
        return true;
    }

    find(id: number):T {
        return null;
    }

    findAll(): [T] {
        return [null];
    }

}

//================================================================
//CLASS QUE EXECUTA INGERFACE
//================================================================

import { Dao } from './Dao';
import { Pessoa } from '../classes/Pessoa';
import { GenericInterfaces } from './GenericsInterfaces';

let dao:Dao<Pessoa> = new Dao<Pessoa>();
let pessoa:Pessoa = new Pessoa('Ednei');

dao.insert(pessoa);
dao.update(pessoa);
dao.delete(1);
dao.find(1);
dao.findAll();

//================================================================
//let dao2 = new Dao<OtherObject>(); INSTANCIA OUTRO OBJETO
//================================================================

//================================================================
//======================= FIM ====================================
//================================================================

//================================================================
//================= MODULO ANGULAR ===============================
//================================================================
//NG NEW -->CRIAR
//NG GENERATE-->GERAR
//NG SERVE --> RODAR

//================================================================
//CRIAR PROJETO
//ng new projeto_angular
//================================================================
//NG FOR
//================================================================
<h1>Bem vindo {{title}}</h1>
<br>
<input type="text"  [(ngModel)]="task">
<button type="button"  (click)="Add()">Add lista</button>

<ul>
  <li *ngFor="let item of tasks">{{item}}</li>
</ul>

<!-- item e a variavel-->
<!-- tasks array declarado no arquivo app.componets.ts-->
<!-- [(ngModel)] diretiva de formulario OBS: PRECISA SE IMPORTADA NO  APP.MODULE-->
<!--obs: [(ngModel)]  --> serve para pegar o valir do input-->
//================================================================

import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  tasks = [];
  title = 'Tasks';
  task = "";

  Add():void{
    this.tasks.push(this.task);
  }
}
//================================================================
//SCRIPT APP.MODEL[IMPORT FormsModule]
//================================================================
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';

//IMPORTA PARA UTILIZAR [(ngModel)] 
import{ FormsModule} from '@angular/forms';//ADD

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule //ADD
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

//================================================================
//==================== FIM =======================================
//================================================================



//================================================================
//COMPONENTS
//INSEIR O CONTEUDO DE UMA PAGINA EM OUTRA
//================================================================
//ng g c taks-list
//================================================================
//ADD SELECTOR NA PAGINA 
//================================================================
<h1>Bem vindo {{title}}</h1>
<br>
<app-taks-list></app-taks-list><!-- selector task-->
//================================================================
//ARQUIVO.HTML
//================================================================
<h2>Taks component</h2>
<input type="text"  [(ngModel)]="task">
<button type="button"  (click)="Add()">Add lista</button>
<ul>
  <li *ngFor="let item of tasks">{{item}}</li>
</ul>
//================================================================
//ARQUIVO.TS
//================================================================
import { Component } from '@angular/core';

@Component({
  selector: 'app-taks-list',//CODIGO QUE VAI NO ARQUIVO HTML
  templateUrl: './taks-list.component.html',
  styleUrls: ['./taks-list.component.css']
})
export class TaksListComponent {

  tasks = [];
  task = "";
  Add():void{ this.tasks.push(this.task);}
}
//================================================================
//==================== FIM =======================================
//================================================================


//================================================================
//PRPOERTY BINDING
// [nome]
//================================================================
//ARQUIVO.HTML
//================================================================

<h2>Taks component</h2>
<input type="text"  [(ngModel)]="task">
<button type="button"  (click)="Add()">Add lista</button>

<!-- Porperty bind lista-->
<ul>
  <li *ngFor="let item2 of tasks" [innerHTML]="item2"></li>
</ul>
<br>
<!-- se for true nao exibe texto-->
<!--[hidden] escpmde-->
<div [hidden]="isAdmin">
  User
</div>

<!-- se for falso exibe -->
<div [hidden]="!isAdmin">
  Administrador
</div>
//================================================================
//ARQUIVO.TS
//================================================================
import { Component } from '@angular/core';

@Component({
  selector: 'app-taks-list',
  templateUrl: './taks-list.component.html',
  styleUrls: ['./taks-list.component.css']
})
export class TaksListComponent {

  tasks = [];
  task = "";
  isAdmin = true;//properti bidy

  Add():void{this.tasks.push(this.task);}
}

//================================================================
//==================== FIM =======================================
//================================================================


//================================================================
//PIPES
//TRANFORMAR TEXT, FORMAT NUM & DATA
//================================================================
//ARQUIVO.HTML
//================================================================
<h1>Bem vindo {{title}}</h1>
<br>

<hr>
<!-- chama arquivo task.html-->
<app-taks-list></app-taks-list>

<hr>
<h1>PIPES</h1>
<p>{{ user | json }}</p>
<p>{{ textUppercase| uppercase }}</p>
<p>{{ textLowercase| lowercase }}</p>
<p>{{ numPercent| percent }}</p>
<p>{{ date | date: 'yyy/MM/dd'}}</p>
<p>{{ moeda | currency}}</p>
<hr>
//================================================================
//ARQUIVO.TS
//================================================================

import { User } from './model/user';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  textUppercase:string  = 'Uppercase';
  textLowercase:string  = 'textLowercase';
  numPercent:number     = 0.5;
  moeda:number          = 10.00;
  date:Date = new Date();//DATA ATUAL

  //CLASS USER
  user:User = {name:"Ednei",idade:35}
}

//================================================================
//==================== FIM =======================================
//================================================================

//================================================================
//DIRETIVAS
//COMPONENTS, ESTRUTURAOAIS E ATRIBUTOS
//================================================================
//DIRETIVA ESTRUTURAL : ngIf, ngSwitch
//================================================================
//ARQUIVE.HTML
//================================================================
<!-- se for true-->
<div *ngIf="isAdmin">
    <p>Exibe conteudo</p>
</div>

<div [ngSwitch]="profile">
    <p *ngSwitchCase="2">Opcao 2</p>
    <p *ngSwitchCase="3">Opcao 3</p>
    <p *ngSwitchCase="4">Opcao 4</p>
    <p *ngSwitchDefault>Nenhuma opcao</p>
</div>

//================================================================
//ARQUIVE.TS
//================================================================

import { User } from './model/user';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
  isAdmin:boolean = true;
  profile:number = 212;
}
//================================================================
//==================== FIM =======================================
//================================================================

//================================================================
//SERVICOS
//DECLARADOS NOS PRIVERES FICA EXPOSTOS PARA TODOS OS COMPONENTS
//VIEW PRIVEDER FICA VISIVEL AOPENAS O COMPONENTS ESPECIFICO
//================================================================
//URL--> jsonplaceholder.typicode.com/comments
//CRIAR O COMPONENT: ng g c commnets
//================================================================
//CRIAR OS SERVICOS: ENTRA NA PASTA COMMNENS: NG G S COMMNENTS
//[OBS: O NOME DO SERVICEO PRECISA SER O MESMO DO COMPONENT]
//================================================================

//================================================================
//CRIAR A CLASSE MODEL
//================================================================
export class Comment{

    private id:number;
    private name:string;
    private email:string;
}

//================================================================
//EDITAR SCRIPT  SERVICE[comment.service.ts]
//================================================================
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Comment } from './commetn.model';
import { Observable } from 'rxjs/Observable';
import  'rxjs/add/operator/map';

@Injectable()
export class CommentService {

  constructor(private http:Http) { }

  getComment(): Observable<Comment[]>{
    return this.http.get('http://jsonplaceholder.typicode.com/comments')
      .map(response=>response.json());

  }
}
//================================================================
//i//nstall npm install rxjs-compat [tirar erro]
//================================================================
//EDITAR SCRIPT COMPONENT [comment.component.ts]
//================================================================
import { Component, OnInit } from '@angular/core';
import { CommentService } from './comment.service';
import { Comment } from './commetn.model';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {

  
  comments:Comment [];//instancia da class
  constructor(private commentService:CommentService) { }

  //[OBS:ngOnInit-->executado assim que a aplicao for aberta]
  //chama  metodo da ckasse servico[usa o subcribe para ]
  ngOnInit() {this.commentService.getComment().subscribe(comments=>this.comments=comments);}
}

//================================================================
//EDIRAR SCRIPT APP.MODULE[IMPORT HTTPMODULE]
//================================================================

import { CommentService } from './comment/comment.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { CommentComponent } from './comment/comment.component';

import { HttpModule } from '@angular/http';//ADD

@NgModule({
  declarations: [
    AppComponent,
    CommentComponent
  ],
  imports: [
    BrowserModule,
    HttpModule//ADD
  ],
  providers: [
    CommentService//ADD[OBS: PODERAR SER UTILIZADOS POR TODOS]
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
//================================================================
//COMMNET.HTML
//================================================================
<div>
  <ul *ngFor="let comment of comments">
    <li>{{comment.id}}-{{comment.name}} - {{comment.email}}</li>
  </ul>
</div>
//================================================================
//APP.HTML[ADD SELECTOR DO COMPONENT]
//================================================================
<app-comment></app-comment>

//================================================================
//==================== FIM =======================================
//================================================================


//================================================================
//VALIDAR FORMULARIO
//================================================================
//VALID INVALID
//PRISTNE DIRTY: QUANDO NADA FOR DIGITADO
//TOUCHED UBTOUCHE:QUANDO O USUARIO ENTRA NO CAMPO
//================================================================
<!-- 
  [INPUT]
  referencia para sabar o estado do campo
  #nameVerify="ngModel"

  [FORM]
  referencia para ngForm: #form="ngForm"
-->
<form #form="ngForm" >
	<div>
	  <label for="name">Name</label>
	  <input type="text" id="name" name="name" [(ngModel)]="name" #nameVerify="ngModel" required>
	  <span *ngIf="nameVerify.invalid">*Name is required</span>
	</div>
	<br>
	<div>
	  <label for="email">Email</label>
	  <input type="text" id="email" name="email" [(ngModel)]="email"  #emailVerify="ngModel" email>
	  <span *ngIf="emailVerify.invalid">Digite um email valido</span>
	</div>
	<br>
	<!-- se o formulario nao for valida desabilita p botao-->
	<button type="submit" [disabled]="!form.valid">Enviar</button>
</form>

//================================================================
//USADO DIRETIVAS PARA O FRMULARIO
//================================================================
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-valida-formulario',
  templateUrl: './valida-formulario.component.html',
  styleUrls: ['./valida-formulario.component.css']
})
export class ValidaFormularioComponent implements OnInit {

  name:string  = "";
  email:string = "";
}

//================================================================
//==================== FIM =======================================
//================================================================


//================================================================
//SPRING BOOT
//================================================================
//STARTECE-->DEPENDECIA QUE AGRUPA OUTRAS DEPENDENCIA
//================================================================
//CRIAR PROJETO VIA LINK: https://start.spring.io/ 
//================================================================

//ACESSAR LINK: https://start.spring.io/
//PREENCHER OS CAMPOS:
//GROUP:NOME DO PACOTE[COM.NOME_PROJETO]
//ARTEFACT:NOME DO PROJETO[NOME_PROJETO]
//CLICAR NO LINK:Switch to the full version.
//MARCAS AS DEPENDENCIAS: 
//CORE: DEVTOOLS[EVITA DE PARAR A APLICAO APOS QUALQUER ALTERCAO]
//WEB
//CLIACAR EM GENERATE PROJECT
//DESCOMPACTAR O PROJETO E COLAR NO WORKSPACE
//VAI NO SPRING TOOLS-> FILE-->IMPORT-->
//SELECIONE[MAVEN: PRJECT EXISTENTE]-->
//SELECIONE A PASTA DO PROJETO-->FINISH

//================================================================
//CRIAR PROJETO NO SPRING TOOLS
//================================================================

//FILE-->NEW -->SPRING START PROJECT
//Name: NOME_PROJETO
//GROUP:[NOME DO PACOTE]
//ARTEFACT: NOME_PROJETO
//PACKEGE: NOME_DO_PACOTE.NOME_PROJETO
//NEXT
//EM AVALIABLE[FILTRO PARA DEPENDENCIAS]
//DIGETE [CORE]DEVTOOL, [SQL]H2, JPA[SQL], WEB[WEB], THYMELIF[TEMPLATE]
//NEXT-->FINISH
//================================================================
//CRIAR UM CONTROLLE:
//CRIAR UM PACOTE DENTRO DE SRC: CONTROLLER
//CRIAR A CLASS DETRO DO PACOTE:
//OBS: AS CLASSES JAVA PRECISAM ESTA NO MEMO PACITE
//================================================================
package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringBootController {

	@RequestMapping("/showText")//sera chamado na url
	public String showTest() {
		return "hello SpringBoot";
	}
}
//================================================================
//RODAR O PROJETO--> DENTRO DA CLASS APPLICATION --> RUN SPRING BOOT
//================================================================
package com.ednei.springBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
//================================================================
//==================== FIM =======================================
//================================================================

//================================================================
//SPRING SECURITY
//================================================================
//POM.XML--> ADD DEPENDENCIAS
//================================================================
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
//================================================================
//CLASS JAVA PAA SOLICITAR SEGURANCA NO ACESSO
//================================================================
package com.example.springboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.GET, "/").permitAll()//permite passar dados via get
		.anyRequest().authenticated()
		.and().formLogin().permitAll()//Asses page login
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")); 
	}
	
	//asses in memory
	@Override
	protected void configure(AuthenticationManagerBuilder outh) throws Exception{
		outh.inMemoryAuthentication()
		.withUser("user").password("123").roles("ADMIN");
	}
	
	//enabled static pages
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/style/**");	
	}	
}


//================================================================
//MONGO DB [mongode.com]
//INSTALL MONGO DB COMPESS[ ABA COMPESS]
//OBS: APOS A INSTALACAO VAI NO CMD E DIGITE MONGOD PARA INICIA O SERVICO
//DEPOIS CONNECT O MONGO COMPESS
//================================================================
//instalar a variavel de ambiente do mongo: [C:\Program Files\MongoDB\Server\4.0\bin]
//criar pasta  do reiretorio: [C:\data\db\]
//ACBARIR O SERVIDOR DO MONGO: mongod
//[OBS:waiting for connections on port 27017]
//[OBS: ABRIR OUTRA JANELA DE COMANDO]
//================================================================
//use school
// db.student.insert({name:'endei', age:22} )
//listar registro
//db.student.find()

//================================================================
//INSERT ARRAY JSON
//db.student.insert([{name:'ana', age:29 }, {name:'jose', age:55 }] )

//================================================================
//identa
//================================================================
//db.student.find().pretty()
//================================================================
//================================================================
//FILTROS: 
//================================================================
//db.student.find({name:'jose'} ).pretty()
//================================================================
//UPDATE:[PRIMEIRO E FILTRO, SEGUNDO : ALTERACAO] [OBS: NAO E RECOMENDADO]
//db.student.update({ name:'jose'},{name:'xxx', age:78 })
//================================================================
//db.student.updateOne({name:'jose'}, {$set:{age:100} })
//================================================================
//REMOVER
//db.student.remove({_id: ObjectId("5b5f5879f641520e9f89ee9c") })
////db.student.find().pretty()
//================================================================


//================================================================
//POSTMAN[ https://www.getpostman.com/apps]
//TESTAR SERVICOS
//================================================================


//================================================================
//SPRING DATA
//SIMPLIFICAR ACESSO A TECNOLOGIA DE COMGO [MONGO DB]
//LIBRARY: SPRING DATA MONGO.BD
//LINK:https://docs.spring.io/spring-data/mongodb/docs/2.1.0.M3/reference/html/
//================================================================
//CRIAR PROJETO-->PRING SWITCH
//CONEXAO COM MONGO DB:
//ABRA PASTA/SRC/MAIN/RESOURCE/ APPLICATION.PROTERTIES

//================================================================
//ARQUIVO APPLICATION.PROPERTIE
//================================================================
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=school
//================================================================
//CRIAR A ENTIDADE
//================================================================
package com.edneispringdata;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Student {
	
	@Id
	private String id;
	private String name;
	private int age;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}

//================================================================
//CRIAR INTERFACE DO USUARIO
//================================================================

package com.edneispringdata;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface StudentRepository extends MongoRepository<Student, String> {
	
}

//================================================================
//CRIAR SERVICOS
//================================================================
package com.edneispringdata;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentContoller {

	@Autowired
	StudentRepository studentRepository;
	
	public static String ID;

	// service[return all student]
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public List<Student> listStudent() {
		return this.studentRepository.findAll();
	}

	// service[insert new student]
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	public Student saveStudent(@RequestBody Student student) {
		return this.studentRepository.save(student);
	}

	//consulta por nome [http://localhost:8080/student/an/name]
	//pesquisar todos que comece por an
	@RequestMapping(value = "/student/{name}/name", method = RequestMethod.GET)
	public List<Student> listStufindByNamedent(@PathVariable String name) {
		return this.studentRepository.findByNameLikeIgnoreCase(name);
	}

	/*// service[search id] [error]
	@RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
	public Optional<Student> findById(@PathVariable String id) {
		return this.studentRepository.findOne(id);
	}*/
}


//================================================================
//INICIAR SERVICOS
//================================================================
//INCIAR MONGO DB
//CMD MONGDOD
//START SPRING BOOT[CLASS APLICATION --> RUN SPRING BOOT]
//================================================================
//START POSTMAN
//NO CAMPO GET ADD ULR:http://localhost:8080/student -->send
//CRIAR UM NOVO SERVICO: CLICAR NA BARRA DE +
//SELECIONAR POST-->URL:http://localhost:8080/student
//MARQUE OPCAO BODY [OPCAPO: RAW, TEXT: JSON(APPLICATION/JSON)]
//DENTRO DO CAMPO
//================================================================
{
	"name": "Novo nome",
	"age": 55
}
//================================================================
//send
//OBS:[PARA UTILIZAR OS SERVICOS BASTA ENVIAR A URL, PRECISA STARTADO E O MONGO DB ]
//================================================================

//================================================================
//GIT VS CODE
//================================================================
//CTRL+SHIFT+P --> GIT CLONE
//ADD URL--> [EX: https://github.com/EdneiFNeto/nbtelecom-tv-ionic]

//================================================================
//PROJETO [BACK-END]
//================================================================
//CRIAR PROJETO SPRING
//dependencias : devtools, security, mongo db, rest reposotiries
//================================================================

//================================================================
//CRIANDO ENTIDADES
//================================================================
//USER
//================================================================
package com.ednei.projeto_backend.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

  @Id
  private String id;
  
  @Indexed(unique=true)
  @NotBlank(message="Email required")
  @Email(message="Email invalid")
  private String email;

  @NotBlank(message="password required")
  @Size(min=6)
  private String password;
  
}

//================================================================

package com.ednei.projeto_backend.entity;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ednei.projeto_backend.enums.StatusEnum;

@Document
public class ChangeStatus {

  @Id
  private String id;
  
  @DBRef(lazy=true)//[referencia ao usuario]
  private Ticket ticket;
  
  @DBRef(lazy=true)//[referencia ao usuario]
  private User userChange;
  
  private Date dateChangeStatus;
  
  private StatusEnum status;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Ticket getTicket() {
    return ticket;
  }

  public void setTicket(Ticket ticket) {
    this.ticket = ticket;
  }

  public User getUserChange() {
    return userChange;
  }

  public void setUserChange(User userChange) {
    this.userChange = userChange;
  }

  public Date getDateChangeStatus() {
    return dateChangeStatus;
  }

  public void setDateChangeStatus(Date dateChangeStatus) {
    this.dateChangeStatus = dateChangeStatus;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }
}

//================================================================
package com.ednei.projeto_backend.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ednei.projeto_backend.enums.PriorityEnum;
import com.ednei.projeto_backend.enums.StatusEnum;

@Document
public class Ticket {

  @Id
  private String id;
  
  
  //usuar que criou ticket
  @DBRef(lazy=true)//[referencia ao usuario
  private User user;
  
  private Date date;
  private String title;
  private Integer number;
  
  private StatusEnum status;
  private PriorityEnum priority;
  
  @DBRef(lazy=true)
  private User assinedUser;
  
  private String description;
  
  private String image;
  
  @Transient //nao que q tenha representacao no banco
  private List<ChangeStatus> changesStatus;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public PriorityEnum getPriority() {
    return priority;
  }

  public void setPriority(PriorityEnum priority) {
    this.priority = priority;
  }

  public User getAssinedUser() {
    return assinedUser;
  }

  public void setAssinedUser(User assinedUser) {
    this.assinedUser = assinedUser;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public List<ChangeStatus> getChanges() {
    return changesStatus;
  }

  public void setChanges(List<ChangeStatus> changesStatus) {
    this.changesStatus = changesStatus;
  }
  
  
}



//================================================================
//ENUM
//================================================================

package com.ednei.projeto_backend.enums;

public enum ProfileEnum {
  
  ROLE_ADIMIN,
  ROLE_CUSTOMER,
  ROLE_TECHINICIN
}

//================================================================
package com.ednei.projeto_backend.enums;

public enum StatusEnum {

  New,
  Assigned, 
  Resolved,
  Approved,
  Disaproved,
  Closed;
  
  //metodos 
  public static StatusEnum getStatus(String status) {
    
    switch(status) {
      case "New": return New;
      case "Assigned": return Assigned;
      case "Resolved": return Resolved;
      case "Approved": return Approved;
      case "Disaproved": return Disaproved;
      case "Closed": return Closed;
      default: return New;
    }
  }
}

//================================================================
package com.ednei.projeto_backend.enums;

public enum PriorityEnum {

  High,//alto
  Normal,
  Low;//baixo
  
}

//================================================================
//REPOSITORIOS [INTERFACES]
//================================================================
package com.ednei.projeto_backend.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ednei.projeto_backend.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

  //OBS[ FINDBY SEGUIDO DO ATRIBUTO DA CLASS]
  User findByEmail(String  email);
}

//================================================================

  package com.ednei.projeto_backend.api.repository;
  
  import java.awt.print.Pageable;
  
  import org.springframework.data.domain.Page;
  import org.springframework.data.mongodb.repository.MongoRepository;
  import com.ednei.projeto_backend.entity.Ticket;
  
  public interface TicketRepository extends MongoRepository<Ticket, String>{
  
    //IgnoreCase: ignora texto em maiusculo
    //Containing: Equivalnete ao like
    //Pesquisa ID do usuario[apenas usar logado]
    Page<Ticket> findByUserIdOrderByDateDesc(Pageable pages, String id);
    
    //Search window [filter]
    Page<Ticket> 
    findByTitleIgnoreCaseContainingAndStatusAndPriorityOrderByDateDesc
    (String title, String status, String priority, Pageable pages);
    
    
    //Filter all parameter[list ticket clients]
    Page<Ticket> 
    findByTitleIgnoreCaseContainingAndStatusAndPriorityAndUserIdOrderByDateDesc
    (String title, String status, String priority, Pageable pages);
    
    Page<Ticket> 
    findByTitleIgnoreCaseContainingAndStatusAndPriorityAndAssinedUserOrderByDateDesc
    (String title, String status, String priority, Pageable pages);
    
    Page<Ticket> findByNumber(Integer number, Pageable pages);
  }


//================================================================
package com.ednei.projeto_backend.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ednei.projeto_backend.entity.ChangeStatus;

public interface ChangesStatusRepository extends MongoRepository<ChangeStatus, String>{

  Iterable<ChangeStatus> findByTicketIdOrderByDateChangeStatusDesc(String ticketId);
  
}


//================================================================
//CRIAR SERVICOS DE USUARIO
//================================================================
package com.ednei.projeto_backend.api.service;

import org.springframework.data.domain.Page;
import com.ednei.projeto_backend.entity.User;

public interface UserService {
  
  User findByEmail(String email); 
  User createOrUpdate(User user);
  User findById(String id);
  void delete(String id);
  Page<User> findAll(int page, int count);//pages & counts

}

//================================================================
//CRIAR IMPLEMENTACAO DOS SERVICOS
//================================================================
package com.ednei.projeto_backend.api.service.impl;

import java.awt.print.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ednei.projeto_backend.api.repository.UserRepository;
import com.ednei.projeto_backend.api.service.UserService;
import com.ednei.projeto_backend.entity.User;

@Service
public class UserServiceImpl implements UserService{

  //dependencia
  @Autowired //diz que foi ijetado uma dependencia
  private UserRepository userrepository;
  
  @Override
  public User findByEmail(String email) {
    return this.userrepository.findByEmail(email);
  }

  @Override
  public User createOrUpdate(User user) {
    return this.userrepository.save(user);
  }

  @Override
  public User findById(String id) {
    return null;
  }

  @Override
  public void delete(String id) {
    this.userrepository.deleteById(id);
    
  }

  @Override
  public Page<User> findAll(int page, int count) {
    Pageable pages = (Pageable) new PageRequest(page, count);
    //return this.userrepository.findAll(pages);
    return null;
  }
}

//================================================================
//JWT - AUTENTICACAO SEM TOKEM [FORMATO DE TOKEN SEGURO, 
//APLICACAO VALIDA ACESSA A REUISICAO]
//INFORM: DADOS DO USUARIO[PERFIL E ETC..]
//================================================================
//1- ADD DEPENDENCIA JWT AO PROJETO
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.ednei</groupId>
  <artifactId>projeto_backend</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>projeto_backend</name>
  <description>Demo project for Spring Boot</description>

  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.4.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
  </parent>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <java.version>1.8</java.version>

    <!-- ADD-->
    <jjwt.version>0.7.0</jjwt.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-rest</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-devtools</artifactId>
      <scope>runtime</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework.security</groupId>
      <artifactId>spring-security-test</artifactId>
      <scope>test</scope>
    </dependency>
    <!-- ADD-->
    <dependency>
      <groupId>io.jsonwebtoken</groupId>
      <artifactId>jjwt</artifactId>
      <version>${jjwt.version}</version>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>
</project>
//================================================================
//2-ARQUIVO APLICATION.PROPERTIES[MAIN/RESOURCES]
#acesso banco
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=helpdesk

jwt.secret=helpDesl_key
# expiration 7 day
jwt.expiration=604800

//================================================================
//3-CRIAR CLASS PARA MANIPULAR TOKEN
//================================================================
package com.ednei.projeto_backend.security.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import com.ednei.projeto_backend.entity.JwtUser;
import com.ednei.projeto_backend.entity.UserDatails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtil implements Serializable{

  private static final long SerialVersionUID=1;
  
  private final String CLAIM_KEY_USERNAME="sub";
  private final String CLAIM_KEY_CREATED="created";
  private final String CLAIM_KEY_EXPIRED="exp";
  
  @Value("${jwt.secret}")//referencia no arquivo [application.propertie]
  private String secret;
  
  @Value("${jwt.expiration}")//referencia no arquivo [application.propertie]
  private Long expiration;
  
  public String getUserNameFromToken(String token) {
    String username;
    try {
      final Claims claims = getClaimsFromToken(token);
      username = claims.getSubject();
    }catch(Exception e) {
      username = null;
    }
    
    return username;
  }

  public Date getExpirationDateFromToken(String token) {
    Date expiration;
    try {
      final Claims claims = getClaimsFromToken(token);
      expiration = claims.getExpiration();
    }catch(Exception e) {
      expiration=null;
    }
    
    return expiration;
  }
  
  
  private Claims getClaimsFromToken(String token) {
    
    Claims claims;
    try {
      claims = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody();
    }catch(Exception e) {
      claims=null;
    }
    
    return claims;
  }
  
  //verify token expired
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }
  
  //create Token
  public String generateToken(UserDetails userDatails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(CLAIM_KEY_USERNAME, userDatails.getUsername());
    
    final Date createdDate = new Date();
    claims.put(CLAIM_KEY_CREATED, createdDate);
    
    return doGeneratedToken(claims);
    
  }

  private String doGeneratedToken(Map<String, Object> claims) {
    final Date createdDate = (Date)claims.get(CLAIM_KEY_CREATED);
    final Date expirationDate = new Date(createdDate.getTime()+expiration*1000);
    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }
  
  public Boolean canTokenBeRefresh(String token) {
    return(!isTokenExpired(token));
  }
  
  //update token
  public String refreshToken(String token) {
    String refreshStoken;
    
    try {
      final Claims claims = getClaimsFromToken(token);
      claims.put(CLAIM_KEY_CREATED, new Date());
      refreshStoken = doGeneratedToken(claims);
    }catch(Exception e) {
      refreshStoken=null;
    }
    return refreshStoken;
  }
  
  //verify valid token
  public Boolean validadeToken(String token, UserDetails userDetails) {
    JwtUser user = (JwtUser) userDetails;
    final String username = getUserNameFromToken(token);
    return (username.equals(user.getUsername())&& !isTokenExpired(token));
  }
}

//================================================================
//4-CRIAR JWTUSER
//================================================================
package com.ednei.projeto_backend.entity;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUser implements UserDetails{

  
  private static final long serialVersionUID = -19608005357942819L;

  private final String id;
  private final String username;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;
  
  
  
  public JwtUser(String id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }
  
  @JsonIgnore
  public String getId() {
    return id;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    
    return authorities;
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return password;
  }

  @JsonIgnore
  @Override
  public String getUsername() {
    return username;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}

//================================================================
//4-CRIAR JWTUSERFACTORY [REPONSALVE POR SPRING RECONHECELA]
//================================================================

package com.ednei.projeto_backend.security.jwt;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.ednei.projeto_backend.entity.User;
import com.ednei.projeto_backend.enums.ProfileEnum;

public class JwtUserFactory {
  
  private JwtUserFactory() {}
  
  //CONVERTE COM BASE NO USUARIO
  public static JwtUser create(User user) {
    return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), mapToGranteAuthoritities(user.getProfile()));
  }
  
  //CONVERTE PARA USER SECURITY
  public static List<GrantedAuthority> mapToGranteAuthoritities(ProfileEnum profileEnum){
     List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
     authority.add(new SimpleGrantedAuthority(profileEnum.toString()));
     return authority;
     
  }
}


//=======================================================
//NAVEGACAO DE TELAS
//======================================================
import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

import { AlertController } from 'ionic-angular';
import { RedeGloboComponent } from '../rede-globo/rede-globo';

@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})
export class LoginPage {

  private login: string;
  private senha: string;


  constructor(public navCtrl: NavController, public navParams: NavParams,
    public alertCtrl: AlertController) {

  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad LoginPage');
    this.startPage();
  }

  
  private startPage():void{
    //seta o usuario coimo Root
    this.navCtrl.setRoot(RedeGloboComponent);
  }
}

//=======================================================================
//ANGULAR + FIREBASE + ANDROID
//=======================================================================
// npm install firebase --save
//=======================================================================
//npm install angularfire2 --save
//=======================================================================
//ARUIVO environment.TS
//=======================================================================

export const environment = {
  production: false,
  firebase:{
    apiKey: "AIzaSyAew3hDDbaglGOhVVWm80DO6lKysSglbUo",
    authDomain: "chat-android-323db.firebaseapp.com",
    databaseURL: "https://chat-android-323db.firebaseio.com",
    projectId: "chat-android-323db",
    storageBucket: "chat-android-323db.appspot.com",
    messagingSenderId: "985196595714"
  }
};


//=======================================================================
//ARUIVO APP.MODULE.T
//=======================================================================
import { environment } from './../environments/environment';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import{AngularFireModule} from 'angularfire2/index'//add
import { AngularFireDatabase } from 'angularfire2/database';//add


@NgModule({
  declarations: [
    AppComponent
  ],

  imports: [
    BrowserModule,
    AngularFireModule.initializeApp(environment.firebase),//add
    
  ],

  providers: [
    AngularFireDatabase//add[DATABASE]
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

//=======================================================================
//ARUIVO APP.COMPONENT.TS
//=======================================================================
import { Component, OnInit } from '@angular/core';
import { AngularFireDatabase } from 'angularfire2/database';//ADD
import { Observable } from 'rxjs';//ADD

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {

  items: Observable<any[]>;
  dados: any[] = [];

  constructor(private angularFire: AngularFireDatabase) {
    this.listar();
  }

  listar(): void {

    this.items = this.angularFire.list('/users').valueChanges();

    this.items.subscribe(data => {

      let nome = data.map(item => item.nome);
      let email = data.map(item => item.email);
      if(this.dados.length>0){
        this.dados = [];
      }

      for (var i = 0; i < data.length; i++) {
        
        if (nome[i] != undefined){
          this.dados.push({ nome: nome[i], email: email[i] });
        }
      }

      console.log(this.dados);
    });
  }

  cadastrar(nome: String, email: String): void {
    this.angularFire.list('/users/' + nome).push({
      nome: nome,
      email: email
    }).then(() => {
      console.log('Sucesso')
    })
  }
}

//=======================================================================
//ARUIVO APP.HTML
//=======================================================================
<ul>
  <li *ngFor="let dado of dados">
   Nome {{ dado.nome }} - {{dado.email}}
  </li>
</ul>

ionic generate provider message


//=======================================================================
//ARRAY OBJECT
//=======================================================================

contacts:Array<Object> =[];

  constructor(public navCtrl: NavController) {
    this.contacts =  [
      { "name":"Ednei Freitas", "icon":"ic_rosto.png", "message":"Menssagem enviada"}
    ]
  }

<ion-item *ngFor="let contact of contacts" (click)="openContact($event, contact)">
      <ion-avatar item-start>
        <img src="assets/imgs/{{contact.icon}}">
      </ion-avatar>
      <h2>{{ contact.name }}</h2>
      <p>{{ contact.message }}</p>
    </ion-item>
  </ion-list>


//=======================================================================
//EVENT CLICK LISTA[[MUDAR DE PAGINA]
//=======================================================================

openContact(event , contact){
    console.log(event);
    console.log(contact);
    this.navCtrl.push(UsersContactsPage, { contact: contact });
  }
<ion-list>

    <ion-item *ngFor="let contact of contacts" (click)="openContact($event, contact)">
      <ion-avatar item-start>
        <img src="assets/imgs/{{contact.icon}}">
      </ion-avatar>
      <h2>{{ contact.name }}</h2>
      <p>{{ contact.message }}</p>
    </ion-item>
  </ion-list>

//=======================================================================
//ADD ELEMETO O HTML
//=======================================================================
  createMessageHtml(msg: any) {

    let node = document.createElement('p');
    node.setAttribute('class', 'my_message');
    let text = document.createTextNode(msg);
    node.appendChild(text);
    document.querySelector('.message').appendChild(node);
    this.srcrollBottom();
	  
	  
//=======================================================================
//SCROLL BOTTOM
//=======================================================================	  
import { ChamadasPage } from './../chamadas/chamadas';
import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, Content } from 'ionic-angular';
import { ViewChild } from '@angular/core';


@IonicPage()
@Component({
  selector: 'page-users-contacts',
  templateUrl: 'users-contacts.html',
})
export class UsersContactsPage {

  @ViewChild(Content) content: Content;

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }
    
  srcrollBottom(){
    this.content.scrollToBottom();
  }
}
