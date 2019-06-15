import { Component, Input, ChangeDetectorRef } from '@angular/core';
import { AbstractSubscriptionComponent } from '../shared/subscription';
import { LoadingContext } from './loading';

@Component({
  selector: 'gae-loading',
  template: `
  <gae-basic-loading [show]="show" [text]="text" [linear]="linear" [error]="error" [waitFor]="loading">
    <ng-content loading select="[loading]"></ng-content>
    <ng-content content select="[content]"></ng-content>
    <ng-content error select="[error]"></ng-content>
  </gae-basic-loading>
  `
})
export class GaeLoadingComponent extends AbstractSubscriptionComponent {

  @Input()
  public show: boolean;

  @Input()
  public text: string;

  @Input()
  public linear: boolean;

  private _loading: boolean;
  private _error: any;

  constructor(private cdRef: ChangeDetectorRef) {
    super();
  }

  get loading() {
    return this._loading;
  }

  get error() {
    return this._error;
  }

  /**
   * Sets a LoadingContext that is watched for the loading state.
   */
  @Input()
  set context(context: LoadingContext) {
    let subscription;

    if (context) {
      subscription = context.stream.subscribe((x) => {
        this._loading = x.isLoading;
        this._error = x.error;
        this.cdRef.detectChanges();
      });
    }

    this.sub = subscription;
  }

}
