import { Host, Directive, Component } from '@angular/core';
import { AbstractDeleteActionDirective, AbstractActionAnalyticDirective, DeleteActionDirectiveEvent } from '@gae-web/appengine-components';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { FooDeleteService } from 'src/app/secure/shared/api/model/foo/foo.service';
import { AnalyticsService, AnalyticsSender } from '@gae-web/appengine-analytics';

@Directive({
    selector: '[appFooDeleteAction]',
    exportAs: 'appFooDeleteAction'
})
export class FooDeleteActionDirective extends AbstractDeleteActionDirective<Foo> {

    constructor(service: FooDeleteService) {
        super(service);
    }

}

@Directive({
    selector: '[appFooDeleteAnalytics]'
})
export class FooDeleteActionAnalyticDirective extends AbstractActionAnalyticDirective<DeleteActionDirectiveEvent<Foo>> {

    private static readonly ANALYTICS_EVENT_NAME = 'delete_tally_type';

    constructor(@Host() action: FooDeleteActionDirective, service: AnalyticsService) {
        super(action, service);
    }

    protected updateAnalyticsWithAction(event: DeleteActionDirectiveEvent<Foo>, analytics: AnalyticsSender) {
        analytics.sendEventData(FooDeleteActionAnalyticDirective.ANALYTICS_EVENT_NAME);
    }

}
