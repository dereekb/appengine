import { NgModule } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatMenuModule, MatIconModule, MatButtonModule, MatSidenavModule, MatListModule } from '@angular/material';

@NgModule({
  exports: [
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatSidenavModule,
    MatListModule,
    FlexLayoutModule
  ]
})
export class SecureAppImportsModule { }
