import { Component, Input } from '@angular/core';
import { GaeGatewayViewsModule } from './view.module';
import { GaeGatewayViewsConfiguration } from './view.config';

@Component({
  selector: 'gae-gateway-box-view',
  templateUrl: './box.component.html'
})
export class GaeGatewayBoxViewComponent {

  @Input()
  public title: string;

  constructor(private _config: GaeGatewayViewsConfiguration) { }

  get logo() {
    return this._config.logoUrl;
  }

}
