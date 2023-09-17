import { Component } from '@angular/core';

@Component({
  selector: 'app-top-layout-navbar',
  templateUrl: './top-layout-navbar.component.html',
  styleUrls: ['./top-layout-navbar.component.css']
})
export class TopLayoutNavbarComponent {
  brandDetails:any;

  ngOnInit(): void {
    const sessionBrand=sessionStorage.getItem("brandDetails");
    if(sessionBrand){
      this.brandDetails=JSON.parse(sessionBrand);
    }
  }
}
