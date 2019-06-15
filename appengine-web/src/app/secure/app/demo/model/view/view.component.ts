import { Component, Input } from '@angular/core';
import { ModelKey } from '@gae-web/appengine-utility';
import { SingleElementConversionSource } from '@gae-web/appengine-utility';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';

// MARK: Shared
export abstract class AbstractModelViewComponent {

  @Input()
  public readonly fooKey: ModelKey;

  @Input()
  public readonly fooSource: SingleElementConversionSource<ModelKey, Foo>;

}

@Component({
  templateUrl: './view.component.html',
})
export class ModelViewComponent extends AbstractModelViewComponent {}
