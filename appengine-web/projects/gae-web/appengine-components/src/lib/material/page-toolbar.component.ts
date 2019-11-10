import { Component, Input, Inject, AfterViewInit, OnDestroy, ChangeDetectorRef, ViewRef, Directive, Host } from '@angular/core';
import { ClickableButton } from './nav.component';
import { Observable, BehaviorSubject, combineLatest, of } from 'rxjs';
import { AbstractSubscriptionComponent } from '../shared/subscription';
import { map, flatMap, tap, shareReplay } from 'rxjs/operators';
import { Destroyable } from '@gae-web/appengine-utility';
import { GaeSidenavControllerDirective } from './sidenav.component';
import { GaeViewUtility } from '../shared/utility';

/**
 * Wraps a sidenav and a page-toolbar within that.
 */
@Component({
  selector: 'gae-page-toolbar',
  templateUrl: 'page-toolbar.component.html'
})
export class GaePageToolbarComponent extends AbstractSubscriptionComponent implements OnDestroy, AfterViewInit {

  private _overrideConfiguration: GaePageToolbarConfiguration;
  private _defaultConfiguration: GaePageToolbarConfiguration;
  private _providers = new BehaviorSubject<GaePageToolbarConfigurationProvider[]>([]);
  private _configurationObs: Observable<GaePageToolbarConfiguration>;
  private _configuration: GaePageToolbarConfiguration = {};

  constructor(private cdRef: ChangeDetectorRef) {
    super();
  }

  ngAfterViewInit(): void {
    const obs = this._providers.pipe(
      map((x) => {
        return x.filter(y => Boolean(y)).map(y => y.stream);
      }),
      flatMap((x: Observable<GaePageToolbarConfiguration>[]) => {
        if (x.length > 0) {
          return combineLatest(x).pipe(
            map((y: GaePageToolbarConfiguration[]) => {
              const validConfigs = y.filter((config) => Boolean(config));
              return validConfigs[validConfigs.length - 1];
            })
          );
        } else {
          return of(undefined);
        }
      }),
      map((x) => {
        return {
          ...this._defaultConfiguration,
          ...x,
          ...this._overrideConfiguration
        };
      }),
      tap((x) => this._configuration = x),
      shareReplay(1)
    );

    this._configurationObs = obs.pipe();
    this.sub = obs.subscribe(() => {
      GaeViewUtility.safeDetectChanges(this.cdRef);
    });
  }

  ngOnDestroy() {
    this._providers.complete();
    super.ngOnDestroy();
  }

  public get configuration() {
    return this._configuration;
  }

  public get stream() {
    return this._configurationObs;
  }

  public get left() {
    return this.configuration.left;
  }

  public get title() {
    return this.configuration.title;
  }

  public get right() {
    return this.configuration.right;
  }

  // MARK: Configurations
  public get overrideConfiguration() {
    return this._overrideConfiguration;
  }

  public set overrideConfiguration(overrideConfiguration) {
    this._overrideConfiguration = overrideConfiguration;
    this.refresh();
  }

  public get defaultConfiguration() {
    return this._defaultConfiguration;
  }

  public set defaultConfiguration(defaultConfiguration) {
    this._defaultConfiguration = defaultConfiguration;
    this.refresh();
  }

  protected get providers() {
    return this._providers.value.slice();
  }

  public addProvider(provider: GaePageToolbarConfigurationProvider) {
    const newConfigurations = this.providers;
    newConfigurations.push(provider);
    this._providers.next(newConfigurations);
  }

  public removeProvider(provider: GaePageToolbarConfigurationProvider) {
    const index = this.providers.indexOf(provider);

    if (index !== -1) {
      this._providers.next(this.providers.splice(index, 1));
    }
  }

  protected refresh() {
    this._providers.next(this.providers);
  }

}

export interface GaePageToolbarConfiguration {
  left?: GaePageToolbarButtonNav;
  title?: string;
  right?: GaePageToolbarButtonNav[];
}

/**
 * Provider for a stream of GaePageToolbarConfiguration values.
 */
export class GaePageToolbarConfigurationProvider implements Destroyable {

  private readonly _configuration: BehaviorSubject<GaePageToolbarConfiguration>;

