import { NgModule } from '@angular/core';
import { GaeAnchorModule } from './anchor/anchor.module';
import { GaeNavBarComponent } from './nav/navbar.component';

@NgModule({
  imports: [
    GaeAnchorModule,
    GaeNavBarComponent
  ],
  declarations: [],
  exports: [
    GaeAnchorModule,
    GaeNavBarComponent
  ]
})
export class GaeViewModule { }
