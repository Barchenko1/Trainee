import {AbstractControl} from '@angular/forms';

export function loginValidator(control: AbstractControl): { [key: string]: boolean } | null {
  const regExp = new RegExp('^[A-Za-z0-9_]{4,16}$');
  if (!regExp.test(control.value)) {
    return {invalidLogin: false};
  }
  return null;
}
