import { BaseError } from 'make-error';

// MARK: OAuth
export class OAuthError extends BaseError {

  constructor(message = 'An error occured occured while logging in.') {
    super(message);
  }

}

export class OAuthCancelledError extends OAuthError {

  constructor(message = 'The request was cancelled or rejected by you.') {
    super(message);
  }

}

export class OAuthPopupClosedError extends OAuthError {

  constructor(message = 'The login request was cancelled by you.') {
    super(message);
  }

}

export class OAuthFailedError extends OAuthError {

  constructor(message = 'The login request failed.') {
    super(message);
  }

}

// MARK: Service
export class ServiceError extends BaseError {

  constructor(message) {
    super(message);
  }

}

export class ServiceSetupError extends BaseError {

  constructor(message) {
    super(message);
  }

}

export class ServiceLoadingError extends BaseError {

  constructor(message) {
    super(message);
  }

}
