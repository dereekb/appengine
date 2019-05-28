import { Component, Input, ViewChild } from '@angular/core';
import { NavigationLink } from './nav.component';
import { Observable } from 'rxjs';

/**
 * Links a GaePageToolbarComponent to the Sidenav.
 */
export class GaePageToolbarSidenavLink {

}

/**
 * Wraps a sidenav and a page-toolbar within that.
 */
@Component({
  selector: 'gae-app-page-toolbar',
  templateUrl: 'page-toolbar.component.html'
})
export class GaePageToolbarComponent {

  @Input()
  leftNav: GaePageToolbarNavigationLink;

  @Input()
  rightNavs: Observable<GaePageToolbarNavigationLink[]>;

}

// MARK: Nav Buttons
export interface GaePageToolbarNavigationLink extends NavigationLink {
  type: NavigationLinkType;
}

enum NavigationLinkType {
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
    <ng-container *ngSwitch="type">
      <button mat-button ui-sref="" *ngSwitchCase="1" [disabled]="disabled"></button>
      <button mat-stroked-button *ngSwitchCase="2" [disabled]="disabled"></button>
      <button mat-raised-button *ngSwitchCase="3" [disabled]="disabled"></button>
      <button mat-icon-button *ngSwitchCase="4" [disabled]="disabled">
        <mat-icon>{{icon}}</mat-icon>
      </button>
    </ng-container>
  `
})
export class GaePageToolbarNavButtonComponent {

  private _link: GaePageToolbarNavigationLink = {
    type: NavigationLinkType.Hidden
  };

  private _type: NavigationLinkType = NavigationLinkType.Basic;

  public get type(): NavigationLinkType {
    return this._type;
  }

  public get icon() {
    return this._link.icon;
  }

  public get disabled() {
    return this._link.disabled;
  }

  public get text() {
    return this._link.text;
  }

  public get link(): GaePageToolbarNavigationLink | NavigationLink {
    return this._link;
  }

  @Input()
  public set link(link: GaePageToolbarNavigationLink | NavigationLink) {
    this._link = {
      type: (link) ? ((link.text) ? NavigationLinkType.Basic : NavigationLinkType.Icon) : NavigationLinkType.Hidden,
      ...link
    };
  }

}

