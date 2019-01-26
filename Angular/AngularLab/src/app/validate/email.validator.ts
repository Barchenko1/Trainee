import {AbstractControl} from '@angular/forms';

export function emailValidator(control: AbstractControl): { [key: string]: boolean } | null {
  const regex = new RegExp('^^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+$');
  if (!regex.test(control.value)) {
    return {invalidEmail: false};
  }
  return null;
}
