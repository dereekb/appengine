import { NgModule } from '@angular/core';
import { SignInGatewayGroupDirective } from './gateway.directive';
import { SignInGatewayRegisterDirective } from './register.directive';
import { SignInGatewayDirective } from './login.directive';
import { SignInGatewaySuccessDirective } from './token.directive';
import { SignInGatewaySignOutDirective } from './signout.directive';
import { GaeApiModule } from '@gae-web/appengine-api';
import { GaeSignInGatewayViewComponent } from './gateway.component';
import { CredentialsComponent } from './credentials.component';
import { GaeGatewayMaterialModule } from '../material.module';
import { CommonModule } from '@angular/common';
import { GaeLoadingComponentsModule } from '@gae-web/appengine-components';

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
    GaeLoadingComponentsModule,
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
    GaeLoadingComponentsModule,
    GaeGatewayMaterialModule
  ]
})
export class GaeGatewayComponentsModule { }
