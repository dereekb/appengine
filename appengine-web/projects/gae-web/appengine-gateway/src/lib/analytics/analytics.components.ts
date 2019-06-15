import { Host, Directive, AfterContentInit, OnDestroy } from '@angular/core';
import { Subscription, Observable } from 'rxjs';
import { AnalyticsService, AnalyticsUser, AnalyticsSender } from '@gae-web/appengine-analytics';
import { LoginTokenPair, DecodedLoginToken, LoginPointerType } from '@gae-web/appengine-token';
import { SignInGatewayDirective } from '../components/login.directive';
import { SignInGatewayRegisterDirective } from '../components/register.directive';
import { SubscriptionObject } from '@gae-web/appengine-utility';

export abstract class AbstractLoginTokenAnalyticsDirective implements OnDestroy {

    private _sub = new SubscriptionObject();

    constructor(protected _analytics: AnalyticsService) { }

    ngOnDestroy() {
        this._sub.destroy();
    }

    protected setSub(sub: Observable<LoginTokenPair>) {
        this._sub.subscription = sub.subscribe((x) => this.onTokenEvent(x));
    }

    protected onTokenEvent(token: LoginTokenPair) {
        const decoded = token.decode();
        const login = String(decoded.login);

        const userOverride: AnalyticsUser = {
            user: login
        };

        this.doTokenAnalytics(this._analytics, decoded, userOverride);
    }

    protected abstract doTokenAnalytics(analytics: AnalyticsSender, token: DecodedLoginToken, user: AnalyticsUser);

}

// Register Anayltics
@Directive({
    selector: '[gaeLoginRegisteredAnalytics]'
})
export class LoginRegisteredAnalyticsDirective extends AbstractLoginTokenAnalyticsDirective implements AfterContentInit {

    constructor(analytics: AnalyticsService, @Host() private _handler: SignInGatewayRegisterDirective) {
        super(analytics);
    }

    ngAfterContentInit() {
        super.setSub(this._handler.registerCompleted.asObservable());
    }

    // MARK: Analytics
    protected doTokenAnalytics(analytics: AnalyticsSender, decoded: DecodedLoginToken, user: AnalyticsUser) {
        const type = decoded.pointerType;
        const typeString = LoginPointerType[type];

        analytics.sendNewUserEvent(user, {
            type: typeString
        });
    }

}

// Login Analytics
@Directive({
    selector: '[gaeLoginSignInAnalytics]'
})
export class LoginSignInAnalyticsDirective extends AbstractLoginTokenAnalyticsDirective implements AfterContentInit {

    constructor(analytics: AnalyticsService, @Host() private _handler: SignInGatewayDirective) {
        super(analytics);
    }

    ngAfterContentInit() {
        super.setSub(this._handler.loginCompleted.asObservable());
    }

    // MARK: Analytics
    protected doTokenAnalytics(analytics: AnalyticsSender, decoded: DecodedLoginToken, user: AnalyticsUser) {
        const type = decoded.pointerType;
        const typeString = LoginPointerType[type];

        analytics.sendUserLoginEvent(user, {
            type: typeString
        });
    }

}
