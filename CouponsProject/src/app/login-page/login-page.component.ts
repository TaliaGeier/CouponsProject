import { Component, OnInit } from '@angular/core';
import { UsersService } from '../shared/services/users.service';
import { User } from '../shared/models/User';
import { UserLoginDetails } from '../shared/models/UserLoginDetails';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  public user:User;

  constructor(private usersService:UsersService) { 
    this.user = new User();
    this.user.userLoginDetails = new UserLoginDetails();
  }

  ngOnInit() {
  }

}
