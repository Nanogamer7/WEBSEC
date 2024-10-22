import { Routes } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {UserinfoComponent} from './userinfo/userinfo.component';

export const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'userinfo', component: UserinfoComponent},
  {path: 'userinfo/:id', component: UserinfoComponent},
];
