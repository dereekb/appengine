import { Injectable, ElementRef, Directive, OnInit, OnDestroy, AfterContentInit } from '@angular/core';

import { UIView, StateDeclaration, StateService, TransitionService } from '@uirouter/angular';
import { SubscriptionObject } from '@gae-web/appengine-utility';
import { Observable } from 'rxjs';

export const DATA_TITLE_KEY = 'appPageTitle';

export type PageTitleShortConfig = string;

export interface PageTitleLongConfig {
  override?: boolean;
  title: string;
}

export type PageTitleConfig = PageTitleShortConfig | PageTitleLongConfig;

@Injectable()
export class PageTitleService {

  constructor(private _stateService: StateService) { }

  public getCurrentTitleForState(stateName: string): string | undefined {
    // TODO: Update this to be a bit more controlable via configuration.
    return this.getTitleForState(stateName) || this.getTitleForCurrentState();
  }

  public getTitleForState(stateName: string): string | undefined {
    const stateDeclaration = this._stateService.get(stateName);

    if (stateDeclaration) {
      return this.getTitleForStateDeclaration(stateDeclaration);
    }

    return undefined;
  }

  public getTitleForStateDeclaration(state: StateDeclaration): string | undefined {
    if (state.data) {
      const titleConfig: PageTitleConfig = state.data[DATA_TITLE_KEY];

      if (titleConfig) {
        return this.buildTitleWithTitleConfig(state, titleConfig);
      }
    }

    return undefined;
  }

  private buildTitleWithTitleConfig(state: StateDeclaration, config: PageTitleConfig): string | undefined {
    switch (typeof config) {
      case 'string':
        return config as string;
      case 'object':
        return this.buildTitleWithLongTitleConfig(state, config as PageTitleLongConfig);
      default:
        throw new Error('Unknown type of PageTitleConfig provided...');
    }
  }

  private buildTitleWithLongTitleConfig(state: StateDeclaration, config: PageTitleLongConfig): string | undefined {
    // TODO: Wrap and read configuration...
    return config.title;
  }

  // Misc
  private getCurrentState(): StateDeclaration {
    return this._stateService.current;
  }

  private getTitleForCurrentState(): string | undefined {
    return this.getTitleForStateDeclaration(this.getCurrentState());
  }

}

@Directive({
  selector: '[gaePageTitle]'
})
export class GaePageTitleDirective implements OnInit, OnDestroy, AfterContentInit {

  private _transitionDispose: any;
  private _overrideSub = new SubscriptionObject();
  private _override: Observable<string>;

  constructor(private _el: ElementRef, private _view: UIView, private _transition: TransitionService, private _service: PageTitleService) { }

  ngOnInit() {
    this._transitionDispose = this._transition.onSuccess({}, () => this.refreshForTransition());
  }

  ngAfterContentInit() {
    this.refreshForTransition();
  }

  ngOnDestroy() {
    this._overrideSub.destroy();

    if (this._transitionDispose) {
      this._transitionDispose();
      delete this._transitionDispose;
    }
  }

  // MARK: Accessors
  protected get title() {
    return this._el.nativeElement.innerText;
  }

  protected set title(title: string | undefined) {
    this._el.nativeElement.innerText = title || '';
  }

  public set titleOverride(override: Observable<string>) {
    if (override) {
      this._overrideSub.subscription = override.subscribe((title) => {
        this.title = title;
      });
    }
  }

  // MARK: Internal
  private refreshForTransition(): void {
    if (!this._override) {
      this.title = this.configurationTitle;
    }
  }

  protected get configurationTitle(): string {
    const stateName = this.contextStateName;
    return this._service.getCurrentTitleForState(stateName);
  }

  /**
   * Returns the state that represents the content within the current UIView.
   */
  private get contextStateName() {
    return this._view.name; // TODO: Check this value?
  }

}
