import {AbstractControl} from '@angular/forms';

export function nameValidator(control: AbstractControl): { [key: string]: boolean } | null {
  const regExp = new RegExp('^[a-zA-Z]+$');
  if (!regExp.test(control.value)) {
    return {invalidName: false};
  }
  return null;
}
