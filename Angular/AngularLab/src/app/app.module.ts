import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import {routing} from './app.routing';
import {ApiService} from './core/api.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {TokenInterceptor} from './core/interceptor';
import {UserComponent} from './user/user.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AddUserComponent } from './add-user/add-user.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { ListUserComponent } from './list-user/list-user.component';
import { RegistrationComponent } from './registration/registration.component';
import { RecaptchaModule } from 'ng-recaptcha';

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    LoginComponent,
    AddUserComponent,
    EditUserComponent,
    ListUserComponent,
    RegistrationComponent,

  ],
  imports: [
    BrowserModule,
    routing,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    RecaptchaModule.forRoot()
  ],
  providers: [ApiService, {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true

  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
