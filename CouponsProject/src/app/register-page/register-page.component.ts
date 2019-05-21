import { Component, OnInit } from '@angular/core';
import { UsersService } from '../shared/services/users.service';
import { User } from '../shared/models/User';
import { UserLoginDetails } from '../shared/models/UserLoginDetails';
import { Customer } from '../shared/models/Customer';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit {

  public customer:Customer;

  constructor(private usersService:UsersService) { 
    this.customer = new Customer();
    this.customer.user = new User();
    this.customer.user.userLoginDetails = new UserLoginDetails();
    this.customer.user.userLoginDetails.type = 'CUSTOMER';


  }

  ngOnInit() {
  }

}
