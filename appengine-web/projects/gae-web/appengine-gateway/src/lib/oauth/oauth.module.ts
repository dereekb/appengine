import { NgModule } from '@angular/core';
import { OAuthButtonSignInDirective } from './oauth.directive';
import { OAuthSignInGatewayComponent } from './oauth.component';
import { GaeGoogleModule, GaeFacebookModule } from '@gae-web/appengine-services';
import { GaeGatewayMaterialModule } from '../material.module';
import { CommonModule } from '@angular/common';
import { GaeApiModule } from '@gae-web/appengine-api';

/**
 * Shared directives and components of Appengine Gateway.
 */
@NgModule({
  declarations: [
    OAuthButtonSignInDirective,
    OAuthSignInGatewayComponent
  ],
  exports: [
    OAuthButtonSignInDirective,
    OAuthSignInGatewayComponent
  ],
  imports: [
    CommonModule,
    GaeApiModule,
    GaeGoogleModule,
    GaeFacebookModule,
    GaeGatewayMaterialModule
  ]
})
export class GaeGatewayOAuthModule { }
