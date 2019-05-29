import { Component, Input, ViewChild, Inject, AfterViewInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { ClickableButton } from './nav.component';
import { Observable, BehaviorSubject, combineLatest } from 'rxjs';
import { AbstractSubscriptionComponent } from '../shared/subscription';
import { map, flatMap } from 'rxjs/operators';

/**
 * Links a GaePageToolbarComponent to the Sidenav.
 */
export class GaePageToolbarSidenav {

}

/**
 * Wraps a sidenav and a page-toolbar within that.
 */
@Component({
  selector: 'gae-page-toolbar',
  templateUrl: 'page-toolbar.component.html'
})
export class GaePageToolbarComponent extends AbstractSubscriptionComponent implements AfterViewInit {

  private _defaultConfiguration: GaePageToolbarConfiguration;
  private _configurations = new BehaviorSubject<Observable<GaePageToolbarConfiguration>[]>([]);
  private _configuration: GaePageToolbarConfiguration = {};

  constructor(private cdRef: ChangeDetectorRef) {
    super();
  }

  ngAfterViewInit(): void {
    this.sub = this._configurations.pipe(
      flatMap((x: Observable<GaePageToolbarConfiguration>[]) => {
        return combineLatest(...x).pipe(
          map((y: GaePageToolbarConfiguration[]) => {
            const validConfigs = y.filter((config) => Boolean(config));
            return validConfigs[validConfigs.length - 1];
          })
        );
      })
    ).subscribe((x) => {
      this._configuration = {
        ...this._defaultConfiguration,
        ...x
      };
      this.cdRef.detectChanges();
    });
  }

  public get activeConfig() {
    return this._configuration;
  }

  public get defaultConfiguration() {
    return this._defaultConfiguration;
  }

  public set defaultConfiguration(defaultConfiguration) {
    this._defaultConfiguration = defaultConfiguration;
  }

  // MARK: Configurations
  protected get configurations() {
    return this._configurations.value.slice();
  }

  public addConfiguration(configuration: Observable<GaePageToolbarConfiguration>) {
    const newConfigurations = this.configurations;
    newConfigurations.push(configuration);
    this._configurations.next(newConfigurations);
  }

  public removeConfiguration(configuration: Observable<GaePageToolbarConfiguration>) {
    const index = this.configurations.indexOf(configuration);

    if (index !== -1) {
      this._configurations.next(this.configurations.splice(index, 1));
    }
  }

  protected refresh() {
    this._configurations.next(this.configurations);
  }

}

export interface GaePageToolbarConfiguration {
  left?: GaePageToolbarButtonNav;
  title?: string;
  right?: GaePageToolbarButtonNav[];
}

/**
 * Component used to pass configurations to a GaePageToolbarComponent.
 */
@Component({
  selector: 'gae-page-toolbar-configuration',
  template: ''
})
export class GaePageToolbarConfigurationComponent implements AfterViewInit, OnDestroy {

  private _configuration = new BehaviorSubject<GaePageToolbarConfiguration>(undefined);

  constructor(@Inject(GaePageToolbarComponent) private readonly toolbarComponent: GaePageToolbarComponent) { }

  ngAfterViewInit() {
    this.toolbarComponent.addConfiguration(this._configuration);
  }

  ngOnDestroy() {
    this.toolbarComponent.removeConfiguration(this._configuration);
  }

  @Input()
  public get configuration() {
    return this._configuration.value;
  }

  public set configuration(configuration) {
    this._configuration.next(configuration);
  }

}

// MARK: Nav Buttons
export interface GaePageToolbarButtonNav extends ClickableButton {
  type: ButtonType;
}

enum ButtonType {
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
      <button mat-button (click)="onNavClick()" *ngSwitchCase="1" [disabled]="disabled"></button>
      <button mat-stroked-button (click)="onNavClick()" *ngSwitchCase="2" [disabled]="disabled"></button>
      <button mat-raised-button (click)="onNavClick()" *ngSwitchCase="3" [disabled]="disabled"></button>
      <button mat-icon-button (click)="onNavClick()" *ngSwitchCase="4" [disabled]="disabled">
        <mat-icon>{{icon}}</mat-icon>
      </button>
    </ng-container>
  `
})
export class GaePageToolbarNavButtonComponent {

  private _nav: GaePageToolbarButtonNav = {
    type: ButtonType.Hidden
  };

  private _type: ButtonType = ButtonType.Basic;

  public get type(): ButtonType {
    return this._type;
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
      type: (nav) ? ((nav.text) ? ButtonType.Basic : ButtonType.Icon) : ButtonType.Hidden,
      ...nav
    };
  }

  public onNavClick() {
    if (this._nav.onClick) {
      this._nav.onClick();
    }
  }

}

