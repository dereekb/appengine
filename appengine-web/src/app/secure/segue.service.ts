import { GatewaySegueService } from '@gae-web/appengine-gateway';
import { ModelUtility, ModelKey, ModelOrKey } from '@gae-web/appengine-utility';
import { StateService } from '@uirouter/core';
import { Injectable } from '@angular/core';
import { Foo } from './shared/api/model/foo/foo';

const DEMO_BASE_STATE = 'demo';
const MODEL_DEMO_BASE_STATE = DEMO_BASE_STATE + '.model';
const MODEL_DEMO_LIST_STATE = MODEL_DEMO_BASE_STATE + '.list';
const MODEL_DEMO_VIEW_STATE = MODEL_DEMO_BASE_STATE + '.view';

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

  // MARK: Demo
  segueToModelDemoList(): any {
    return this._stateService.go(MODEL_DEMO_LIST_STATE);
  }

  segueToFoo(target: ModelOrKey<Foo>) {
    const name: string = MODEL_DEMO_VIEW_STATE;
    const params = {
        fooKey: ModelUtility.readModelKeyString(target)
    };

    return this._stateService.go(name, params);
  }

}
