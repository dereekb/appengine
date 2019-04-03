import { Component, ViewEncapsulation } from '@angular/core';

@Component({
  templateUrl: './gateway.component.html',
  styleUrls: ['./gateway.scss'],
  encapsulation: ViewEncapsulation.None
})
export class GatewayComponent {

  public readonly copyright = '© 2019 GAE';

}
