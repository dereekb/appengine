import { Observable, Subscription, Subject } from 'rxjs';
import { filter } from 'rxjs/operators';
import { Injectable, Optional } from '@angular/core';
import { SubscriptionObject } from '@gae-web/appengine-utility';

export type AnalyticsEventName = string;
export type AnalyticsUserId = string;

export interface AnalyticsUser {
  readonly user: AnalyticsUserId;
  readonly properties?: {
    readonly [key: string]: string | number;
  };
}

export interface AnalyticsEventData {
  readonly [key: string]: string | number | boolean;
}

export interface AnalyticsEvent {
  readonly name?: AnalyticsEventName;
  readonly value?: number;
  readonly data?: AnalyticsEventData;
}

export interface UserAnalyticsEvent extends AnalyticsEvent {
  readonly user?: AnalyticsUser;
}

export abstract class AnalyticsSender {

  abstract sendNewUserEvent(user: AnalyticsUser, data?: AnalyticsEventData);

  abstract sendUserLoginEvent(user: AnalyticsUser, data?: AnalyticsEventData);

  abstract sendUserPropertiesEvent(user: AnalyticsUser, data?: AnalyticsEventData);

  abstract sendEventData(name: AnalyticsEventName, data?: AnalyticsEventData);

  abstract sendEvent(event: AnalyticsEvent);

  abstract sendPageView();

}

export enum AnalyticsStreamEventType {

  PageView,
  UserChange,

  // Events
  NewUserEvent,
  UserLoginEvent,
  UserPropertiesEvent,
  Event

}

export interface AnalyticsStreamEvent {
  readonly type: AnalyticsStreamEventType;
  readonly user?: AnalyticsUser;
  readonly event?: UserAnalyticsEvent;
  readonly userId?: AnalyticsUserId;
}

export abstract class AnalyticsUserSource {
  readonly userStream: Observable<AnalyticsUser | undefined>;
  abstract getAnalyticsUser(): Observable<AnalyticsUser>;
}


export abstract class AnalyticsServiceListener {
  public abstract listenToService(service: AnalyticsService): void;
}

export abstract class AbstractAnalyticsServiceListener implements AnalyticsServiceListener {

  protected _service: AnalyticsService;
  protected _sub = new SubscriptionObject();

  public listenToService(service: AnalyticsService): void {
    this._service = service;
    this._sub.subscription = service.events.pipe(filter((e) => this.filterEvent(e)))
      .subscribe((event) => this.updateOnStreamEvent(event));
  }

  protected filterEvent(streamEvent: AnalyticsStreamEvent) {
    return true;
  }

  protected abstract updateOnStreamEvent(event: AnalyticsStreamEvent);

}

export class AnalyticsServiceConfiguration {
  listeners: AnalyticsServiceListener[];
  isProduction?: boolean;
  userSource?: AnalyticsUserSource;
}

export class AnalyticsStreamEventAnalyticsEventWrapper implements AnalyticsStreamEvent {

  constructor(public readonly event: UserAnalyticsEvent, public readonly type: AnalyticsStreamEventType = AnalyticsStreamEventType.Event) { }

  public get user() {
    return this.event.user;
  }

  public get userId() {
    return (this.user) ? this.user.user : undefined;
  }

}

/**
 * Primary analytics service that emits analytics events that components can listen to.
 */
@Injectable()
export class AnalyticsService implements AnalyticsSender {

  static readonly USER_REGISTRATION_EVENT_NAME = 'user_registered';
  static readonly USER_LOGIN_EVENT_NAME = 'user_login';
  static readonly USER_PROPERTIES_EVENT_NAME = 'user_properties';

  private _subject = new Subject<AnalyticsStreamEvent>();

  private _user?: AnalyticsUser;
  private _userSub: Subscription;

  constructor(private _config: AnalyticsServiceConfiguration, @Optional() userSource: AnalyticsUserSource = _config.userSource) {
    this._init();

    if (userSource) {
      this.setUserSource(userSource);
    }
  }

  // MARK: Source
  public setUserSource(source: AnalyticsUserSource) {
    this._clearUserSub();

    this._userSub = source.userStream.subscribe((user) => {
      this.user = user;
    });
  }

  // MARK: Events
  public get events() {
    return this._subject.asObservable();
  }

  /**
   * Sends an event.
   */
  public sendNewUserEvent(user: AnalyticsUser, data?: AnalyticsEventData) {
    this.sendNextEvent({
      name: AnalyticsService.USER_REGISTRATION_EVENT_NAME,
      data
    }, AnalyticsStreamEventType.NewUserEvent, user);
  }

  public sendUserLoginEvent(user: AnalyticsUser, data?: AnalyticsEventData) {
    this.sendNextEvent({
      name: AnalyticsService.USER_LOGIN_EVENT_NAME,
      data
    }, AnalyticsStreamEventType.UserLoginEvent, user);
  }

  public sendUserPropertiesEvent(user: AnalyticsUser, data?: AnalyticsEventData) {
    this.sendNextEvent({
      name: AnalyticsService.USER_PROPERTIES_EVENT_NAME,
      data
    }, AnalyticsStreamEventType.UserPropertiesEvent, user);
  }

  public sendEventData(name: AnalyticsEventName, data?: AnalyticsEventData) {
    return this.sendEvent({
      name,
      data
    });
  }

  public sendEventType(eventType: AnalyticsEventName) {
    this.sendNextEvent({
      name: eventType
    }, AnalyticsStreamEventType.Event);
  }

  public sendEvent(event: AnalyticsEvent) {
    this.sendNextEvent(event, AnalyticsStreamEventType.Event);
  }

  public sendPageView() {
    this.sendNextEvent({}, AnalyticsStreamEventType.PageView);
  }

  protected sendNextEvent(event: AnalyticsEvent = {}, type: AnalyticsStreamEventType, userOverride?: AnalyticsUser) {
    const user = (userOverride === undefined) ? this._user : userOverride;
    const analyticsEvent: UserAnalyticsEvent = { ...event, user };
    this.nextEvent(analyticsEvent, type);
  }

  protected nextEvent(event: UserAnalyticsEvent, type: AnalyticsStreamEventType) {
    const wrapper = new AnalyticsStreamEventAnalyticsEventWrapper(event, type);
    this._subject.next(wrapper);
  }

  private set user(user: AnalyticsUser) {
    this._user = user;
    this.sendNextEvent({}, AnalyticsStreamEventType.UserChange);
  }

  private _clearUserSub() {
    if (this._userSub) {
      this._userSub.unsubscribe();
      delete this._userSub;
    }
  }

  // MARK: Internal
  private _init() {

    if (this._config.isProduction) {

      // Initialize listeners.
      this._config.listeners.forEach((listener) => {
        listener.listenToService(this);
      });

    } else {

      // Create a new subscription
      this._subject.subscribe((x) => {
        console.log(`Analytics Event: ${AnalyticsStreamEventType[x.type]} User: ${x.userId} Data: ${JSON.stringify(x.event)}.`);
      });

      console.warn('Analytics not in production mode. All analytics events are ignored.');
      return;
    }
  }

}
