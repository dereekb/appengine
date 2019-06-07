import { Component, Input, ViewRef, Inject, ChangeDetectorRef, ViewChild, AfterViewInit } from '@angular/core';

import { ActionEvent, ActionObject, ActionState } from '../../shared/action';
import { AbstractSubscriptionComponent } from '../../shared/subscription';

/**
 * Action view that changes what is displayed based on the action of the state.
 */
@Component({
  selector: 'gae-action-view',
  templateUrl: './action.component.html'
})
export class GaeActionViewComponent extends AbstractSubscriptionComponent implements AfterViewInit {

  private _action: ActionObject = { state: ActionState.Reset } as any;
  private _error?: any;

  @ViewChild('error', { static: true }) customErrorContent;

  private _hasCustomError;

  constructor(@Inject(ChangeDetectorRef) private cdRef: ChangeDetectorRef) {
    super();
  }

  ngAfterViewInit() {
    this._hasCustomError = Boolean(this.customErrorContent);
    this.cdRef.detectChanges();
  }

  public get hasCustomError() {
    return this._hasCustomError;
  }

  public get showContent() {
    return !this.showDone;
  }

  public get showError() {
    return this._action.state === ActionState.Error;
  }

  public get showDone() {
    return this._action.state === ActionState.Complete;
  }

  public get state() {
    return this._action.state;
  }

  public get error() {
    return this._error;
  }

  // MARK: Action
  public get action() {
    return this._action;
  }

  @Input()
  public set action(action: ActionObject) {
    this.setAction(action);
  }

  protected setAction(action: ActionObject) {
    if (action) {
      this._action = action;
      this.sub = action.stream.subscribe((event) => this.updateForActionEvent(event));
    }
  }

  protected updateForActionEvent(event: ActionEvent) {
    if (!(this.cdRef as ViewRef).destroyed) {
      this._error = event.error;
      this.cdRef.detectChanges();
    }
  }

  public reset() {
    this._action.reset();
  }

}

/**
 * Action view that changes what is displayed based on the action of the state.
 */
@Component({
  selector: 'gae-action-done-view',
  template: `
    <div class="gae-action-done-view">
      <mat-icon>{{ icon }}</mat-icon>
      <h2>{{ title }}</h2>
      <p>{{ text }}</p>
    </div>
  `
})
export class GaeActionDoneViewComponent {

  @Input()
  public icon = 'done';

  @Input()
  public title = 'Success';

  @Input()
  public text = 'Action was completed successfully.';

}


/**
 * Action view that changes what is displayed based on the action of the state.
 */
@Component({
  selector: 'gae-action-reset-view',
  template: `
    <div class="gae-action-reset-view">
      <button mat-button (click)="reset()">{{ text }}</button>
    </div>
  `
})
export class GaeActionResetViewComponent {

  @Input()
  public text = 'Reset';

  constructor(@Inject(GaeActionViewComponent) private readonly _actionViewComponent: GaeActionViewComponent) { }

  reset() {
    this._actionViewComponent.reset();
  }

}
