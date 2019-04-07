import { Component, Input } from '@angular/core';
import { ErrorInput, CodedError, ErrorUtility } from 'projects/gae-web/appengine-utility/src/lib/error';

/**
 * Basic loading component.
 */
@Component({
  selector: 'gae-error',
  templateUrl: './error.component.html'
})
export class GaeErrorComponent {

  private _error: CodedError;

  get errorData(): CodedError {
    return this._error;
  }

  @Input()
  set error(error: ErrorInput) {
    this._error = ErrorUtility.makeErrorData(error);
  }

}
