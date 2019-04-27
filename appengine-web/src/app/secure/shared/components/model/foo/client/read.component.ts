import { Input, Component, forwardRef } from '@angular/core';

import { AbstractReadSourceComponent, ProvideReadSourceComponent } from '../../../../shared/client/resource/read.component';

import { SingleElementSource } from '../../../../../../shared/utility/source.interface';

import { TallyTypeLinkedModel, TallyType } from '../../../../../tally/tally/tallytype/tallytype';
import { TallyTypeReadSourceFactory } from '../../../../../tally/tally/tallytype/tallytype.service';

@Component({
    template: '',
    selector: 'app-tally-type-read-source',
    providers: [ProvideReadSourceComponent(TallyTypeReadSourceComponent)]
})
export class TallyTypeReadSourceComponent extends AbstractReadSourceComponent<TallyType> {

    constructor(source: TallyTypeReadSourceFactory) {
        super(source);
    }

    // TODO: Move to separate directive.
    @Input()
    public set tallyTypeLinkedKeyInput(source: SingleElementSource<TallyTypeLinkedModel>) {
        this.readSourceKeys = source.first.map((x) => (x) ? x.tallyTypeKey : []);
    }

}
