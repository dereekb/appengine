import { NgModule } from '@angular/core';
import { OAuthButtonSignInDirective } from './components/oauth.directive';
import { SignInGatewayGroupDirective } from './components/gateway.directive';
import { SignInGatewayRegisterDirective } from './components/register.directive';
import { SignInGatewayDirective } from './components/login.directive';
import { SignInGatewaySuccessDirective, SignInGatewaySignOutDirective } from './components/token.directive';
import { LoginRegisteredAnalyticsDirective, LoginSignInAnalyticsDirective } from './components/analytics.components';
import { ApiModule } from '@gae-web/appengine-api';
import { GaeSignInGatewayViewComponent } from './components/gateway.component';
import { CredentialsComponent } from './components/credentials.component';
import { GatewayMaterialModule } from './material.module';
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
    SignInGatewaySignOutDirective,
    LoginRegisteredAnalyticsDirective,
    LoginSignInAnalyticsDirective
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
    SignInGatewaySignOutDirective,
    LoginRegisteredAnalyticsDirective,
    LoginSignInAnalyticsDirective
  ],
  imports: [
    CommonModule,
    ApiModule,
    GaeLoadingModule,
    GatewayMaterialModule
  ]
})
export class GatewayDirectivesModule { }
