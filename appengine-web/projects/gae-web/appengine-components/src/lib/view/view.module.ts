import { NgModule } from '@angular/core';
import { GaeAnchorModule } from './anchor/anchor.module';

@NgModule({
  imports: [
    GaeAnchorModule
  ],
  declarations: [],
  exports: [
    GaeAnchorModule
  ]
})
export class GaeViewModule { }
