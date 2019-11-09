import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GaeGoogleModule, GaeFacebookModule, GoogleOAuthServiceConfig, FacebookApiServiceConfig } from '@gae-web/appengine-services';
import { GaeGatewayViewsModule } from '../view.module';
import { OAuthLoginApiService, TestUtility, RegisterApiService } from '@gae-web/appengine-api';
import { GaeSignUpComponent } from './signup.component';
import { UserLoginTokenService, BasicTokenUserService, StoredTokenStorageAccessor, UserLoginTokenAuthenticator, AppTokenStorageService } from '@gae-web/appengine-token';
import { GatewaySegueService } from '../../state.service';
import { TestGatewaySegueService } from '../../../test/state.service';
import { TestAnalyticsModule } from '@gae-web/appengine-analytics';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { UIRouterModule } from '@uirouter/angular';

describe('GaeSignUpComponent', () => {
  let component: GaeSignUpComponent;
  let fixture: ComponentFixture<GaeSignUpComponent>;

  const httpClientSpy: { post: jasmine.Spy } = jasmine.createSpyObj('HttpClient', ['post']);

  const httpClient = httpClientSpy as any;
  const testRegisterApiService = new RegisterApiService(httpClient, TestUtility.testApiRouteConfig());
  const testOAuthLoginApiService = new OAuthLoginApiService(httpClient, TestUtility.testApiRouteConfig());

  const testUserLoginTokenService = new BasicTokenUserService();

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
        provide: RegisterApiService,
        useValue: testRegisterApiService
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
    fixture = TestBed.createComponent(GaeSignUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
