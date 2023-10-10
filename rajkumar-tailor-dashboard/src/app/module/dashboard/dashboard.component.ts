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

  dashboardCardList:any=[
    {
      "title":"Total Orders",
      "icon":"mdi mdi-diamond mdi-24px float-right",
      "count":"95,5741",
      "variation":"Increased by 5%",
      "bg":"bg-gradient-info"
    },
    {
      "title":"Pending Orders",
      "icon":"mdi mdi-bookmark-outline mdi-24px float-right",
      "count":"45,6334",
      "variation":"Decreased by 10%",
      "bg":"bg-gradient-success"
    },
    {
      "title":"Recieved Payment",
      "icon":"mdi mdi-diamond mdi-24px float-right",
      "count":"15,0000",
      "variation":"Increased by 60%",
      "bg":"bg-gradient-danger"
    }
  ];
}
