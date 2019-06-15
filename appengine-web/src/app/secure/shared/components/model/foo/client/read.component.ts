import { Input, Component, forwardRef } from '@angular/core';
import { ProvideReadSourceComponent, AbstractReadSourceComponent } from '@gae-web/appengine-components';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { FooReadSourceFactory } from 'src/app/secure/shared/api/model/foo/foo.service';

@Component({
    template: '',
    selector: 'app-foo-read-source',
    providers: [ProvideReadSourceComponent(FooReadSourceComponent)]
})
export class FooReadSourceComponent extends AbstractReadSourceComponent<Foo> {

    constructor(source: FooReadSourceFactory) {
        super(source);
    }

}
