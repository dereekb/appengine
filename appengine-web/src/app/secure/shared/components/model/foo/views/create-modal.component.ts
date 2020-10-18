import { Component } from '@angular/core';
import { AbstractCreateActionDialogComponent } from '@gae-web/appengine-components';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';

@Component({
  templateUrl: 'create-modal.component.html',
})
export class FooCreateDialogComponent extends AbstractCreateActionDialogComponent<Foo> {

  constructor(@Inject(MatDialogRef) public readonly dialogRef: MatDialogRef<FooCreateDialogComponent>) {
    super();
  }
  
}
