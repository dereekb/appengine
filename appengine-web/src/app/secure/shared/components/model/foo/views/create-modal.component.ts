import { Component } from '@angular/core';
import { AbstractCreateActionDialogCompoment } from '@gae-web/appengine-components';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';

@Component({
  templateUrl: 'create-modal.component.html',
})
export class FooCreateDialogComponent extends AbstractCreateActionDialogCompoment<Foo> {}
