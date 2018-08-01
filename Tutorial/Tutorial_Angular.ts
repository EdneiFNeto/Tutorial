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
//[OBS: ABRIR OUTRA JANELA DE COMANDO: digite mongo]
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
