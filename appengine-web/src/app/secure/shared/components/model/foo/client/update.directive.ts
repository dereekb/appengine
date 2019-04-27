import { Host, Directive, Component } from '@angular/core';
import { AbstractUpdateActionDirective, AbstractActionAnalyticDirective, UpdateActionDirectiveEvent } from '@gae-web/appengine-components';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { FooUpdateService } from 'src/app/secure/shared/api/model/foo/foo.service';
import { AnalyticsService, AnalyticsSender } from '@gae-web/appengine-analytics';

@Directive({
    selector: '[appFooUpdateAction]',
    exportAs: 'appFooUpdateAction'
})
export class FooUpdateActionDirective extends AbstractUpdateActionDirective<Foo> {

    constructor(service: FooUpdateService) {
        super(service);
    }

}

@Directive({
    selector: '[appFooUpdateAnalytics]'
})
export class FooUpdateActionAnalyticDirective extends AbstractActionAnalyticDirective<UpdateActionDirectiveEvent<Foo>> {

    private static readonly ANALYTICS_EVENT_NAME = 'update_tally_type';

    constructor(@Host() action: FooUpdateActionDirective, service: AnalyticsService) {
        super(action, service);
    }

    protected updateAnalyticsWithAction(event: UpdateActionDirectiveEvent<Foo>, analytics: AnalyticsSender) {

        // TODO: Update with additional parameters

        analytics.sendEventData(FooUpdateActionAnalyticDirective.ANALYTICS_EVENT_NAME);
    }

}
