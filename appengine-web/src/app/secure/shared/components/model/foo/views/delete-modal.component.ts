import { Component } from '@angular/core';
import { AbstractCreateActionDialogCompoment } from '@gae-web/appengine-components';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { AbstractDeleteActionDialogCompoment } from '@gae-web/appengine-components';

@Component({
  templateUrl: 'delete-modal.component.html',
})
export class FooDialogComponent extends AbstractDeleteActionDialogCompoment<Foo> {}
