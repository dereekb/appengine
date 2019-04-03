import { NgModule } from '@angular/core';
import { OAuthButtonSignInDirective } from './components/oauth.directive';
import { SignInGatewayGroupDirective } from './components/gateway.directive';
import { SignInGatewayRegisterDirective } from './components/register.directive';
import { SignInGatewayDirective } from './components/login.directive';
import { SignInGatewaySuccessDirective, SignInGatewaySignOutDirective } from './components/token.directive';
import { LoginRegisteredAnalyticsDirective, LoginSignInAnalyticsDirective } from './components/analytics.components';
import { ApiModule } from '@gae-web/appengine-api';
import { LoginStateViewComponent } from './components/login.component';
import { CredentialsComponent } from './components/credentials.component';
import { GatewayMaterialModule } from './material.module';
import { CommonModule } from '@angular/common';

/**
 * Shared directives and components of Appengine Gateway.
 */
@NgModule({
  declarations: [
    CredentialsComponent,
    LoginStateViewComponent,
    OAuthButtonSignInDirective,
    SignInGatewayGroupDirective,
    SignInGatewayRegisterDirective,
    SignInGatewayDirective,
    SignInGatewaySuccessDirective,
    SignInGatewaySignOutDirective,
    LoginRegisteredAnalyticsDirective,
    LoginSignInAnalyticsDirective
  ],
  exports: [
    CredentialsComponent,
    LoginStateViewComponent,
    OAuthButtonSignInDirective,
    SignInGatewayGroupDirective,
    SignInGatewayRegisterDirective,
    SignInGatewayDirective,
    SignInGatewaySuccessDirective,
    SignInGatewaySignOutDirective,
    LoginRegisteredAnalyticsDirective,
    LoginSignInAnalyticsDirective
  ],
  imports: [
    CommonModule,
    ApiModule,
    GatewayMaterialModule
  ]
})
export class GatewayDirectivesModule { }
