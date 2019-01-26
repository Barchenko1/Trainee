import { Component, OnInit } from '@angular/core';
// import {User} from '../model/user.model';
import {Router} from '@angular/router';
import {ApiService} from '../core/api.service';
import {User} from '../model/user.model';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-list-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  private message: string;

  constructor(private router: Router, private apiService: ApiService, private http: HttpClient) {}

  ngOnInit(): void {
    if (!window.localStorage.getItem('token')) {
      this.router.navigate(['login']);
      return;
    }
    this.message = ' user';
  }

  logout(): void {
    this.router.navigate(['login']);
    window.localStorage.setItem('token', '');
  }

}