  constructor(configuration: GaePageToolbarConfiguration = {}) {
    this._configuration = new BehaviorSubject<GaePageToolbarConfiguration>(configuration);
  }

  public destroy() {
    this._configuration.complete();
  }

  public get stream(): Observable<GaePageToolbarConfiguration> {
    return this._configuration.asObservable();
  }

  public get currentConfiguration() {
    return this._configuration.value;
  }

  public setConfiguration(configuration) {
    this._configuration.next(configuration);
  }

}

/**
 * Component used to pass configurations to a GaePageToolbarComponent.
 */
@Component({
  selector: 'gae-page-toolbar-configuration',
  template: ''
})
export class GaePageToolbarConfigurationComponent implements AfterViewInit, OnDestroy {

  private readonly _provider = new GaePageToolbarConfigurationProvider();

  constructor(@Inject(GaePageToolbarComponent) private readonly toolbarComponent: GaePageToolbarComponent) { }

  ngAfterViewInit() {
    this.toolbarComponent.addProvider(this._provider);
  }

  ngOnDestroy() {
    this.toolbarComponent.removeProvider(this._provider);
    this._provider.destroy();
  }

  @Input()
  public get configuration() {
    return this._provider.currentConfiguration;
  }

  public set configuration(configuration) {
    this._provider.setConfiguration(configuration);
  }

}

// MARK: Nav Buttons
export interface GaePageToolbarButtonNav extends ClickableButton {
  type: ToolbarButtonNavType;
}

export enum ToolbarButtonNavType {
  Hidden = 0,
  Basic = 1,
  Stroked = 2,
  Raised = 3,
  Icon = 4
}

/**
 * A button used in the page toolbar navigation components.
 */
@Component({
  selector: 'gae-page-toolbar-nav-button',
  template: `
    <ng-container [ngSwitch]="type">
      <button mat-button (click)="onNavClick()" *ngSwitchCase="1" [disabled]="disabled">{{text}}</button>
      <button mat-stroked-button (click)="onNavClick()" *ngSwitchCase="2" [disabled]="disabled">{{text}}</button>
      <button mat-raised-button (click)="onNavClick()" *ngSwitchCase="3" [disabled]="disabled">{{text}}</button>
      <button mat-icon-button (click)="onNavClick()" *ngSwitchCase="4" [disabled]="disabled">
        <mat-icon>{{icon}}</mat-icon>
      </button>
    </ng-container>
  `
})
export class GaePageToolbarNavButtonComponent {

  private _nav: GaePageToolbarButtonNav = {
    type: ToolbarButtonNavType.Hidden
  };

  public get type(): ToolbarButtonNavType {
    return this._nav.type;
  }

  public get icon() {
    return this._nav.icon;
  }

  public get disabled() {
    return this._nav.disabled || !this._nav.onClick;
  }

  public get text() {
    return this._nav.text;
  }

  public get nav(): GaePageToolbarButtonNav | ClickableButton {
    return this._nav;
  }

  @Input()
  public set nav(nav: GaePageToolbarButtonNav | ClickableButton) {
    this._nav = {
      type: (nav) ? ((nav.text) ? ToolbarButtonNavType.Basic : ToolbarButtonNavType.Icon) : ToolbarButtonNavType.Hidden,
      ...nav
    };
  }

  public onNavClick() {
    if (this._nav.onClick) {
      this._nav.onClick();
    }
  }

}

/**
 * Links a GaePageToolbarComponent to a SidenavController and sets the override configuration to drive the sidenav.
 */
@Directive({
  selector: '[gaePageToolbarSidenavControllerLink]',
  exportAs: 'gaePageToolbarSidenavControllerLink'
})
export class GaePageToolbarSidenavControllerLinkDirective implements AfterViewInit {

  constructor(@Inject(GaeSidenavControllerDirective) private _sidenavController: GaeSidenavControllerDirective, @Host() @Inject(GaePageToolbarComponent) private _toolbar: GaePageToolbarComponent) { }

  ngAfterViewInit(): void {
    this._toolbar.overrideConfiguration = {
      left: {
        icon: 'menu',
        text: 'menu',
        type: ToolbarButtonNavType.Icon,
        onClick: () => {
          this._sidenavController.open();
        }
      }
    };
  }

}
