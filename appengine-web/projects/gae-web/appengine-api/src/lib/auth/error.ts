import { BaseError } from 'make-error';

export class TokenAuthenticationError extends BaseError {
  constructor(message?: string) {
    super(message);
  }
}

export class ExpiredTokenAuthorizationError extends TokenAuthenticationError {
  public static readonly CODE = 'EXPIRED_AUTHORIZATION';
}

export class InvalidTokenAuthorizationError extends TokenAuthenticationError {
  public static readonly CODE = 'INVALID_AUTHORIZATION';
}
