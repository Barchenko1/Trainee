import { Role } from './role.model';

export class User {
  userId: number;
  login: string;
  password: string;
  firstName: string;
  lastName: string;
  email: string;
  birthday: Date;
  role: Role;

  constructor() {}
}
