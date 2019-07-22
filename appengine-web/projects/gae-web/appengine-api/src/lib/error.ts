import { BaseError } from 'make-error';

export class ApiModuleConfigurationError extends BaseError {
  constructor(message?: string) {
    super(message);
  }
}

/**
 * Thrown when a required/requested module is unavailable.
 */
export class ApiModuleUnavailableException extends ApiModuleConfigurationError {

}

/**
 * Thrown in cases where the JWT configuration is incorrect or incompleted.
 *
 * This may be thrown later in the application and not necessarily at startup.
 */
export class ApiJwtConfigurationError extends ApiModuleConfigurationError {
  constructor(message?: string) {
    super(message);
  }
}
