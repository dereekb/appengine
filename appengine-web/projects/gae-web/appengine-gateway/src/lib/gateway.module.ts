import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UIRouterModule } from '@uirouter/angular';
import { SignInComponent } from './signin/signin.component';
import { SignUpComponent } from './signup/signup.component';
import { SignOutComponent } from './signout/signout.component';
import { GatewayComponent } from './gateway.component';
import { CredentialsComponent } from './components/credentials.component';
import { OAuthSignInGatewayComponent } from './components/oauth.component';
import { LoginStateViewComponent } from './components/login.component';
import { OAuthButtonSignInDirective } from './components/oauth.directive';
import { SignInGatewayGroupDirective } from './components/gateway.directive';
import { SignInGatewayRegisterDirective } from './components/register.directive';
import { SignInGatewayDirective } from './components/login.directive';
import { SignInGatewaySuccessDirective, SignInGatewaySignOutDirective } from './components/token.directive';
import { LoginRegisteredAnalyticsDirective, LoginSignInAnalyticsDirective } from './components/analytics.components';
import { GoogleModule, FacebookModule } from '@gae-web/appengine-services';
import { ApiModule } from '@gae-web/appengine-api';
import { GATEWAY_STATES } from './gateway.states';
import { GatewayMaterialModule } from './material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { GatewayDirectivesModule } from './directives.module';

// TODO: as Any temporary to override compiler issues with ui router.)
export const ROUTER_CONFIG: any = {
  states: GATEWAY_STATES,
  // config: routerConfigFn // TODO: Add back later for protecting app?
};

@NgModule({
  declarations: [
    GatewayComponent,
    SignInComponent,
    SignUpComponent,
    SignOutComponent,
    CredentialsComponent,
    OAuthSignInGatewayComponent,
    LoginStateViewComponent
  ],
  imports: [
    GatewayDirectivesModule,
    CommonModule,
    GatewayMaterialModule,
    FlexLayoutModule,
    FacebookModule,
    GoogleModule,
    ApiModule,
    UIRouterModule.forChild(ROUTER_CONFIG)
  ]
})
export class GatewayModule { }
