import { Input, Component, ChangeDetectorRef, OnInit, OnDestroy, NgZone } from '@angular/core';
import { ClickableAnchor, AbstractSubscriptionComponent, GaeViewUtility } from '@gae-web/appengine-components';
import { TransitionService, StateService } from '@uirouter/core';

export interface ClickableAnchorLink extends ClickableAnchor {
  title: string;
}

interface NavAnchorLink {
  selected: boolean;
  anchor: ClickableAnchorLink;
}

/**
 * Component that displays a navbar.
 */
@Component({
  selector: 'gae-nav-bar',
  templateUrl: './navbar.component.html'
})
export class GaeNavBarComponent extends AbstractSubscriptionComponent implements OnInit, OnDestroy {

  private stopWatchingTransition: () => void;

  @Input()
  public align = 'center';

  private _links: ClickableAnchorLink[];
  private _anchors: NavAnchorLink[];

  constructor(private readonly _stateService: StateService, private readonly _transitionService: TransitionService, private readonly _ngZone: NgZone) {
    super();
  }

  ngOnInit() {
    this.stopWatchingTransition = this._transitionService.onSuccess({}, () => {
      this._ngZone.run(() => this._updateAnchors());
    }) as any;
  }

  ngOnDestroy() {
    if (!this.stopWatchingTransition) {
      this.stopWatchingTransition();
      delete this.stopWatchingTransition;
    }
  }

  // MARK: Accessors
  @Input()
  public get links(): ClickableAnchorLink[] {
    return this._links;
  }
  public set links(links: ClickableAnchorLink[]) {
    this._links = links;
    this._updateAnchors();
  }

  public get anchors(): NavAnchorLink[] {
    return this._anchors;
  }

  private _updateAnchors(): void {
    const path = this._stateService.$current.name;
    const links = this._links || [];

    this._anchors = links.map((anchor) => {
      const selected: boolean = path.startsWith(anchor.ref);

      return {
        selected,
        anchor
      };
    });
  }

}
