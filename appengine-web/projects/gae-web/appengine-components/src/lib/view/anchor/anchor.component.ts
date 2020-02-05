import { Input, Component } from '@angular/core';
import { ClickableElement, ClickableUrl, ConfigurableClickableSegue } from '../../shared/clickable';

export interface ClickableAnchor extends ClickableElement, ConfigurableClickableSegue, ClickableUrl { }

export enum GaeAnchorComponentType {
  None = 0,
  Clickable = 1,
  Sref = 2,
  Href = 3,
  Disabled = 4
}

/**
 * Component that renders an anchor element depending on the input.
 */
@Component({
  selector: 'gae-anchor',
  templateUrl: './anchor.component.html'
})
export class GaeAnchorComponent {

  private _type: GaeAnchorComponentType;
  private _disabled: boolean;
  private _anchor: ClickableAnchor;

  @Input()
  public anchorClass: string | string[] | object;

  public get type() {
    return this._type;
  }

  public get anchor(): ClickableAnchor {
    return this._anchor;
  }

  @Input()
  public set anchor(anchor: ClickableAnchor) {
    this._anchor = anchor;
    this._updateType();
  }

  public get disabled(): boolean {
    return this._disabled;
  }

  @Input()
  public set disabled(disabled: boolean) {
    if (this._disabled !== disabled) {
      this._disabled = disabled;
      this._updateType();
    }
  }

  /**
   * Updates the anchor's type.
   */
  private _updateType() {
    let type: GaeAnchorComponentType = GaeAnchorComponentType.Disabled;

    if (!this.disabled && this.anchor) {
      if (this.anchor.ref) {
        type = GaeAnchorComponentType.Sref;
      } else if (this.anchor.onClick) {
        type = GaeAnchorComponentType.Clickable;
      } else if (this.anchor.url) {
        type = GaeAnchorComponentType.Href;
      }
    }

    this._type = type;
  }

}
