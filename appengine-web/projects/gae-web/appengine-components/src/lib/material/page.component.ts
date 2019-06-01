import { Component, Input, ViewChild } from '@angular/core';
import { MatSidenav, MatSidenavContainer } from '@angular/material';

/**
 * Wraps a sidenav and a page-toolbar within that.
 */
@Component({
  selector: 'gae-app-page',
  templateUrl: 'page.component.html'
})
export class GaeAppPageComponent {

  @ViewChild(MatSidenav)
  public readonly sidenav: MatSidenav;

  @ViewChild(MatSidenavContainer)
  public readonly sidenavContainer: MatSidenavContainer;

}
