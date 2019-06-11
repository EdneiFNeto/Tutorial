
//====================================================
//SIDE MENU
//REFERENCIA: https://www.youtube.com/watch?v=srTt7AVof-U
//====================================================
//CRIAR O MENU
//====================================================
//APP.COMPONENT.HTML
/*
<ion-app>
<ion-split-pane>
<ion-menu>
<ion-header>
<ion-toolbar>
<ion-button slot="start">
<ion-menu-button></ion-menu-button>
</ion-button>
<ion-title>MENU</ion-title>
</ion-toolbar>
</ion-header>

<ion-content>
<ion-list>
<ion-menu-toggle *ngFor="let p of pages">
<ion-item>
<ion-icon [name]="p.icon"></ion-icon>
<ion-label>{{ p.title }}</ion-label>
</ion-item>            
</ion-menu-toggle>
</ion-list>
</ion-content>
</ion-menu>

<ion-router-outlet main></ion-router-outlet>
</ion-split-pane>
</ion-app>
*/
//====================================================
//app.componeny.ts
//====================================================
//DEFINE OS ITENS DO MENU
//====================================================
import { Component } from '@angular/core';

import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

@Component({
    selector: 'app-root',
    templateUrl: 'app.component.html'
})
export class AppComponent {

    pages = [
        { title: 'Titulo 1', icon: 'home' },
        { title: 'Titulo 1', icon: 'home' },
        { title: 'Titulo 1', icon: 'home' },
        { title: 'Titulo 1', icon: 'home' },
    ]
    constructor(
        private platform: Platform,
        private splashScreen: SplashScreen,
        private statusBar: StatusBar
    ) {
        this.initializeApp();
    }

    initializeApp() {
        this.platform.ready().then(() => {
            this.statusBar.styleDefault();
            this.splashScreen.hide();
        });
    }
}

//====================================================
//home.html
//====================================================
//INSEIRE O ICON HABURGER PARA O MENU
//====================================================
/* 
    <ion-header>
        <ion-toolbar>
            <!--define o botao de menu-->
            <ion-button slot="start">
            <ion-menu-button></ion-menu-button>
            </ion-button>
            <!--define o botao de menu-->
            <ion-title>Home</ion-title>
        </ion-toolbar>
    </ion-header>
*/


//====================================================
//app-routing.module.ts
//====================================================
//DEFINE AS ROTAS
//====================================================
import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' }, //deifne a pagina inicial
    { path: 'login', loadChildren: './login/login.module#LoginPageModule' },
    { path: 'contato', loadChildren: './contato/contato.module#ContatoPageModule' },
    { path: 'sobre', loadChildren: './sobre/sobre.module#SobrePageModule' },
    { path: 'ionic', loadChildren: './ionic/ionic.module#IonicPageModule' },
    { path: 'flutter', loadChildren: './flutter/flutter.module#FlutterPageModule' },
    { path: 'home', loadChildren: './home/home.module#HomePageModule' },
];

@NgModule({
    imports: [
        RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
    ],
    exports: [RouterModule]
})
export class AppRoutingModule { }


