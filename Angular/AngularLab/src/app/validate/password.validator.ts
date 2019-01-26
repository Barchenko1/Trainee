import {AbstractControl} from '@angular/forms';

export function passwordValidator(control: AbstractControl): { [key: string]: boolean } | null {
  const regExp = new RegExp('^[a-zA-Z0-9]{4,16}$');
  if (!regExp.test(control.value)) {
    return {invalidPassword: false};
  }
  return null;
}
