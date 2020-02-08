import { NgModule } from '@angular/core';
import { MatTabsModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { GaeAnchorModule } from '../anchor/anchor.module';
import { GaeNavBarComponent } from './navbar.component';

@NgModule({
  imports: [
    CommonModule,
    MatTabsModule,
    GaeAnchorModule
  ],
  declarations: [
    GaeNavBarComponent
  ],
  exports: [
    GaeNavBarComponent
  ]
})
export class PlugNavModule { }
