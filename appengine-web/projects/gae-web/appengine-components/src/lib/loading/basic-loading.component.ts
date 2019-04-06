import { Component, Input, Output, OnChanges, ViewChild, ChangeDetectorRef, AfterViewInit } from '@angular/core';

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

  @Input()
  public text: string;

  @Input()
  public show = true;

  @Input()
  public linear = false;

  @Input()
  public error: Error;

  @ViewChild('error') customErrorContent;
  @ViewChild('loading') customLoadingContent;

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
      if (this.show && !this.isLoading) {
        state = LoadingComponentState.Content;
      } else {
        state = LoadingComponentState.Loading;
      }
    }

    this._state = state;
  }

}
