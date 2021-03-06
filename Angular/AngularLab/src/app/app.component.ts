import { Component } from '@angular/core';
import {ApiService} from './core/api.service';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'AngularFrontEnd';

  constructor(private app: ApiService, private http: HttpClient, private router: Router) {
  }
}
