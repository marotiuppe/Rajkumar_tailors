import { Component } from '@angular/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent {

  usersList:any;

  ngOnInit(): void {
    this.getAllUsers();
  }
  getAllUsers(){
  // 1 : New
  // 2 : Active
  // 3 : In Active

  // 1 : Admin
  // 2 : Cutting Master
  // 3 : Shirt Maker
  // 4 : Pant Maker
  // 5 : Khamish Maker
  // 6 : All

  this.usersList= [
    {
      "profileImage": "https://mdbootstrap.com/img/new/avatars/8.jpg",
      "profileName": "Raju Tadagur",
      "mobileNo":9666516654,
      "role":"All",
      "roleId":5,
      "status":2,
      "totalShirts": 30,
      "totalPants": 20,
      "totalKhamish": 13,
      "advanceAmount": 1500,
      "totalAmount": 2500,
      "remainingAmount":1000,
      "joinDate": "May 15, 2015"
    },
    {
      "profileImage": "https://mdbootstrap.com/img/new/avatars/2.jpg",
      "profileName": "Rajkumar Biradar",
      "mobileNo":8184822673,
      "role":"Cutting Master",
      "roleId":2,
      "status":2,
      "totalShirts": 30,
      "totalPants": 20,
      "totalKhamish": 13,
      "advanceAmount": 1500,
      "totalAmount": 2500,
      "remainingAmount":1000,
      "joinDate": "May 15, 2015"
    },
    {
      "profileImage": "https://mdbootstrap.com/img/new/avatars/3.jpg",
      "profileName": "Basu Ibitwar",
      "mobileNo":8106412714,
      "role":"Shirt Maker",
      "roleId":3,
      "status":2,
      "totalShirts": 30,
      "totalPants": 20,
      "totalKhamish": 13,
      "advanceAmount": 1500,
      "totalAmount": 2500,
      "remainingAmount":1000,
      "joinDate": "May 15, 2015"
    },
    {
      "profileImage": "https://mdbootstrap.com/img/new/avatars/4.jpg",
      "profileName": "Sambhajirao",
      "mobileNo":9325386676,
      "role":"Shirt Maker",
      "roleId":3,
      "status":2,
      "totalShirts": 30,
      "totalPants": 20,
      "totalKhamish": 13,
      "advanceAmount": 1500,
      "totalAmount": 2500,
      "remainingAmount":1000,
      "joinDate": "May 15, 2015"
    },
    {
      "profileImage": "https://mdbootstrap.com/img/new/avatars/5.jpg",
      "profileName": "Sambhale Mama",
      "mobileNo":9848824256,
      "role":"Shirt Maker",
      "roleId":3,
      "status":2,
      "totalShirts": 30,
      "totalPants": 20,
      "totalKhamish": 13,
      "advanceAmount": 1500,
      "totalAmount": 2500,
      "remainingAmount":1000,
      "joinDate": "May 15, 2015"
    },{
      "profileImage": "https://mdbootstrap.com/img/new/avatars/6.jpg",
      "profileName": "Ramrao Anna",
      "mobileNo":8308713668,
      "role":"Pant Maker",
      "roleId":4,
      "status":2,
      "totalShirts": 30,
      "totalPants": 20,
      "totalKhamish": 13,
      "advanceAmount": 1500,
      "totalAmount": 2500,
      "remainingAmount":1000,
      "joinDate": "May 15, 2015"
    },
    {
      "profileImage": "https://mdbootstrap.com/img/new/avatars/7.jpg",
      "profileName":"Bgagwan Vibhute",
      "mobileNo":9960478295,
      "role":"Pant Maker",
      "roleId":4,
      "status":3,
      "totalShirts": 30,
      "totalPants": 20,
      "totalKhamish": 13,
      "advanceAmount": 1500,
      "totalAmount": 2500,
      "remainingAmount":1000,
      "joinDate": "May 15, 2015"
    },
    {
      "profileImage": "https://mdbootstrap.com/img/new/avatars/8.jpg",
      "profileName": "Vishnu Tailor",
      "mobileNo":9441525812,
      "role":"Cutting Master",
      "roleId":2,
      "status":2,
      "totalShirts": 30,
      "totalPants": 20,
      "totalKhamish": 13,
      "advanceAmount": 1500,
      "totalAmount": 2500,
      "remainingAmount":1000,
      "joinDate": "May 15, 2015"
    },
    {
      "profileImage": "https://mdbootstrap.com/img/new/avatars/9.jpg",
      "profileName": "Nagnath Anna",
      "mobileNo":9533012341,
      "role":"Pant Maker",
      "roleId":4,
      "status":3,
      "totalShirts": 30,
      "totalPants": 20,
      "totalKhamish": 13,
      "advanceAmount": 1500,
      "totalAmount": 2500,
      "remainingAmount":1000,
      "joinDate": "May 15, 2015"
    },{
      "profileImage": "https://mdbootstrap.com/img/new/avatars/10.jpg",
      "profileName": "Laxman Rao",
      "mobileNo":6281019530,
      "role":"Pant Maker",
      "roleId":4,
      "status":3,
      "totalShirts": 30,
      "totalPants": 20,
      "totalKhamish": 13,
      "advanceAmount": 1500,
      "totalAmount": 2500,
      "remainingAmount":1000,
      "joinDate": "May 15, 2015"
    },
    {
      "profileImage": "https://mdbootstrap.com/img/new/avatars/11.jpg",
      "profileName": "Gajanan Sonkamble",
      "mobileNo":8796305329,
      "role":"Pant Maker",
      "roleId":4,
      "status":2,
      "totalShirts": 30,
      "totalPants": 20,
      "totalKhamish": 13,
      "advanceAmount": 1500,
      "totalAmount": 2500,
      "remainingAmount":1000,
      "joinDate": "May 15, 2015"
    },
    {
      "profileImage": "https://mdbootstrap.com/img/new/avatars/12.jpg",
      "profileName": "Maroti Uppe",
      "mobileNo":8796305329,
      "role":"Admin",
      "roleId":1,
      "status":2,
      "totalShirts": 30,
      "totalPants": 20,
      "totalKhamish": 13,
      "advanceAmount": 1500,
      "totalAmount": 2500,
      "remainingAmount":1000,
      "joinDate": "May 15, 2015"
    }
  ]
  }

}
