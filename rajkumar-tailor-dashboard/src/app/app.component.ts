import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'rajkumar-tailor-dashboard';
  loadPage:any='login';
  isLogin:any=false;
  isSignUp:any=false;
  userDetails:any;
  brandDetails:any;
  constructor(private router:Router){}

   ngOnInit(): void {
    this.router.events.subscribe(e => {
      if (e instanceof NavigationEnd) {
        this.loadPage = e.url.toString().substr(1);
        if(this.loadPage == ""){
          this.loadPage="login";
        }
        if(this.loadPage != "" && this.loadPage != "login" && this.loadPage != "sign-up"){
          this.isLogin=true;
        }
        console.log(this.loadPage);
        sessionStorage.setItem("loadPage",this.loadPage);
      }
    });
    this.grtUserDetails();
    this.getBrandDetails();
  }

  grtUserDetails(){
    this.userDetails={
      "name":"Maroti Uppe",
      "profile":"assets/images/faces/face1.jpg",
      "role":"Admin",
      "menu":[
        {
          "menuTitle":"Dashboard",
          "routerLink":"dashboard",
          "menuIcon":"mdi mdi-home menu-icon"
        },{
          "menuTitle":"User",
          "routerLink":"user",
          "menuIcon":"mdi mdi-account-box menu-icon"
        }
      ]
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
