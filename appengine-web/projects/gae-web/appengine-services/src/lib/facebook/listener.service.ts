import { Injectable } from '@angular/core';

import { AnalyticsStreamEvent, AnalyticsStreamEventType, AnalyticsServiceListener } from '@gae-web/appengine-analytics';
import { FacebookAnalyticsService, FacebookApiService } from './facebook.service';

/**
 * AnalyticsServiceListener adapter for Facebook.
 */
@Injectable()
export class FacebookAnalyticsListenerService extends AnalyticsServiceListener {

    constructor(private _facebookApi: FacebookApiService) {
        super();
    }

    protected updateOnStreamEvent(streamEvent: AnalyticsStreamEvent) {
        this._facebookApi.getApi().then((api) => {
            this.handleStreamEvent(api.AppEvents, streamEvent);
        });
    }

    protected handleStreamEvent(api: FacebookAnalyticsService, streamEvent: AnalyticsStreamEvent) {
        switch (streamEvent.type) {
            case AnalyticsStreamEventType.NewUserEvent:
                this.updateWithNewUserEvent(api, streamEvent);
                break;
            case AnalyticsStreamEventType.UserLoginEvent:
            case AnalyticsStreamEventType.Event:
                this.updateWithEvent(api, streamEvent);
                break;
            case AnalyticsStreamEventType.PageView:
                api.logPageView();
                break;
            case AnalyticsStreamEventType.UserChange:
                this.changeUserId(api, streamEvent.userId);
                break;
        }
    }

    protected updateWithNewUserEvent(api: FacebookAnalyticsService, streamEvent: AnalyticsStreamEvent) {
        this.updateWithEvent(api, streamEvent, api.EventNames.COMPLETED_REGISTRATION);

        const properties = {};

        api.updateUserProperties(properties);
    }

    protected updateWithEvent(api: FacebookAnalyticsService, streamEvent: AnalyticsStreamEvent, name?: string) {
        const event = streamEvent.event;

        this.changeUserId(api, streamEvent.userId);
        api.logEvent(name || event.name, event.value, event.data);
    }

    private changeUserId(api: FacebookAnalyticsService, userId: string | undefined) {
        if (userId) {
            api.setUserID(userId);
        } else {
            api.clearUserID();
        }
    }

}
