import { Host, Directive, Component } from '@angular/core';
import { UpdateActionDirectiveEvent, AbstractUpdateActionDirective } from '../../../../shared/client/action/update.directive';
import { AbstractActionAnalyticDirective } from '../../../../shared/client/action/action.directive';

import { TallyType } from '../../../../../tally/tally/tallytype/tallytype';
import { TallyTypeUpdateService } from '../../../../../tally/tally/tallytype/tallytype.service';

import { AnalyticsService, AnalyticsSender } from '../../../../../../shared/analytics/analytics.service';

@Directive({
    selector: '[appTallyTypeUpdateAction]',
    exportAs: 'appTallyTypeUpdateAction'
})
export class TallyTypeUpdateActionDirective extends AbstractUpdateActionDirective<TallyType> {

    constructor(service: TallyTypeUpdateService) {
        super(service);
    }

}

@Directive({
    selector: '[appTallyTypeUpdateAnalytics]'
})
export class TallyTypeUpdateActionAnalyticDirective extends AbstractActionAnalyticDirective<UpdateActionDirectiveEvent<TallyType>> {

    private static readonly ANALYTICS_EVENT_NAME = 'update_tally_type';

    constructor(@Host() action: TallyTypeUpdateActionDirective, service: AnalyticsService) {
        super(action, service);
    }

    protected updateAnalyticsWithAction(event: UpdateActionDirectiveEvent<TallyType>, analytics: AnalyticsSender) {

        // TODO: Update with additional parameters

        analytics.sendEventData(TallyTypeUpdateActionAnalyticDirective.ANALYTICS_EVENT_NAME);
    }

}
