import { RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {AddUserComponent} from './add-user/add-user.component';
import {EditUserComponent} from './edit-user/edit-user.component';
import {ListUserComponent} from './list-user/list-user.component';
import {UserComponent} from './user/user.component';
import {RegistrationComponent} from './registration/registration.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent},
  { path: '', component: LoginComponent},
  { path: 'create', component: AddUserComponent},
  { path: 'edit', component: EditUserComponent},
  { path: 'list-user', component: ListUserComponent},
  { path: 'user', component: UserComponent},
  { path: 'registration', component: RegistrationComponent}
];

export const routing = RouterModule.forRoot(routes);
