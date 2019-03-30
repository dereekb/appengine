import { AnalyticsService, AnalyticsServiceListener, AnalyticsServiceConfiguration, AnalyticsUserSource, AnalyticsUser, AnalyticsStreamEvent, AnalyticsStreamEventType } from './analytics.service';
import { config, BehaviorSubject, Subject } from 'rxjs';

class TestAnalyticsServiceListener extends AnalyticsServiceListener {

  readonly events = new Subject<AnalyticsStreamEvent>();

  updateOnStreamEvent(event: AnalyticsStreamEvent) {
    this.events.next(event);
  }

}

describe('Analytics Service', () => {

  const userStream = new BehaviorSubject<AnalyticsUser>(undefined);

  const testListener: TestAnalyticsServiceListener = new TestAnalyticsServiceListener();

  const testUserSource: AnalyticsUserSource = {
    userStream,
    getAnalyticsUser() {
      return userStream;
    }
  };

  let analyticsService: AnalyticsService;

  beforeEach(() => {
    const configuration: AnalyticsServiceConfiguration = {
      listeners: [testListener],
      userSource: testUserSource
    };

    analyticsService = new AnalyticsService(configuration);
  });

  it('#sendPageView() should send a page view event', (done) => {

    testListener.events.subscribe((event) => {
      expect(event.type).toBe(AnalyticsStreamEventType.PageView);
      done();
    });

    analyticsService.sendPageView();
  });

});
