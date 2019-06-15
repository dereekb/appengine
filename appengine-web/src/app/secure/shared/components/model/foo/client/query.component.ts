import { Directive, Component, forwardRef } from '@angular/core';
import { ProvideIterableSourceComponent, AbstractConfigurableKeyQuerySourceComponent, AbstractIterableKeySourceComponent } from '@gae-web/appengine-components';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { FooQueryService, FooCachedKeySourceCache } from 'src/app/secure/shared/api/model/foo/foo.service';

@Component({
    template: '',
    selector: 'app-foo-key-query-source',
    providers: ProvideIterableSourceComponent(FooQuerySourceComponent)
})
export class FooQuerySourceComponent extends AbstractConfigurableKeyQuerySourceComponent<Foo> {

    constructor(service: FooQueryService) {
        super(service);
    }

}

@Component({
    template: '',
    selector: 'app-foo-default-query-source',
    providers: ProvideIterableSourceComponent(FooDefaultQuerySourceComponent)
})
export class FooDefaultQuerySourceComponent extends AbstractIterableKeySourceComponent {

    constructor(service: FooCachedKeySourceCache) {
        super(service.makeNewSource());
    }

}
