import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './module/login/login.component';
import { SignUpComponent } from './module/sign-up/sign-up.component';
import { DashboardComponent } from './module/dashboard/dashboard.component';
import { UserComponent } from './module/user/user.component';
import { AddUserComponent } from './module/user/add-user/add-user.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  }, {
    path: 'sign-up',
    component: SignUpComponent
  }, {
    path: 'dashboard',
    component: DashboardComponent
  }, {
    path: 'user',
    component: UserComponent
  }, {
    path: 'addUser',
    component: AddUserComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
