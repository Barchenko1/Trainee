import { Component, OnInit , Inject} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../model/user.model';
import {ApiService} from '../core/api.service';
import {Role} from '../model/role.model';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {

  users: User[];

  roles: Role[];

  login: string;

  private message: string;

  constructor(private router: Router, private apiService: ApiService, private route: ActivatedRoute ) { }

  ngOnInit() {
    if (!window.localStorage.getItem('token')) {
      this.router.navigate(['login']);
      return;
    }
    this.apiService.getUsers()
      .subscribe(data => {
        this.users = data.user;
      });
  }

  addUser(): void {
    this.router.navigate(['create']);
  }

  editUser(user: User) {
   // window.localStorage.removeItem('editUserLogin');
    window.localStorage.setItem('editUserLogin', user.login);
    window.localStorage.setItem('editUserPassword', user.password);
    window.localStorage.setItem('editUserPasswordAgain', user.password);
    window.localStorage.setItem('editUserFirstName', user.firstName);
    window.localStorage.setItem('editUserLastName', user.lastName);
    window.localStorage.setItem('editUserEmail', user.email);

    this.router.navigate(['edit']);
  }

  logout() {
    window.sessionStorage.clear();
    window.localStorage.clear();
    this.router.navigate(['login']);
    window.localStorage.setItem('token', '');
  }

  deleteUser(user: User): void {
    if (confirm('Are you sure to want delete this user with ' + user.login + ' login?')) {
      this.apiService.deleteUser(user.login)
        .subscribe(data => {
            this.apiService.getUsers();
            this.users = this.users.filter(u => u !== user);
            this.message = 'User deleted';
            alert(this.message);
          },
          (error) => {
            this.users = this.users.filter(u => u !== user);
            this.message = 'User deleted';
            alert(this.message);
          }
        );
    }
  }

  myCalcAge(birthday): number {
    const age = Math.abs(

      new Date(

        Date.now() - birthday // 1. Get the difference as new Date object

      ).getUTCFullYear() - 1970 // 2. Calculate the years

    );
    return age;
  }


}
