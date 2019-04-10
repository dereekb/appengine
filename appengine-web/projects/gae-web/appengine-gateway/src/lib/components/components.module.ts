import { NgModule } from '@angular/core';
import { SignInGatewayGroupDirective } from './gateway.directive';
import { SignInGatewayRegisterDirective } from './register.directive';
import { SignInGatewayDirective } from './login.directive';
import { SignInGatewaySuccessDirective, SignInGatewaySignOutDirective } from './token.directive';
import { GaeApiModule } from '@gae-web/appengine-api';
import { GaeSignInGatewayViewComponent } from './gateway.component';
import { CredentialsComponent } from './credentials.component';
import { GaeGatewayMaterialModule } from '../material.module';
import { CommonModule } from '@angular/common';
import { GaeLoadingModule } from '@gae-web/appengine-components';

/**
 * Shared directives and components of Appengine Gateway.
 */
@NgModule({
  declarations: [
    CredentialsComponent,
    GaeSignInGatewayViewComponent,
    SignInGatewayGroupDirective,
    SignInGatewayRegisterDirective,
    SignInGatewayDirective,
    SignInGatewaySuccessDirective,
    SignInGatewaySignOutDirective
  ],
  exports: [
    GaeLoadingModule,
    GaeGatewayMaterialModule,
    CredentialsComponent,
    GaeSignInGatewayViewComponent,
    SignInGatewayGroupDirective,
    SignInGatewayRegisterDirective,
    SignInGatewayDirective,
    SignInGatewaySuccessDirective,
    SignInGatewaySignOutDirective
  ],
  imports: [
    CommonModule,
    GaeApiModule,
    GaeLoadingModule,
    GaeGatewayMaterialModule
  ]
})
export class GaeGatewayComponentsModule { }
