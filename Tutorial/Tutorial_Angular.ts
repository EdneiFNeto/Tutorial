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
//ESTRUTURA
//================================================================



//================================================================
//SPRING BOOT
//================================================================

//================================================================
//MONGO DB
//================================================================


//================================================================
//SPRING DATA
//================================================================
