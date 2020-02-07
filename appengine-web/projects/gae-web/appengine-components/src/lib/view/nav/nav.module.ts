import { NgModule } from '@angular/core';
import { GaeComponentsModule } from '@gae-web/appengine-components';
import { MatIconModule, MatButtonModule, MatListModule, MatTabsModule } from '@angular/material';
import { UIRouterModule } from '@uirouter/angular';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { GaeAnchorModule } from '../anchor/anchor.module';
import { GaeNavBarComponent } from './navbar.component';

@NgModule({
  imports: [
    CommonModule,
    GaeAnchorModule,
    MatTabsModule
  ],
  declarations: [
    GaeNavBarComponent
  ],
  exports: [
    GaeNavBarComponent
  ]
})
export class PlugSharedViewComponentsModule { }
