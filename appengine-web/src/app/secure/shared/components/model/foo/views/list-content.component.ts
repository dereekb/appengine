import { Component, ViewEncapsulation } from '@angular/core';

import { Foo } from '../../../../../tally/tally/tallytype/tallytype';

import { AbstractListContentComponent } from '../../../../shared/display/list/list-content.component';

import { FooListViewComponent } from './list-view.component';

@Component({
    selector: 'app-foo-list-content',
    templateUrl: './list-content.component.html'
})
export class FooListContentComponent extends AbstractListContentComponent<Foo> {

    constructor(listView: FooListViewComponent) {
        super(listView);
    }

}
