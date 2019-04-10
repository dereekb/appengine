import { NgModule } from '@angular/core';
import { LoginRegisteredAnalyticsDirective, LoginSignInAnalyticsDirective } from './analytics.components';

/**
 * Analytics components of Appengine Gateway.
 */
@NgModule({
  declarations: [
    LoginRegisteredAnalyticsDirective,
    LoginSignInAnalyticsDirective
  ],
  exports: [
    LoginRegisteredAnalyticsDirective,
    LoginSignInAnalyticsDirective
  ],
  imports: []
})
export class GatewayAnalyticsModule { }
