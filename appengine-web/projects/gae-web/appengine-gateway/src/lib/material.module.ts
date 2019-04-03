import {NgModule} from '@angular/core';

import {
  MatInputModule,
  MatRippleModule
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
    ObserversModule,
    PlatformModule

    // TODO: Add other dependencies.
  ]
})
export class GatewayMaterialModule {}
