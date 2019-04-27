import { NgModule } from '@angular/core';
import { GaeActionMessageSnackbarDirective } from './snackbar.directive';
import { MatSnackBarModule } from '@angular/material';

@NgModule({
  imports: [
    MatSnackBarModule
  ],
  declarations: [
    GaeActionMessageSnackbarDirective,
  ],
  exports: [
    GaeActionMessageSnackbarDirective
  ]
})
export class GaeMaterialModule { }
