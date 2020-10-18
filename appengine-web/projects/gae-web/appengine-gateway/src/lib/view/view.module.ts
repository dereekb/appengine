import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GaeSignInComponent } from './signin/signin.component';
import { GaeSignUpComponent } from './signup/signup.component';
import { GaeSignOutComponent } from './signout/signout.component';
import { GaeGoogleModule, GaeFacebookModule } from '@gae-web/appengine-services';
import { GaeGatewayMaterialModule } from '../material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { GaeGatewayBoxViewComponent } from './box.component';
import { GaeGatewayAnalyticsModule } from '../analytics/analytics.module';
import { GaeGatewayComponentsModule } from '../components/components.module';
import { GaeGatewayOAuthModule } from '../oauth/oauth.module';
import { GaeGatewayViewsConfiguration } from './view.config';
import { UIRouterModule } from '@uirouter/angular';

@NgModule({
  declarations: [
    GaeSignInComponent,
    GaeSignUpComponent,
    GaeSignOutComponent,
    GaeGatewayBoxViewComponent,
  ],
  exports: [
    GaeSignInComponent,
    GaeSignUpComponent,
    GaeSignOutComponent,
    GaeGatewayBoxViewComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    UIRouterModule,
    GaeGatewayAnalyticsModule,
    GaeGatewayOAuthModule,
    GaeGatewayComponentsModule,
    GaeGatewayMaterialModule,
    GaeFacebookModule,
    GaeGoogleModule
  ]
})
export class GaeGatewayViewsModule {

  static forRoot(configuration: GaeGatewayViewsConfiguration): ModuleWithProviders<GaeGatewayViewsModule> {
    return {
      ngModule: GaeGatewayViewsModule,
      providers: [
        // Configuration
        {
          provide: GaeGatewayViewsConfiguration,
          useValue: configuration
        }
      ]
    };
  }

}
