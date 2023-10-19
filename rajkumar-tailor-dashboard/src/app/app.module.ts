import { NgModule } from '@angular/core';
import { FormsModule} from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TopLayoutNavbarComponent } from './layout/top-layout-navbar/top-layout-navbar.component';
import { SidebarLayoutNavbarComponent } from './layout/sidebar-layout-navbar/sidebar-layout-navbar.component';
import { FooterComponent } from './layout/footer/footer.component';
import { DashboardComponent } from './module/dashboard/dashboard.component';
import { LoginComponent } from './module/login/login.component';
import { SignUpComponent } from './module/sign-up/sign-up.component';
import { UserComponent } from './module/user/user.component';
import { AddUserComponent } from './module/user/add-user/add-user.component';

@NgModule({
  declarations: [
    AppComponent,
    TopLayoutNavbarComponent,
    SidebarLayoutNavbarComponent,
    FooterComponent,
    DashboardComponent,
    LoginComponent,
    SignUpComponent,
    UserComponent,
    AddUserComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
