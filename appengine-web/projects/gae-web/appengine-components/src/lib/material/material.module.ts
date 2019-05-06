import { NgModule } from '@angular/core';
import { GaeActionMessageSnackbarDirective } from './snackbar.directive';
import { MatSnackBarModule } from '@angular/material';
import { GaeColorImageComponent, GaeColorStyleDirective, GaeBackgroundColorStyleDirective, GaeStringColorDirective } from './color.component';
import { CommonModule } from '@angular/common';

@NgModule({
  imports: [
    CommonModule,
    MatSnackBarModule
  ],
  declarations: [
    GaeActionMessageSnackbarDirective,
    GaeColorImageComponent,
    GaeColorStyleDirective,
    GaeBackgroundColorStyleDirective,
    GaeStringColorDirective
  ],
  exports: [
    GaeActionMessageSnackbarDirective,
    GaeColorImageComponent,
    GaeColorStyleDirective,
    GaeBackgroundColorStyleDirective,
    GaeStringColorDirective
  ]
})
export class GaeMaterialComponentsModule { }
