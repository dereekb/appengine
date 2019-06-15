import { Component, ViewEncapsulation } from '@angular/core';

import { FooListViewComponent } from './list-view.component';
import { AbstractListContentComponent } from '@gae-web/appengine-components';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';

@Component({
    selector: 'app-foo-list-content',
    templateUrl: './list-content.component.html'
})
export class FooListContentComponent extends AbstractListContentComponent<Foo> {

    constructor(listView: FooListViewComponent) {
        super(listView);
    }

}
