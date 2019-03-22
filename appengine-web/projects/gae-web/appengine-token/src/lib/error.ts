import { BaseError } from 'make-error';
import { LoginTokenPair } from './token';

// Exceptions
export class TokenServiceError extends BaseError { }

export class TokenLoginCommunicationError extends TokenServiceError {

  constructor(public readonly error: Error, message?: string) {
    super(message);
  }

}

export class TokenLoginError extends TokenServiceError {

  constructor(public readonly pair: LoginTokenPair, message?: string) {
    super(message);
  }

}

export class TokenExpiredError extends TokenLoginError { }


/**
 * Thrown when the login token provided is invalid in some way, whether it is expired or not.
 */
export class InvalidLoginTokenError extends TokenServiceError {

  constructor(private _token: LoginTokenPair, message: string = 'Token was invalid.') {
    super(message);
  }

  get token(): LoginTokenPair {
    return this._token;
  }

}

export class UnavailableLoginTokenError extends TokenServiceError {

  constructor(message: string = 'Token was not available.') {
    super(message);
  }

}

// MARK: Authorization Errors

export class TokenAuthorizationError extends BaseError {

  constructor(message?: string) {
    super(message);
  }

}

export class ExpiredTokenAuthorizationError extends TokenAuthorizationError {
  public static readonly CODE = 'EXPIRED_AUTHORIZATION';
}

export class InvalidTokenAuthorizationError extends TokenAuthorizationError {
  public static readonly CODE = 'INVALID_AUTHORIZATION';
}
