import { Injectable } from '@angular/core';
import { UserLoginDetails } from '../models/UserLoginDetails';
import { User } from '../models/User';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserDataMap } from '../models/UserDataMap';
import { Customer } from '../models/Customer';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private http:HttpClient, private router: Router ) { }

  public login(user: User): void {
    let observable = this.http.post<UserDataMap>("http://localhost:8080/users/login", user);
   
    observable.subscribe(userDataMap => {

      if (userDataMap.type === 'COMPANY') {
        this.router.navigate(["/companies"]);
      }
      if (userDataMap.type === 'CUSTOMER') {
        this.router.navigate(["/customers"]);
      }
      if (userDataMap.type === 'ADMINISTRATOR') {
        this.router.navigate(["/admin"]);
      }

      if (userDataMap.type != null) {

        sessionStorage.setItem(userDataMap.token + "", userDataMap.type);

        sessionStorage.setItem("userId", userDataMap.userID + "");
        
        sessionStorage.setItem("companyId", userDataMap.companyID + "");

      } else {
        alert("Invalid user or password");
      }


    }, err => {
      alert("Error Status: " + err.status + ", Message: " + err.message);
    });

  }
  public register(customer: Customer): void {
    let observable = this.http.post<Customer>("http://localhost:8080/customers", customer);
    
    observable.subscribe(customerReturned => {
      
    this.router.navigate(["/users/login"]);     


    }, err => {
      alert("Error Status: " + err.status + ", Message: " + err.message);
    });

  }
  
}
