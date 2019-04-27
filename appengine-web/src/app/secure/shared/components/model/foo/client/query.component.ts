import { Directive, Component, forwardRef } from '@angular/core';

import { AbstractSourceComponent, ProvideIterableSourceComponent } from '../../../../shared/client/resource/source.component';
import { AbstractConfigurableKeyQuerySourceComponent, AbstractIterableKeySourceComponent } from '../../../../shared/client/resource/query.component';

import { ModelKey } from '../../../../../../shared/appengine/datastore/modelkey';

import { TallyType } from '../../../../../tally/tally/tallytype/tallytype';
import { TallyTypeQueryService, TallyTypeCachedKeySourceCache } from '../../../../../tally/tally/tallytype/tallytype.service';

@Component({
    template: '',
    selector: 'app-tally-type-key-query-source',
    providers: ProvideIterableSourceComponent(TallyTypeQuerySourceComponent)
})
export class TallyTypeQuerySourceComponent extends AbstractConfigurableKeyQuerySourceComponent<TallyType> {

    constructor(service: TallyTypeQueryService) {
        super(service);
    }

}

@Component({
    template: '',
    selector: 'app-tally-type-default-query-source',
    providers: ProvideIterableSourceComponent(TallyTypeDefaultQuerySourceComponent)
})
export class TallyTypeDefaultQuerySourceComponent extends AbstractIterableKeySourceComponent<TallyType> {

    constructor(service: TallyTypeCachedKeySourceCache) {
        super(service.makeNewSource());
    }

}
