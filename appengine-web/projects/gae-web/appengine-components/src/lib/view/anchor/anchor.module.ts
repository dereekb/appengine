import { NgModule } from '@angular/core';
import { GaeAnchorComponent } from './anchor.component';
import { UIRouterModule } from '@uirouter/angular';
import { CommonModule } from '@angular/common';
import { GaeListAnchorComponent } from './list-anchor.component';

@NgModule({
  imports: [
    CommonModule,
    UIRouterModule
  ],
  declarations: [
    GaeAnchorComponent,
    GaeListAnchorComponent
  ],
  exports: [
    GaeAnchorComponent,
    GaeListAnchorComponent
  ]
})
export class GaeAnchorModule { }
