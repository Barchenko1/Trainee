import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {ApiService} from '../core/api.service';
import {Role} from '../model/role.model';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  addForm: FormGroup;

  private captchaResponse: string;

  roleUser: Role = {roleId: 2, name: 'USER'};

  constructor(private formBuilder: FormBuilder, private router: Router, private apiService: ApiService) { }

  ngOnInit() {
    this.addForm = this.formBuilder.group({
      userId: [],
      login: ['', Validators.required],
      password: ['', Validators.required],
      passwordAgain: ['', Validators.required],
      email: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      birthday: ['', Validators.required],
      role: [this.roleUser]
    });
  }

  onSubmit() {
    this.apiService.createUser(this.addForm.value)
      .subscribe(data => {
        alert('Account was created. You may log in');
    //    this.router.navigate(['login']);
      }, error => {
        alert('Account was created. You may log in');
      });
    console.log(this.roleUser);
  }

  cansel() {
    this.router.navigate(['login']);
  }

  resolved(captchaResponse: string) {
    this.captchaResponse = captchaResponse;
  }


}
