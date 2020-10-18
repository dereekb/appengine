import { Component, Input } from '@angular/core';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { AbstractDeleteActionDialogComponent } from '@gae-web/appengine-components';
import { StateOrName } from '@uirouter/core';
import { ModelOrKey } from '@gae-web/appengine-utility';

@Component({
  templateUrl: 'delete-modal.component.html',
})
export class FooDeleteDialogComponent extends AbstractDeleteActionDialogComponent<Foo> {

  @Input()
  public segueRef: StateOrName;

  @Input()
  public targetKey: ModelOrKey<Foo>;

  constructor(@Inject(MatDialogRef) public readonly dialogRef: MatDialogRef<FooDeleteDialogComponent>) {
    super();
  }

}
