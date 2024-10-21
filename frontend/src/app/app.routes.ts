import { Routes } from '@angular/router';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {SiteComponent} from './site/site.component';
import {UserinfoComponent} from './userinfo/userinfo.component';

export const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'site', component: SiteComponent},
  {path: 'userinfo', component: UserinfoComponent},
  {path: 'userinfo/:id', component: UserinfoComponent},
];
