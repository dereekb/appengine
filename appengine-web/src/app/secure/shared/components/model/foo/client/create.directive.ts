import { Input, Host, Directive, Component } from '@angular/core';
import { AbstractCreateActionDirective, CreateActionDirectiveEvent, AbstractActionAnalyticDirective, ActionState } from '@gae-web/appengine-components';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { FooCreateService } from 'src/app/secure/shared/api/model/foo/foo.service';
import { AnalyticsService, AnalyticsSender } from '@gae-web/appengine-analytics';

@Directive({
    selector: '[appFooCreateAction]',
    exportAs: 'appFooCreateAction'
})
export class FooCreateActionDirective extends AbstractCreateActionDirective<Foo> {

    constructor(service: FooCreateService) {
        super(service);
    }

    /*
    // DEBUGGING
    public testCreate() {
        const templates: Foo[] = [];
        let i: number;

        for (i = 0; i < 10; i += 1) {
            templates.push(this.makeJunk());
        }

        this.doCreate({
            templates: templates
        });
    }

    private makeJunk() {
        const template: Foo = new Foo();

        template.verb = 'Test';
        template.noun = String(Math.random()).substring(0, 15);

        return template;
    }
    */

}

@Directive({
    selector: '[appFooCreateAnalytics]'
})
export class FooCreateActionAnalyticDirective extends AbstractActionAnalyticDirective<CreateActionDirectiveEvent<Foo>> {

    private static readonly ANALYTICS_EVENT_NAME = 'create_foo';

    private _count = 0;
    private _start = new Date();

    @Input()
    public generator: string;

    constructor(@Host() action: FooCreateActionDirective, service: AnalyticsService) {
        super(action, service);
    }

    public filterType(event: CreateActionDirectiveEvent<Foo>, analytics: AnalyticsSender) {
        return true;    // Let all Types through.
    }

    protected updateAnalyticsWithAction(event: CreateActionDirectiveEvent<Foo>, analytics: AnalyticsSender) {
        switch (event.state) {
            case ActionState.Reset:
                this._start = new Date();
                break;
            case ActionState.Complete:
                const secondsElapsed = (new Date().getTime() - this._start.getTime()) / 1000;
                const foo = event.result.models[0];

                const data = {
                    seconds: secondsElapsed,
                    generator: this.generator,
                    resetCount: this._count
                };

                analytics.sendEventData(FooCreateActionAnalyticDirective.ANALYTICS_EVENT_NAME, data);
                this._start = new Date();
                this._count += 1;
                break;
            case ActionState.Working:
                break;
        }
    }

}
