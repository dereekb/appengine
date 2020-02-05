import { Input, Component } from '@angular/core';
import { AnchorListElement } from './list-content.component';

/**
 * Component that wraps a gae-anchor and is used within a AbstractDelegatedAnchorListContentComponent implementation.
 */
@Component({
  selector: 'gae-list-anchor',
  template: '<gae-anchor [anchor]="element?.anchor" [anchorClass]="anchorClass"><ng-content></ng-content></gae-anchor>'
})
export class GaeListAnchorComponent {

  @Input()
  public anchorClass: string | string[] | object;

  @Input()
  public disabled: boolean;

  @Input()
  public element: AnchorListElement<any>;

}
