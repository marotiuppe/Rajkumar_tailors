import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './footer/footer.component';
import { PreloaderComponent } from './preloader/preloader.component';
import { NavigationComponent } from './navigation/navigation.component';
import { CarouselComponent } from './carousel/carousel.component';
import { AboutComponent } from './about/about.component';
import { ProcessComponent } from './process/process.component';
import { WorkComponent } from './work/work.component';
import { ProductsComponent } from './products/products.component';
import { FeatureComponent } from './feature/feature.component';
import { ContactComponent } from './contact/contact.component';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    PreloaderComponent,
    NavigationComponent,
    CarouselComponent,
    AboutComponent,
    ProcessComponent,
    WorkComponent,
    ProductsComponent,
    FeatureComponent,
    ContactComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
