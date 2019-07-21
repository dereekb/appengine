import { OnDestroy } from '@angular/core';
import { LoginTokenPair } from '@gae-web/appengine-token';
import { Observable, Subscription, BehaviorSubject } from 'rxjs';
import { first } from 'rxjs/operators';
import { AbstractSubscriptionComponent } from '@gae-web/appengine-components';

export enum SignInGatewayState {
  Idle = 0,
  Working = 1,
  Done = 2,
  Error = 3
}

export interface SignInGatewayEvent {
  readonly state: SignInGatewayState;
  readonly error?: Error;
  readonly token?: LoginTokenPair;
}

/**
 * Interface that streams SignInGatewayEvent values and can be reset.
 */
export abstract class SignInGateway {
  readonly state: SignInGatewayState;
  readonly stream: Observable<SignInGatewayEvent>;
  abstract resetSignInGateway();
}

/**
 * Abstract SignInGateway
 */
export abstract class AbstractSignInGateway extends AbstractSubscriptionComponent implements SignInGateway {

  private _stream = new BehaviorSubject<SignInGatewayEvent>({
    state: SignInGatewayState.Idle
  });

  // MARK: Accessors
  public get state() {
    return this._stream.value.state;
  }

  public get event() {
    return this._stream.value;
  }

  public get stream() {
    return this._stream.asObservable();
  }

  // MARK: Stream
  protected nextIdle() {
    this._stream.next({
      state: SignInGatewayState.Idle
    });
  }

  protected nextWorking() {
    this._stream.next({
      state: SignInGatewayState.Working
    });
  }

  protected nextLoginToken(loginTokenPair: LoginTokenPair) {
    this._stream.next({
      state: SignInGatewayState.Done,
      token: loginTokenPair
    });
  }

  protected nextError(error: Error) {
    this._stream.next({
      state: SignInGatewayState.Error,
      error
    });
  }

  protected next(event: SignInGatewayEvent) {
    this._stream.next(event);
  }

  // MARK: Internal
  public resetSignInGateway() {
    this.nextIdle();
  }

  protected setTokenSub(tokenObs: Observable<LoginTokenPair>) {
    this.sub = tokenObs.pipe(
      first()
    ).subscribe((token) => {
      this.nextLoginToken(token);
    }, (error) => {
      this.nextError(error);
    });
  }

}
