import { Component, ViewEncapsulation, forwardRef } from '@angular/core';

import { Foo } from '../../../../../tally/tally/tallytype/tallytype';

import { AbstractListViewComponent, ProvideListViewComponent } from '../../../../shared/display/list/list-view.component';

@Component({
    selector: 'app-foo-list-view',
    templateUrl: './list-view.component.html',
    providers: ProvideListViewComponent(FooListViewComponent)
})
export class FooListViewComponent extends AbstractListViewComponent<Foo> {}
