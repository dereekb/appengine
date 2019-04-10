import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OAuthSignInGatewayComponent } from '../components/oauth.component';
import { GoogleModule, FacebookModule, GoogleOAuthServiceConfig, FacebookApiServiceConfig } from '@gae-web/appengine-services';
import { GatewayComponentsModule } from '../components/components.module';
import { OAuthLoginApiService, TestUtility, RegisterApiService } from '@gae-web/appengine-api';
import { SignUpComponent } from './signup.component';
import { UserLoginTokenService, LegacyAppTokenUserService, StoredTokenStorageAccessor, UserLoginTokenAuthenticator, AppTokenStorageService } from '@gae-web/appengine-token';
import { GatewaySegueService } from '../state.service';
import { TestGatewaySegueService } from '../../test/state.service';
import { TestAnalyticsModule } from '@gae-web/appengine-analytics';

describe('SignUpComponent', () => {
  let component: SignUpComponent;
  let fixture: ComponentFixture<SignUpComponent>;

  const httpClientSpy: { post: jasmine.Spy } = jasmine.createSpyObj('HttpClient', ['post']);

  const httpClient = httpClientSpy as any;
  const testRegisterApiService = new RegisterApiService(httpClient, TestUtility.testApiRouteConfig());
  const testOAuthLoginApiService = new OAuthLoginApiService(httpClient, TestUtility.testApiRouteConfig());

  const storageAccessor = StoredTokenStorageAccessor.getLocalStorageOrBackupAccessor();
  const tokenAuthenticator: UserLoginTokenAuthenticator = {} as any;

  const testUserLoginTokenService = new LegacyAppTokenUserService(new AppTokenStorageService(storageAccessor), tokenAuthenticator);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        TestAnalyticsModule.forRoot(),
        GoogleModule.forRoot(new GoogleOAuthServiceConfig(''), false),
        FacebookModule.forRoot(new FacebookApiServiceConfig(''), false),
        GatewayComponentsModule
      ],
      declarations: [OAuthSignInGatewayComponent, SignUpComponent],
      providers: [{
        provide: OAuthLoginApiService,
        useValue: testOAuthLoginApiService
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
    fixture = TestBed.createComponent(SignUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
