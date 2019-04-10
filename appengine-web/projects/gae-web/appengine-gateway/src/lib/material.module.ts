import { NgModule } from '@angular/core';

import {
  MatInputModule,
  MatRippleModule,
  MatProgressSpinnerModule
} from '@angular/material';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
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
    ObserversModule,
    PlatformModule,
    BrowserAnimationsModule

    // TODO: Add other dependencies.
  ]
})
export class GaeGatewayMaterialModule { }
