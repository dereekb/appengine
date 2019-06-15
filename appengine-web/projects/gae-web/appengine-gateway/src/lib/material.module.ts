import { NgModule } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';
import { MatRippleModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { PlatformModule } from '@angular/cdk/platform';
import { ObserversModule } from '@angular/cdk/observers';

/**
 * NgModule for appengine-gateway's Material imports.
 */
@NgModule({
  exports: [
    MatInputModule,
    MatRippleModule,
    MatProgressSpinnerModule,
    MatProgressBarModule,
    MatButtonModule,
    ObserversModule,
    PlatformModule
    // TODO: Add other dependencies.
  ]
})
export class GaeGatewayMaterialModule { }
