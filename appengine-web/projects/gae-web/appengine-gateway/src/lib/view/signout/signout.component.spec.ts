import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GaeGoogleModule, GaeFacebookModule, GoogleOAuthServiceConfig, FacebookApiServiceConfig } from '@gae-web/appengine-services';
import { GaeGatewayViewsModule } from '../view.module';
import { OAuthLoginApiService, TestUtility } from '@gae-web/appengine-api';
import { GaeSignOutComponent } from './signout.component';
import { UserLoginTokenService, LegacyAppTokenUserService, StoredTokenStorageAccessor, UserLoginTokenAuthenticator, AppTokenStorageService } from '@gae-web/appengine-token';
import { GatewaySegueService } from '../../state.service';
import { TestGatewaySegueService } from '../../../test/state.service';
import { TestAnalyticsModule } from '@gae-web/appengine-analytics';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { UIRouterModule } from '@uirouter/angular';

describe('GaeSignOutComponent', () => {
  let component: GaeSignOutComponent;
  let fixture: ComponentFixture<GaeSignOutComponent>;

  const httpClientSpy: { post: jasmine.Spy } = jasmine.createSpyObj('HttpClient', ['post']);

  const httpClient = httpClientSpy as any;
  const testOAuthLoginApiService = new OAuthLoginApiService(httpClient, TestUtility.testApiRouteConfig());

  const storageAccessor = new StoredTokenStorageAccessor();
  const tokenAuthenticator: UserLoginTokenAuthenticator = {} as any;

  const testUserLoginTokenService = new LegacyAppTokenUserService(new AppTokenStorageService(storageAccessor), tokenAuthenticator);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        NoopAnimationsModule,
        UIRouterModule.forRoot(),
        TestAnalyticsModule.forRoot(),
        GaeGoogleModule.forRoot(false),
        GaeFacebookModule.forRoot(false),
        GaeGatewayViewsModule.forRoot({})
      ],
      providers: [{
        provide: OAuthLoginApiService,
        useValue: testOAuthLoginApiService
      },
      {
        provide: GoogleOAuthServiceConfig,
        useValue: new GoogleOAuthServiceConfig('')
      },
      {
        provide: FacebookApiServiceConfig,
        useValue: new FacebookApiServiceConfig('')
      },
      {
        provide: UserLoginTokenService,
        useValue: testUserLoginTokenService
      },
      {
        provide: GatewaySegueService,
        useValue: new TestGatewaySegueService()
      }]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GaeSignOutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
