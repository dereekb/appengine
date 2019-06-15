import { Component, Input, OnChanges, ViewChild, ChangeDetectorRef, AfterViewInit } from '@angular/core';
import { ErrorInput, ValueUtility } from '@gae-web/appengine-utility';

/**
 * GaeBasicLoadingComponent loading state.
 */
export enum LoadingComponentState {

  Loading = 0,

  Content = 1,

  Error = 2

}

/**
 * Basic loading component.
 */
@Component({
  selector: 'gae-basic-loading',
  templateUrl: './basic-loading.component.html'
})
export class GaeBasicLoadingComponent implements AfterViewInit, OnChanges {

  private _show = true;

  @Input()
  public text: string;

  @Input()
  public linear = false;

  @Input()
  public error: ErrorInput;

  @ViewChild('error', {static: true}) customErrorContent;
  @ViewChild('loading', {static: true}) customLoadingContent;

  private _hasCustomError;
  private _hasCustomLoading;

  private _loading: boolean;
  private _state: LoadingComponentState = LoadingComponentState.Loading;

  constructor(private cdRef: ChangeDetectorRef) { }

  ngAfterViewInit() {
    this._hasCustomError = Boolean(this.customErrorContent);
    this._hasCustomLoading = Boolean(this.customLoadingContent);
    this.cdRef.detectChanges();
  }

  get isLoading() {
    return this._loading;
  }

  get show() {
    return this._show;
  }

  @Input()
  set show(show: boolean | undefined) {
    this._show = ValueUtility.isNotNullOrUndefined(show) ? show : true;
  }

  @Input()
  set waitFor(object) {
    this._loading = Boolean(object);
  }

  public get hasCustomError() {
    return this._hasCustomError;
  }

  public get hasCustomLoading() {
    return this._hasCustomLoading;
  }

  public get state() {
    return this._state;
  }

  ngOnChanges() {
    let state = LoadingComponentState.Error;

    if (!this.error) {
      if (!this.isLoading && this.show) {
        state = LoadingComponentState.Content;
      } else {
        state = LoadingComponentState.Loading;
      }
    }

    this._state = state;
  }

}
