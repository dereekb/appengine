import { MatSidenav, MatSidenavContainer, MatDrawerToggleResult } from '@angular/material/sidenav';
import { TransitionService } from '@uirouter/core';
import { Directive, AfterContentInit, OnDestroy, Input, Host, Inject } from '@angular/core';

@Directive({
  selector: '[gaeSidenavController]',
  exportAs: 'gaeSidenavController'
})
export class GaeSidenavControllerDirective implements AfterContentInit, OnDestroy {

  @Input()
  public readonly sidenav: MatSidenav;

  private _transitionUnsub: () => void;

  constructor(@Inject(MatSidenavContainer) @Host() public readonly container: MatSidenavContainer, private _transitionService: TransitionService) { }

  ngAfterContentInit() {
    this._transitionUnsub = this._transitionService.onSuccess({}, () => {
      this.close();
    }) as () => void;
  }

  ngOnDestroy() {
    if (this._transitionUnsub) {
      this._transitionUnsub();
    }
  }

  // MARK: Public
  public close(): Promise<MatDrawerToggleResult> {
    return this.sidenav.close();
  }

  public open(): Promise<MatDrawerToggleResult> {
    return this.sidenav.open();
  }

}
