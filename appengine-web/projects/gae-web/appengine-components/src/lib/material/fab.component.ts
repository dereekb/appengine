import {
  Input, Output, Component, Directive, EventEmitter, OnInit, OnDestroy,
  AfterViewInit, ViewEncapsulation, forwardRef, Injectable, Type, Provider
} from '@angular/core';

import { StateService } from '@uirouter/angular';
import { Subject, Observable } from 'rxjs';
import { ValueUtility, SubscriptionObject } from '@gae-web/appengine-utility';
import { throttleTime } from 'rxjs/operators';
import { MatProgressButtonOptions } from 'mat-progress-buttons';

/**
 * Abstract page button component.
 */
@Directive()
export abstract class GaePageButton {

  @Input()
  public disabled: boolean;

  @Input()
  public icon;

  @Input()
  public text: string;

  @Output()
  public readonly buttonClicked = new EventEmitter<{}>();

  public clickButton() {
    this.buttonClicked.emit();
  }

}

export function ProvidePageButton<S extends GaePageButton>(sourceType: Type<S>): Provider[] {
  return [{ provide: GaePageButton, useExisting: sourceType }];
}


/**
 * Service used to ensure only a single fab is visible at a time.
 */
export class PageFabService {

  private _visibleButton = new Subject<GaePageFabComponent | undefined>();

  private _list: GaePageFabComponent[] = [];

  constructor() { }

  public get visibleButtonObs(): Observable<GaePageFabComponent> {
    return this._visibleButton.asObservable();
  }

  public addFab(fab: GaePageFabComponent) {
    this._list.push(fab);
    this._updateForChanges();
  }

  public removeFab(fab: GaePageFabComponent) {
    // Filter out the fab since it may be anywhere in the chain.
    this._list = this._list.filter((x) => x && (x !== fab));
    this._updateForChanges();
  }

  public updateForFabChange(fab: GaePageFabComponent) {
    this._updateForChanges();
  }

  private _updateForChanges() {
    const reverseList = this._list.slice().reverse();
    const nextVisible = ValueUtility.findWhileUndefined(reverseList, (fab: GaePageFabComponent) => {
      if (!fab.hidden) {
        return fab;
      } else {
        return undefined;
      }
    });

    this._visibleButton.next(nextVisible);
  }

}

/*
 <a (click)="clickButton()" [fxHide]="isHidden" [disabled]="disabled" mat-fab class="page-fab" [ngClass]="{ 'page-fab-disabled': disabled }">
    <mat-icon>{{icon}}</mat-icon>
  </a>
 */

@Component({
  selector: 'gae-page-fab',
  template: `
      <mat-spinner-button class="page-fab" (btnClick)="clickButton()" [options]="btnOptions"></mat-spinner-button>
  `,
  providers: ProvidePageButton(GaePageFabComponent)
})
export class GaePageFabComponent extends GaePageButton implements OnInit, OnDestroy {

  @Input()
  public working: boolean;

  private _forcedHidden = false;
  private _isHidden = false;

  private _sub = new SubscriptionObject();

  constructor(private readonly _fabService: PageFabService) {
    super();
    this.icon = 'add';
  }

  ngOnInit() {
    this._sub.subscription = this._fabService.visibleButtonObs.subscribe((fab) => {
      this._isHidden = !(fab === this);
    });
    this._fabService.addFab(this);
  }

  ngOnDestroy() {
    this._sub.destroy();
    this._fabService.removeFab(this);
  }

  public get isHidden() {
    return this._isHidden;
  }

  public get hidden() {
    return this._forcedHidden;
  }

  @Input()
  public set hidden(hidden: boolean) {
    hidden = Boolean(hidden);

    if (hidden !== this._forcedHidden) {
      this._forcedHidden = hidden;
      this._fabService.updateForFabChange(this);
    }
  }

  public get btnOptions(): MatProgressButtonOptions {
    const icon = (this.icon) ? {
      fontIcon: this.icon
    } : undefined;

    return {
      active: this.working,
      icon,
      // buttonIcon: icon,
      text: this.text,
      buttonColor: 'accent',
      barColor: 'accent',
      fab: true,
      mode: 'indeterminate',
      // Only disabled if we're not working, in order to show the animation.
      disabled: !this.working && this.disabled
    };
  }

}

@Component({
  selector: 'gae-page-button',
  template: `
      <a (click)="clickButton()" [disabled]="disabled" mat-button class="page-button">
          <mat-icon *ngIf="icon">{{icon}}</mat-icon><span *ngIf="text">{{text}}</span>
      </a>
  `,
  providers: ProvidePageButton(GaePageButtonComponent)
})
export class GaePageButtonComponent extends GaePageButton {

  constructor() {
    super();
    this.text = 'Done';
  }

}

// MARK: Button Directives
@Directive({
  selector: '[gaePageButtonSegue]'
})
export class GaePageButtonSegueDirective implements OnInit, OnDestroy {

  private _sub = new SubscriptionObject();

  @Input()
  public gaePageButtonSegue: string;

  @Input()
  public segueParams: {};

  public throttle = 50;

  constructor(private _fab: GaePageButton, private _state: StateService) { }

  ngOnInit() {
    this._sub.subscription = this._fab.buttonClicked.pipe(
      throttleTime(this.throttle)
    ).subscribe(() => {
      this.performSegue();
    });
  }

  ngOnDestroy() {
    this._sub.destroy();
  }

  // MARK: Segue
  public get segueName() {
    return this.gaePageButtonSegue;
  }

  protected performSegue() {
    const params = { ...this._state.current.params, ...this.segueParams };
    this._state.go(this.segueName, params);
  }

}
