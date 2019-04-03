import { Component, ViewEncapsulation } from '@angular/core';

@Component({
  templateUrl: './gateway.component.html',
  styleUrls: ['./gateway.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class GatewayComponent {

  public readonly copyright = '© 2019 GAE';

}
