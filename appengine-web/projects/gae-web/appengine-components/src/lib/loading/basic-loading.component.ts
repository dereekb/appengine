import { Component, Input, OnChanges, ViewChild, ChangeDetectorRef, AfterViewInit, ElementRef, AfterContentChecked } from '@angular/core';
import { ErrorInput, ValueUtility } from '@gae-web/appengine-utility';
import { GaeViewUtility } from '../shared/utility';

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
export class GaeBasicLoadingComponent implements OnChanges, AfterViewInit {

  private _show = true;

  @Input()
  public text: string;

  @Input()
  public linear = false;

  @Input()
  public error: ErrorInput;

  @ViewChild('customError') customErrorContent: ElementRef;
  @ViewChild('customLoading') customLoadingContent: ElementRef;

  private _loading: boolean;
  private _state: LoadingComponentState = LoadingComponentState.Loading;

  constructor(private _cdRef: ChangeDetectorRef) { }

  ngAfterViewInit() {
    this._tryUpdateState();
    GaeViewUtility.safeDetectChanges(this._cdRef);
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
    return GaeViewUtility.checkNgContentWrapperHasContent(this.customErrorContent);
  }

  public get hasCustomLoading() {
    return GaeViewUtility.checkNgContentWrapperHasContent(this.customLoadingContent);
  }

  public get state() {
    return this._state;
  }

  ngOnChanges() {
    this._detectStateChanges();
  }

  private _detectStateChanges() {
    if (this._tryUpdateState()) {
      GaeViewUtility.safeDetectChanges(this._cdRef);
    }
  }

  private _tryUpdateState(): boolean {
    const state = this._calculateNewState();

    if (this._state !== state) {
      this._state = state;
      return true;
    } else {
      return false;
    }
  }

  private _calculateNewState(): LoadingComponentState {
    let state = LoadingComponentState.Error;

    if (!this.error) {
      if (!this.isLoading && this.show) {
        state = LoadingComponentState.Content;
      } else {
        state = LoadingComponentState.Loading;
      }
    }

    return state;
  }

}
