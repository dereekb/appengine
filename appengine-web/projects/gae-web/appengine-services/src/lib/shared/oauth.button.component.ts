import { HostListener, OnDestroy } from '@angular/core';
import { OAuthLoginService, OAuthLoginServiceTokenResponse } from './oauth.service';
import { Observable, Subscription, BehaviorSubject } from 'rxjs';
import { first } from 'rxjs/operators';

export enum OAuthButtonState {
  Idle = 0,
  Working = 1,
  Done = 2,
  Error = 3
}

export interface OAuthButtonEvent {
  readonly state: OAuthButtonState;
  readonly error?: Error;
  readonly response?: OAuthLoginServiceTokenResponse;
}

export abstract class OAuthLoginServiceButton {
  readonly serviceName: string;
  readonly state: OAuthButtonState;
  readonly stream: Observable<OAuthButtonEvent>;
  abstract resetButton();
}

/**
 * Abstract oauth button that listens for a click and triggers loading if
 */
export abstract class AbstractOAuthLoginServiceButton implements OAuthLoginServiceButton, OnDestroy {

  protected _sub: Subscription;

  private _stream = new BehaviorSubject<OAuthButtonEvent>({
    state: OAuthButtonState.Idle
  });

  constructor(private _service: OAuthLoginService) { }

  ngOnDestroy() {
    this._clearSub();
  }

  // MARK: Accessors
  public get serviceName() {
    return this._service.serviceName;
  }

  public get state() {
    return this._stream.value.state;
  }

  public get response() {
    return this._stream.value.response;
  }

  public get stream() {
    return this._stream.asObservable();
  }

  public get clickable() {
    return this.state === OAuthButtonState.Idle || this.state === OAuthButtonState.Error;
  }

  // MARK: Clicks
  @HostListener('click')
  public clickLogin(): void {
    if (this.clickable) {
      this.tryLogin();
    }
  }

  private tryLogin() {
    this._stream.next({
      state: OAuthButtonState.Working
    });

    this._sub = this._service.tokenLogin().pipe(first())
      .subscribe((response) => {
        this._stream.next({
          state: OAuthButtonState.Done,
          response
        });
      }, (error) => {
        this._stream.next({
          state: OAuthButtonState.Error,
          error
        });
      }, () => {
        this._clearSub();
      });
  }

  // MARK: Internal
  public resetButton() {
    this._clearSub();
    this._stream.next({
      state: OAuthButtonState.Idle
    });
  }

  private _clearSub() {
    if (this._sub) {
      this._sub.unsubscribe();
    }
  }

}
