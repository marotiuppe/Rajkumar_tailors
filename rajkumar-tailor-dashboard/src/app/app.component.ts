import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'rajkumar-tailor-dashboard';
  userDetails:any;
  brandDetails:any;
  constructor(private router:Router){}

   ngOnInit(): void {
    this.grtUserDetails();
    this.getBrandDetails();
  }

  grtUserDetails(){
    this.userDetails={
      "name":"Maroti Uppe",
      "profile":"assets/images/faces/face1.jpg",
      "role":"Admin"
    }
    sessionStorage.setItem("userDetails",JSON.stringify(this.userDetails));
  }

  getBrandDetails(){
    this.brandDetails={
      "name":"RajKumar Tailor",
      "logo":"assets/images/favicon.png",
      "miniLogo":"assets/images/favicon.png",
    }
    sessionStorage.setItem("brandDetails",JSON.stringify(this.brandDetails));
  }
}
