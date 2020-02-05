import { NgModule } from '@angular/core';
import { GaeAnchorComponent } from './anchor.component';
import { UIRouterModule } from '@uirouter/angular';
import { CommonModule } from '@angular/common';

@NgModule({
  imports: [
    CommonModule,
    UIRouterModule
  ],
  declarations: [
    GaeAnchorComponent
  ],
  exports: [
    GaeAnchorComponent
  ]
})
export class GaeAnchorModule { }
