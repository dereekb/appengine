import { BaseError } from 'make-error';

export class ApiConfigurationError extends BaseError {
  constructor(message?: string) {
    super(message);
  }
}

/**
 * Thrown in cases where the JWT configuration is incorrect or incompleted.
 *
 * This may be thrown later in the application and not necessarily at startup.
 */
export class ApiJwtConfigurationError extends ApiConfigurationError {
  constructor(message?: string) {
    super(message);
  }
}
