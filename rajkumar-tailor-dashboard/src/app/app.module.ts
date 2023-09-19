import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TopLayoutNavbarComponent } from './layout/top-layout-navbar/top-layout-navbar.component';
import { SidebarLayoutNavbarComponent } from './layout/sidebar-layout-navbar/sidebar-layout-navbar.component';
import { FooterComponent } from './layout/footer/footer.component';
import { DashboardComponent } from './module/dashboard/dashboard.component';
import { LoginComponent } from './module/login/login.component';
import { SignUpComponent } from './module/sign-up/sign-up.component';

@NgModule({
  declarations: [
    AppComponent,
    TopLayoutNavbarComponent,
    SidebarLayoutNavbarComponent,
    FooterComponent,
    DashboardComponent,
    LoginComponent,
    SignUpComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
