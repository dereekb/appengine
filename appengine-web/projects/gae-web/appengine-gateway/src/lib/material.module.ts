import { NgModule } from '@angular/core';

import {
  MatInputModule,
  MatRippleModule,
  MatProgressSpinnerModule,
  MatButtonModule
} from '@angular/material';

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
    MatButtonModule,
    ObserversModule,
    PlatformModule
    // TODO: Add other dependencies.
  ]
})
export class GaeGatewayMaterialModule { }
