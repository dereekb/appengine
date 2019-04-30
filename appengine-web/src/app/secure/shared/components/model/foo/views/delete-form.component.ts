import { Component, ViewEncapsulation } from '@angular/core';
import { Foo } from '../../../../../tally/tally/tallytype/tallytype';
import { AbstractModelChangeFormComponent } from '../../../../shared/display/model/change-form.component';

// MARK: Component
@Component({
    selector: 'app-foo-delete-view',
    templateUrl: './delete-form.component.html'
})
export class FooDeleteFormComponent extends AbstractModelChangeFormComponent<Foo> {}
