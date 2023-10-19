import { HttpClient,  HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root',
})
export class LoginService {
    constructor(private httpClient: HttpClient) { }
    userLogin(username:any,password:any){
        let headers = null;
       headers =  new HttpHeaders().set("Content-Type", "application/json1")
       .set("username","admin")
       .set("password","Corpus@123");
        return this.httpClient
        .post<any>(environment.baseUrl + `auth/login`,{},{ headers: headers});
    }
    forgotpassword(username:any){
        let headers = null;
       headers =  new HttpHeaders().set("username", username);
        return this.httpClient
        .get<any>(environment.baseUrl + `auth/forgotPassword`,
            { headers: headers}
        );
    }
    getDisplayMenus(loginauthToken:any){
        let headers = null;
        headers =  new HttpHeaders().set("Authorization",loginauthToken);
        return this.httpClient
        .get<any>(environment.baseUrl + 'newdrm/menu/displaymenus',
            { headers: headers}
        );
    }
}