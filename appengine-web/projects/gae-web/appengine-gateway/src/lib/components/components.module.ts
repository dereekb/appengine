import { NgModule } from '@angular/core';
import { OAuthButtonSignInDirective } from './oauth.directive';
import { SignInGatewayGroupDirective } from './gateway.directive';
import { SignInGatewayRegisterDirective } from './register.directive';
import { SignInGatewayDirective } from './login.directive';
import { SignInGatewaySuccessDirective, SignInGatewaySignOutDirective } from './token.directive';
import { ApiModule } from '@gae-web/appengine-api';
import { GaeSignInGatewayViewComponent } from './gateway.component';
import { CredentialsComponent } from './credentials.component';
import { GatewayMaterialModule } from '../material.module';
import { CommonModule } from '@angular/common';
import { GaeLoadingModule } from '@gae-web/appengine-components';

/**
 * Shared directives and components of Appengine Gateway.
 */
@NgModule({
  declarations: [
    CredentialsComponent,
    GaeSignInGatewayViewComponent,
    OAuthButtonSignInDirective,
    SignInGatewayGroupDirective,
    SignInGatewayRegisterDirective,
    SignInGatewayDirective,
    SignInGatewaySuccessDirective,
    SignInGatewaySignOutDirective
  ],
  exports: [
    GaeLoadingModule,
    GatewayMaterialModule,
    CredentialsComponent,
    GaeSignInGatewayViewComponent,
    OAuthButtonSignInDirective,
    SignInGatewayGroupDirective,
    SignInGatewayRegisterDirective,
    SignInGatewayDirective,
    SignInGatewaySuccessDirective,
    SignInGatewaySignOutDirective
  ],
  imports: [
    CommonModule,
    ApiModule,
    GaeLoadingModule,
    GatewayMaterialModule
  ]
})
export class GatewayComponentsModule { }
