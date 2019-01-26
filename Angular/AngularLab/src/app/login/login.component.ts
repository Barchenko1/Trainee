import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {ApiService} from '../core/api.service';
import {User} from '../model/user.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  invalidLogin = false;
  user: User;

  constructor(private formBuilder: FormBuilder, private router: Router, private apiService: ApiService) {
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }
    const loginPayload = {
      username: this.loginForm.controls.username.value,
      password: this.loginForm.controls.password.value
    };
    this.apiService.login(loginPayload).subscribe(data => {
      window.sessionStorage.setItem('firstName', data.message);
      window.sessionStorage.setItem('lastName', data.message);
      window.sessionStorage.setItem('temp', data.message);
      if (data.message === 'admin') {
        window.localStorage.setItem('token', data.result);
        this.router.navigate(['list-user']);
      } else if (data.message === 'user') {
        window.localStorage.setItem('token', data.result);
        this.router.navigate(['user']);
      } else {
        this.invalidLogin = true;
        alert(data.message);
      }
    });
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.required]
    });
  }

  onRegistration() {
    this.router.navigate(['registration']);
  }


}

