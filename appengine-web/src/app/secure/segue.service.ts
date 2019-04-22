import { GatewaySegueService } from '@gae-web/appengine-gateway/public-api';
import { StateService } from '@uirouter/core';
import { Injectable } from '@angular/core';

@Injectable()
export class AppSegueService implements GatewaySegueService {

  constructor(private _stateService: StateService) { }

  // MARK: GatewaySegueService
  segueToGateway(): any {
    return this._stateService.go('app.signin');
  }

  segueToOnboarding(): any {
    return this.segueToApp();
  }

  segueToApp(): any {
    return this._stateService.go('app');
  }

  // MARK: App

}
