import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../model/user.model';
import {Observable} from 'rxjs/index';
import {ApiResponse} from '../model/api.response';
import {Role} from '../model/role.model';
import {tap} from 'rxjs/operators';

@Injectable()
export class ApiService {
  constructor(private http: HttpClient) {}

  Url = 'http://10.10.103.151:8080/rest/users';

  httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

  // Url = 'http://localhost:8080/rest/users';

  getUsers(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.Url);
  }

  getUserById(id: string): Observable<User> {
    return this.http.get<User>(this.Url + '/userId/' + id);
  }

  getUserByLogin(login: string): Observable<User> {
    return this.http.get<User>(this.Url + '/' + login);
  }

  login(loginPayload) {
    return this.http.post<ApiResponse>('http://10.10.103.151:8080/' + 'temp', loginPayload);
  }

  logout() {
    return this.http.get<ApiResponse>('http://10.10.103.151:8080/logout');
  }

  createUser (user): Observable<ApiResponse> {
    console.log(user);
    return this.http.post<ApiResponse>(this.Url + '/create', JSON.stringify(user), this.httpOptions);
  }

  updateUser(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.Url + '/edit', user);
  }

  deleteUser(login: string): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.Url + '/delete/' + login);
  }

  getRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(this.Url + '/roles');
  }

  getRoleById(roleId: number): Observable<Role> {
    return this.http.get<Role>(this.Url + '/roleId/' + roleId);
  }

  getRoleByName(name: string): Observable<Role> {
    return this.http.get<Role>(this.Url + '/roleName/' + name);
  }

}
