import { Component, ViewEncapsulation } from '@angular/core';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { AbstractModelChangeFormComponent } from '@gae-web/appengine-components';

// MARK: Component
@Component({
    selector: 'app-foo-update-view',
    templateUrl: './update-form.component.html'
})
export class FooUpdateFormComponent extends AbstractModelChangeFormComponent<Foo> {}
