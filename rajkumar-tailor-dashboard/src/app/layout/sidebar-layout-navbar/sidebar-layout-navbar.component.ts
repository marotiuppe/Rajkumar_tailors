import { Component } from '@angular/core';

@Component({
  selector: 'app-sidebar-layout-navbar',
  templateUrl: './sidebar-layout-navbar.component.html',
  styleUrls: ['./sidebar-layout-navbar.component.css']
})
export class SidebarLayoutNavbarComponent {
  userDetails:any;

  ngOnInit(): void {
    const sharedDataString=sessionStorage.getItem("userDetails");
    if(sharedDataString){
      this.userDetails=JSON.parse(sharedDataString);
    }
  }
}
