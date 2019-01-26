import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {ApiService} from '../core/api.service';
import {Role} from '../model/role.model';
import {User} from '../model/user.model';
import {loginValidator} from '../validate/login.validator';
import {passwordValidator} from '../validate/password.validator';
import {emailValidator} from '../validate/email.validator';
import {nameValidator} from '../validate/name.validator';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  addForm: FormGroup;

  roles: Role[] = [
    {roleId: 1, name: 'ADMIN'},
    {roleId: 2, name: 'USER'}
  ];
  message: string;
  user: User = new User();

  selectedRole: Object = {};

   constructor(private formBuilder: FormBuilder, private router: Router, private apiService: ApiService) { }

  ngOnInit() {
    if (!window.localStorage.getItem('token')) {
      this.router.navigate(['login']);
      return;
    }
    this.addForm = this.formBuilder.group({
      userId: [],
      login: ['', loginValidator],
      password: ['', passwordValidator],
      passwordAgain: ['', passwordValidator],
      email: ['', emailValidator],
      firstName: ['', nameValidator],
      lastName: ['', nameValidator],
      birthday: ['', Validators.required],
      role: [this.selectedRole]
    });
  }

  onSubmit() {
    this.apiService.createUser(this.addForm.value)
      .subscribe(data => {
        alert('User created');
      }, error => {
        alert('User created');
      });
  }

  cansel() {
    this.router.navigate(['list-user']);
  }
}
