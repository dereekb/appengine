import { Component, ViewEncapsulation } from '@angular/core';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { AbstractModelChangeFormComponent } from '@gae-web/appengine-components';

// MARK: Component
@Component({
    selector: 'app-foo-delete-view',
    templateUrl: './delete-form.component.html'
})
export class FooDeleteFormComponent extends AbstractModelChangeFormComponent<Foo> {}
