import { BaseError } from 'make-error';

export type ErrorCode = string;
export type ErrorMessage = string;

export interface ErrorWrapper {
  data: CodedError;
}

export interface CodedError {
  code: ErrorCode;
  message: ErrorMessage;
}

export type ErrorInput = ErrorWrapper | CodedError;

export class ErrorUtility {

  static makeErrorData(inputError: ErrorInput | undefined): CodedError | undefined {
    let error: CodedError;

    if (inputError) {
      if ((inputError as CodedError).code) {
        error = inputError as CodedError;
      } else if ((inputError as ErrorWrapper).data) {
        error = (inputError as ErrorWrapper).data;
      } else if (inputError instanceof BaseError) {
        error = {
          code: (inputError as any).code || inputError.name,
          message: inputError.message
        };
      } else {
        error = {
          code: 'ERROR',
          message: (inputError as any).message || ''
        };
      }
    }

    return error;
  }

}
