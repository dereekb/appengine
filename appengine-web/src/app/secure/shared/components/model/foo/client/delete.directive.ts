import { Host, Directive, Component } from '@angular/core';
import { DeleteActionDirectiveEvent, AbstractDeleteActionDirective } from '../../../../shared/client/action/delete.directive';
import { AbstractActionAnalyticDirective } from '../../../../shared/client/action/action.directive';

import { TallyType } from '../../../../../tally/tally/tallytype/tallytype';
import { TallyTypeDeleteService } from '../../../../../tally/tally/tallytype/tallytype.service';

import { AnalyticsService, AnalyticsSender } from '../../../../../../shared/analytics/analytics.service';

@Directive({
    selector: '[appTallyTypeDeleteAction]',
    exportAs: 'appTallyTypeDeleteAction'
})
export class TallyTypeDeleteActionDirective extends AbstractDeleteActionDirective<TallyType> {

    constructor(service: TallyTypeDeleteService) {
        super(service);
    }

}

@Directive({
    selector: '[appTallyTypeDeleteAnalytics]'
})
export class TallyTypeDeleteActionAnalyticDirective extends AbstractActionAnalyticDirective<DeleteActionDirectiveEvent<TallyType>> {

    private static readonly ANALYTICS_EVENT_NAME = 'delete_tally_type';

    constructor(@Host() action: TallyTypeDeleteActionDirective, service: AnalyticsService) {
        super(action, service);
    }

    protected updateAnalyticsWithAction(event: DeleteActionDirectiveEvent<TallyType>, analytics: AnalyticsSender) {
        analytics.sendEventData(TallyTypeDeleteActionAnalyticDirective.ANALYTICS_EVENT_NAME);
    }

}
