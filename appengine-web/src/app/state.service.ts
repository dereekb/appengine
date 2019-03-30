import { Injectable } from '@angular/core';
import { StateService } from '@uirouter/angular';

export abstract class TokenStateConfig {
  public appState: string;
  public newUserAppState?: string;
  public loginState: string;
}

/**
 * Service that provides functions for transitioning to the login and app states.
 */
@Injectable()
export class TokenStateService {

  constructor(private _config: TokenStateConfig, private _state: StateService) { }

  get loginState(): string {
    return this._config.loginState;
  }

  get appState(): string {
    return this._config.appState;
  }

  get newUserAppState(): string {
    return this._config.newUserAppState || this._config.appState;
  }

  public goLoginState() {
    return this._state.go(this.loginState);
  }

  public goApp() {
    return this._state.go(this.appState);
  }

  public goNewUserApp() {
    return this._state.go(this.newUserAppState);
  }

}
