import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {ApiService} from '../core/api.service';
import {Role} from '../model/role.model';
import {first} from 'rxjs/operators';
import {User} from '../model/user.model';
import {loginValidator} from '../validate/login.validator';
import {passwordValidator} from '../validate/password.validator';
import {emailValidator} from '../validate/email.validator';
import {nameValidator} from '../validate/name.validator';
import {error} from '@angular/compiler/src/util';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  editForm: FormGroup;

  roles: Role[] = [
    {roleId: 1, name: 'ADMIN'},
    {roleId: 2, name: 'USER'}
  ];

  user: User;

  users: User[];

  login: string;

  selectedRole: Object = {};
  private editUserLogin: string;
  private editUserPassword: string;
  private editUserPasswordAgain: string;
  private editUserFirstName: string;
  private editUserLastName: string;
  private editUserEmail: string;


  constructor(private formBuilder: FormBuilder, private router: Router, private apiService: ApiService, private route: ActivatedRoute) { }

  ngOnInit() {
    if (!window.localStorage.getItem('token')) {
      this.router.navigate(['login']);
      return;
    }
    this.editUserLogin = window.localStorage.getItem('editUserLogin');
    this.editUserPassword = window.localStorage.getItem('editUserPassword');
    this.editUserPasswordAgain = window.localStorage.getItem('editUserPasswordAgain');
    this.editUserFirstName = window.localStorage.getItem('editUserFirstName');
    this.editUserLastName = window.localStorage.getItem('editUserLastName');
    this.editUserEmail = window.localStorage.getItem('editUserEmail');

    this.login = this.route.snapshot.paramMap.get('login');
    this.editForm = this.formBuilder.group({
      userId: [],
      login: [this.editUserLogin, loginValidator],
      password: [this.editUserPassword, passwordValidator],
      passwordAgain: [this.editUserPasswordAgain, passwordValidator],
      email: [this.editUserEmail, emailValidator],
      firstName: [this.editUserFirstName, nameValidator],
      lastName: [this.editUserLastName, nameValidator],
      birthday: ['', Validators.required],
      role: [this.selectedRole]
    });
    // this.apiService.getUserByLogin(this.login)
    //   .subscribe(data => {
    //     // this.user = data, this.initGroup();
    //    // this.editForm.setValue(data);
    //   });
    // this.apiService.getUserByLogin(this.login).subscribe((data) => {
    //   this.user = data, this.initGroup();
    // }, error => {
    //
    // });
  }

  // initGroup() {
  //   this.editForm = this.formBuilder.group({
  //     id: [this.user.userId],
  //     login: [this.user.login, loginValidator],
  //     password: ['', passwordValidator],
  //     passwordAgain: ['', passwordValidator],
  //     email: [this.user.email, emailValidator],
  //     firstName: [this.user.firstName, nameValidator],
  //     lastName: [this.user.lastName, nameValidator],
  //     birthday: [this.user.birthday],
  //     role: [this.selectedRole]
  //   });
  // }


  onSubmit() {
    this.apiService.updateUser(this.editForm.value)
      .pipe(first())
      .subscribe(
        date => {
          if (date.status === 200) {
            alert('User updated.');
            this.router.navigate(['list-user']);
          } else {
            alert('error');
          }
        }, error => {
          alert('User updated.');
        });
  }

  cansel() {
    this.router.navigate(['list-user']);
  }
}
