import { GatewaySegueService } from '@gae-web/appengine-gateway/public-api';
import { StateService } from '@uirouter/core';

export class AppSegueService implements GatewaySegueService {

  constructor(private _stateService: StateService) { }

  // MARK: GatewaySegueService
  segueToGateway(): any {
    return this._stateService.go('app.signin');
  }

  segueToOnboarding(): any {

  }

  segueToApp(): any {
    return this._stateService.go('app');
  }

  // MARK: App

}
