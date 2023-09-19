import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './module/login/login.component';
import { SignUpComponent } from './module/sign-up/sign-up.component';
import { DashboardComponent } from './module/dashboard/dashboard.component';
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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
