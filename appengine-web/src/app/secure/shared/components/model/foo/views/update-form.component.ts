import { Input, Component, ViewEncapsulation } from '@angular/core';

import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

import { ModelKeyUtility, ModelKey, ModelOrKey, UniqueModel } from '../../../../../../shared/appengine/datastore/modelkey';
import { Foo } from '../../../../../tally/tally/tallytype/tallytype';
import { AbstractModelChangeFormComponent } from '../../../../shared/display/model/change-form.component';

// MARK: Component
@Component({
    selector: 'app-foo-update-view',
    templateUrl: './update-form.component.html'
})
export class FooUpdateFormComponent extends AbstractModelChangeFormComponent<Foo> {}
