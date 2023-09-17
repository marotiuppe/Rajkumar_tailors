import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TopLayoutNavbarComponent } from './layout/top-layout-navbar/top-layout-navbar.component';
import { SidebarLayoutNavbarComponent } from './layout/sidebar-layout-navbar/sidebar-layout-navbar.component';
import { FooterComponent } from './layout/footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    TopLayoutNavbarComponent,
    SidebarLayoutNavbarComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
