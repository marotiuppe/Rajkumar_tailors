import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  recentUpdates:any={
    "updatedBy":"Maroti Uppe",
    "updatedDate":"October 3rd, 2018",
    "update":[
      "assets/images/dashboard/ganesh.jpg",
      "assets/images/dashboard/machine.jpg",
      "assets/images/dashboard/measuement.jpg",
      "assets/images/dashboard/meterial-quality.png",
      "assets/images/dashboard/school.jpg",
      "assets/images/dashboard/Shirts.png",
    ]
  }
}
