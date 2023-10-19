import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './login-service';
import { SpinnerService } from 'src/app/spinner.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  userName:any;
  password:any;
  pwdType:any="password";
  rememberDetails:any;
  checkUser:any;
  checkPwd:any;
  validate:boolean=true;
  validateSuccess:boolean=true;
  validateMsg:any="";

  constructor(private route: Router,private service:LoginService,private SpinnerService:SpinnerService) { }

  ngOnInit(): void {
    this.checkUser = localStorage.getItem("checkuser");
    this.checkPwd = localStorage.getItem("checkpwd");
    if(this.checkUser != null && this.checkUser != undefined){
      this.userName = this.checkUser;
    }
    if(this.checkUser != null && this.checkUser != undefined){
      this.password = this.checkPwd;
    }
    if(this.checkUser != null && this.checkUser != undefined && this.checkUser != null && this.checkUser != undefined){
      this.rememberDetails = true;
    }else{
      this.rememberDetails = false;
    }
  }

  showpassword(val:any){
    if(val == 'password'){
      this.pwdType = 'text';
    }
    if(val == 'text'){
      this.pwdType = 'password';
    }
  }

  userLogin(){
    debugger;
    localStorage.setItem("checkuser",this.userName);
    localStorage.setItem("checkpwd",this.password);
    this.validate=true;
     if(this.userName == undefined || this.userName == ""){
      this.validate=false;
      this.validateMsg="Please enter username";
     }else if(this.password == undefined || this.password == ""){
      this.validate=false;
      this.validateMsg="Please enter Password";
    }else{
      this.SpinnerService.showSpinner();
      this.service.userLogin(this.userName,this.password).subscribe((resp: any) => {
        this.SpinnerService.hideSpinner();
        if(resp.responseStatus.statusCode == 200){
          sessionStorage.setItem("userauthToken","Bearer"+" "+resp.response.accessToken);
          sessionStorage.setItem("uname",resp.response.username);
          sessionStorage.setItem("lastlogin",resp.response.lastLoginTime);
          sessionStorage.setItem("reload","true");
          this.route.navigate([`/dashboard`]);
        }else if(resp.responseStatus.statusCode == 208){
          this.validate=false;
          this.validateMsg=resp.responseStatus.statusMessage;
          this.route.navigate([`/dashboard`]);
          // this.SnackbarService.openSnackBar({message:resp.responseStatus.statusMessage,status:'warning'})
        }else if(resp.responseStatus.statusCode == 401){
          this.validate=false;
          this.validateMsg=resp.responseStatus.statusMessage;
          this.route.navigate([`/dashboard`]);
          // this.SnackbarService.openSnackBar({message:resp.responseStatus.statusMessage,status:'warning'})
        }else{
          this.route.navigate([`/dashboard`]);
        }
      })
    }
  }

  forgotPassword(){
    let valid=true;
    this.validateSuccess=true;
    this.validate=true;
    if(this.userName == ""){
      valid=false;
      this.validate=false;
      this.validateMsg="Please enter username";
      // this.SnackbarService.openSnackBar({message:"PLEASE ENTER USERNAME",status:'warning'})
    }

    if(valid){
      this.SpinnerService.showSpinner();
      this.service.forgotpassword(this.userName).subscribe((resp: any) => {
        this.SpinnerService.hideSpinner();
        if(resp.statusCode == 200){
          this.validateSuccess=false;
          this.validateMsg=resp.statusMessage;
        //  this.SnackbarService.openSnackBar({message:resp.statusMessage,status:'success'})
        }else{
          this.validate=false;
          this.validateMsg=resp.statusMessage;
          // this.SnackbarService.openSnackBar({message:resp.statusMessage,status:'warning'})
        }
      })
    }
    
  }
}
