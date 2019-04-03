import { SignInGateway, SignInGatewayState } from './gateway';
import { Directive, Input, EventEmitter } from '@angular/core';
import { LoginTokenPair, DecodedLoginToken, TokenType } from '@gae-web/appengine-token';
import { RegisterService } from '@gae-web/appengine-api';
import { first, tap } from 'rxjs/operators';
import { AbstractSignInGatewayDirective } from './gateway.directive';

/**
 * Used for watching a SignInGateway and providing functions for registration.
 */
@Directive({
  selector: '[gaeLoginTokenRegisterHandler]',
  exportAs: 'gaeLoginTokenRegisterHandler'
})
export class SignInGatewayRegisterDirective extends AbstractSignInGatewayDirective {

  public readonly registerCompleted = new EventEmitter<LoginTokenPair>();

  private _existingToken: LoginTokenPair;
  private _registerToken: LoginTokenPair;

  constructor(private _registerService: RegisterService) {
      super();
  }

  // MARK: Accessors
  @Input()
  public set gaeLoginTokenRegisterHandler(gateway: SignInGateway) {
      this.setGateway(gateway);
  }

  public get readyToRegister() {
      return Boolean(this._registerToken);
  }

  public get hasExistingToken() {
      return Boolean(this._existingToken);
  }

  public get disabledLogin() {
      return this.state === SignInGatewayState.Working || this.hasExistingToken || this.readyToRegister;
  }

  // MARK: Source
  protected updateWithLoginToken(loginToken: LoginTokenPair) {
      const decoded: DecodedLoginToken = loginToken.decode();

      switch (decoded.type) {
          case TokenType.Registration:
              this.setRegisterToken(loginToken);
              break;
          case TokenType.Full:
              this._existingToken = loginToken;
              this.emitErrorMessage('An account with this login already exists.');
              break;
          case TokenType.Refresh:
              throw new Error('Refresh token passed where not expected.');
      }
  }

  protected setRegisterToken(loginToken: LoginTokenPair) {
      this._registerToken = loginToken;
      this.nextIdle();
  }

  // MARK: Register
  public register() {
      if (!this.readyToRegister) {
          return;
      }

      this.nextWorking();

      const encodedToken = this._registerToken.token;

      const obs = this._registerService.register(encodedToken).pipe(
          first(),
          tap((loginTokenPair) => {
               // Emit login token pair event too.
              this.registerCompleted.emit(loginTokenPair);
          })
      );

      this.setTokenSub(obs);
  }

  public cancelRegister() {
      this.resetSignInGateway();
  }

  // MARK: Existing
  public loginWithExisting() {
      if (this.hasExistingToken) {
          this.nextLoginToken(this._existingToken);
      }
  }

  public cancelLoginWithExisting() {
      this.resetSignInGateway();
  }

  // MARK: Internal
  public resetSignInGateway() {
      super.resetSignInGateway();
      delete this._registerToken;
      delete this._existingToken;
  }

}
