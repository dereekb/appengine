import { NgModule } from '@angular/core';
import { GaeAnchorModule } from './anchor/anchor.module';
import { GaeNavModule } from './nav/nav.module';

@NgModule({
  imports: [
    GaeAnchorModule,
    GaeNavModule
  ],
  declarations: [],
  exports: [
    GaeAnchorModule,
    GaeNavModule
  ]
})
export class GaeViewModule { }
