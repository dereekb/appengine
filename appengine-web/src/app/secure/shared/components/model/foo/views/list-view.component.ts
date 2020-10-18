import { Component, ViewEncapsulation, forwardRef } from '@angular/core';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { ProvideListViewComponent, AbstractListViewComponent } from '@gae-web/appengine-components';

@Component({
    selector: 'app-foo-list-view',
    templateUrl: './list-view.component.html',
    providers: ProvideListViewComponent(FooListViewComponent)
})
export class FooListViewComponent extends AbstractListViewComponent<Foo> {
  // TODO: add explicit constructor
 }
